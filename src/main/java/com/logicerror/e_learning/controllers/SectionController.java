package com.logicerror.e_learning.controllers;

import com.logicerror.e_learning.controllers.responses.ApiResponse;
import com.logicerror.e_learning.dto.SectionDto;
import com.logicerror.e_learning.dto.VideoDto;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.requests.course.section.BatchCreateSectionRequest;
import com.logicerror.e_learning.requests.course.section.CreateSectionRequest;
import com.logicerror.e_learning.requests.course.section.UpdateSectionRequest;
import com.logicerror.e_learning.services.section.ISectionService;
import com.logicerror.e_learning.services.video.IVideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/sections")
@RequiredArgsConstructor
public class SectionController {
    private final ISectionService sectionService;
    private final IVideoService videoService;

    // Get methods
    // Example: Get section by ID
    @GetMapping("/{sectionId}")
    public ResponseEntity<ApiResponse<SectionDto>> getSectionById(@PathVariable Long sectionId) {
        // Logic to fetch section by ID
        Section section = sectionService.getSectionById(sectionId);
        // Convert to DTO and return response
        SectionDto sectionDto = sectionService.convertToDto(section);

        return ResponseEntity.ok(new ApiResponse<>("Section fetched successfully", sectionDto));
    }

    // Example: Get section by title
    @GetMapping("/course/{courseId}/section/title/{title}")
    public ResponseEntity<ApiResponse<SectionDto>> getSectionByTitle(@PathVariable Long courseId, @PathVariable String title) {
        // Logic to fetch section by title
        Section section = sectionService.getSectionByTitle(courseId, title);
        // Convert to DTO and return response
        SectionDto sectionDto = sectionService.convertToDto(section);

        return ResponseEntity.ok(new ApiResponse<>("Section fetched successfully", sectionDto));
    }

    @GetMapping("/{sectionId}/videos")
    public ResponseEntity<ApiResponse<Page<VideoDto>>> getSectionVideos(@PathVariable Long sectionId, Pageable pageable) {
        Page<Video> videosPage = sectionService.getSectionVideos(sectionId, pageable);
        Page<VideoDto> videoDtoPage = videosPage.map(videoService::convertToDto);
        return ResponseEntity.ok(new ApiResponse<>("Videos fetched successfully", videoDtoPage));
    }


    // Post methods
    @PostMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<SectionDto>> createSection(@RequestBody @Valid CreateSectionRequest createSectionRequest, @PathVariable Long courseId) {
        // Logic to create a new section
        Section section = sectionService.createSection(createSectionRequest, courseId);
        // Convert to DTO and return response
        SectionDto sectionDto = sectionService.convertToDto(section);

        return ResponseEntity.ok(new ApiResponse<>("Section created successfully", sectionDto));
    }

    @PostMapping("/course/{courseId}/batch")
    public ResponseEntity<ApiResponse<List<SectionDto>>> batchCreateSections(@RequestBody @Valid BatchCreateSectionRequest batchCreateSectionRequest, @PathVariable Long courseId) {
        List<Section> createdSections = sectionService.batchCreateSections(batchCreateSectionRequest, courseId);
        List<SectionDto> createdSectionsDto = createdSections.stream()
                .map(sectionService::convertToDto)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>("Sections created successfully", createdSectionsDto));
    }

    // Patch methods
    @PatchMapping("/{sectionId}")
    public ResponseEntity<ApiResponse<SectionDto>> updateSection(@RequestBody @Valid UpdateSectionRequest updateSectionRequest, @PathVariable Long sectionId) {
        // Logic to update an existing section
        Section section = sectionService.updateSection(updateSectionRequest, sectionId);
        // Convert to DTO and return response
        SectionDto sectionDto = sectionService.convertToDto(section);

        return ResponseEntity.ok(new ApiResponse<>("Section updated successfully", sectionDto));
    }

    // Delete methods
    @DeleteMapping("/{sectionId}")
    public ResponseEntity<ApiResponse<Void>> deleteSection(@PathVariable Long sectionId) {
        // Logic to delete a section
        sectionService.deleteSection(sectionId);
        // Return response
        return ResponseEntity.ok(new ApiResponse<>("Section deleted successfully", null));
    }
}
