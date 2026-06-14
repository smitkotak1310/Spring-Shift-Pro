package com.springshift.pro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MigrationTask {
    private String category;
    private String impact;
    private String description;
    private String suggestion;
    private String codeSnippet; // The new field for AI code deltas
}
