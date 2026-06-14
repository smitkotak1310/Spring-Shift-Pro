package com.springshift.pro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AnalysisResponse {
    private double readinessScore;
    private int totalIssues;
    private Map<String, Integer> issuesByCategory;
    private List<MigrationTask> roadmap;
}
