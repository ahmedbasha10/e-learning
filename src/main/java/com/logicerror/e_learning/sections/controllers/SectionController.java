package com.logicerror.e_learning.sections.controllers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.sections.dtos.SectionDto;
import com.logicerror.e_learning.sections.requests.BatchCreateSectionRequest;
import com.logicerror.e_learning.sections.requests.CreateSectionRequest;
import com.logicerror.e_learning.sections.requests.UpdateSectionRequest;
import com.logicerror.e_learning.sections.services.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/sections")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @PostMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<SectionDto>> createSection(@RequestBody @Valid CreateSectionRequest createSectionRequest, @PathVariable Long courseId) {
        SectionDto section = sectionService.createSection(createSectionRequest, courseId);
        return ResponseEntity.ok(new ApiResponse<>("Section created successfully", section));
    }

    @PostMapping("/course/{courseId}/batch")
    public ResponseEntity<ApiResponse<List<SectionDto>>> batchCreateSections(@RequestBody @Valid BatchCreateSectionRequest batchCreateSectionRequest, @PathVariable Long courseId) {
        List<SectionDto> createdSections = sectionService.batchCreateSections(batchCreateSectionRequest, courseId);
        return ResponseEntity.ok(new ApiResponse<>("Sections created successfully", createdSections));
    }

    @PatchMapping("/{sectionId}")
    public ResponseEntity<ApiResponse<SectionDto>> updateSection(@RequestBody @Valid UpdateSectionRequest updateSectionRequest, @PathVariable Long sectionId) {
        SectionDto section = sectionService.updateSection(updateSectionRequest, sectionId);
        return ResponseEntity.ok(new ApiResponse<>("Section updated successfully", section));
    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<ApiResponse<Void>> deleteSection(@PathVariable Long sectionId) {
        sectionService.deleteSection(sectionId);
        return ResponseEntity.ok(new ApiResponse<>("Section deleted successfully", null));
    }
}
