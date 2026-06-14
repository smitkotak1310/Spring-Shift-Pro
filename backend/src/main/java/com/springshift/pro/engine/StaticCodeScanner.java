package com.springshift.pro.engine;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.springshift.pro.dto.MigrationTask;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class StaticCodeScanner {

    public List<MigrationTask> scanCodebase(String source) {
        List<MigrationTask> tasks = new ArrayList<>();
        CompilationUnit cu = StaticJavaParser.parse(source);

        for (ImportDeclaration imp : cu.getImports()) {
            String name = imp.getNameAsString();
            
            if (name.startsWith("javax.persistence")) {
                tasks.add(new MigrationTask("Persistence", "Breaking", 
                    "Legacy JPA Import found: " + name,
                    "Replace with 'jakarta.persistence.*'.",
                    "// Legacy\nimport javax.persistence.Entity;\n\n// Modernized (AI Suggestion)\nimport jakarta.persistence.Entity;"));
            }
            
            if (name.startsWith("javax.servlet")) {
                tasks.add(new MigrationTask("Web", "Breaking", 
                    "Legacy Servlet Import: " + name,
                    "Replace with 'jakarta.servlet.*'.",
                    "// Legacy\nimport javax.servlet.http.HttpServletRequest;\n\n// Modernized\nimport jakarta.servlet.http.HttpServletRequest;"));
            }
        }
        return tasks;
    }
}
