package com.springshift.pro.controller;

import com.springshift.pro.dto.AnalysisResponse;
import com.springshift.pro.service.ModernizationService;
import com.springshift.pro.service.ReportGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/modernize")
@RequiredArgsConstructor
public class AnalysisController {
    private final ModernizationService service;
    private final ReportGenerator reportGenerator;

    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResponse> analyze(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(service.analyzeProject(file));
    }

    @PostMapping("/export")
    public ResponseEntity<byte[]> export(@RequestBody AnalysisResponse data) {
        byte[] pdf = reportGenerator.generateRoadmapPdf(data.getRoadmap(), data.getReadinessScore());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=MigrationRoadmap.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
