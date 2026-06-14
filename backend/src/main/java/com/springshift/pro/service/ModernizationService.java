package com.springshift.pro.service;

import com.springshift.pro.dto.AnalysisResponse;
import com.springshift.pro.dto.MigrationTask;
import com.springshift.pro.engine.PomAnalyzer;
import com.springshift.pro.engine.StaticCodeScanner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ModernizationService {
    private final PomAnalyzer pomAnalyzer;
    private final StaticCodeScanner codeScanner;

    public AnalysisResponse analyzeProject(MultipartFile pomFile) throws Exception {
        List<MigrationTask> tasks = pomAnalyzer.analyze(pomFile);
        String mockSource = "import javax.persistence.Entity; import javax.servlet.http.HttpServletRequest; public class Demo {}";
        tasks.addAll(codeScanner.scanCodebase(mockSource));
        return AnalysisResponse.builder()
                .readinessScore(calculateScore(tasks))
                .totalIssues(tasks.size())
                .issuesByCategory(groupByCategory(tasks))
                .roadmap(tasks)
                .build();
    }

    private double calculateScore(List<MigrationTask> tasks) {
        if (tasks.isEmpty()) return 100.0;
        double penalty = tasks.stream().mapToDouble(t -> t.getImpact().equals("Breaking") ? 15.0 : 5.0).sum();
        return Math.max(0, 100.0 - penalty);
    }

    private Map<String, Integer> groupByCategory(List<MigrationTask> tasks) {
        Map<String, Integer> cats = new HashMap<>(Map.of("Security", 0, "Persistence", 0, "Web", 0, "Core", 0));
        tasks.forEach(t -> cats.merge(t.getCategory(), 1, Integer::sum));
        return cats;
    }
}
