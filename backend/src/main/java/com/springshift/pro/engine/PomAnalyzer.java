package com.springshift.pro.engine;

import com.springshift.pro.dto.MigrationTask;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class PomAnalyzer {
    public List<MigrationTask> analyze(MultipartFile file) throws Exception {
        List<MigrationTask> tasks = new ArrayList<>();
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new InputStreamReader(file.getInputStream()));
        String version = model.getParent() != null ? model.getParent().getVersion() : "Unknown";
        if (version.startsWith("2.")) {
            tasks.add(new MigrationTask("Core", "Breaking", "Project uses Spring Boot " + version, "Upgrade parent to 3.3.0. Note: This requires Java 17+ baseline.", "// pom.xml\n<parent>\n  <version>3.3.0</version>\n</parent>"));
        }
        model.getDependencies().forEach(dep -> {
            if (dep.getArtifactId().contains("spring-boot-starter-security")) {
                tasks.add(new MigrationTask("Security", "Breaking", "Spring Security 5.x detected.", "Migration to Security 6.x required. Lambda-based configuration DSL is now mandatory.", "// SecurityConfig.java\n@Bean\npublic SecurityFilterChain filterChain(HttpSecurity http) {\n  return http.build();\n}"));
            }
        });
        return tasks;
    }
}
