package com.logicerror.e_learning.mappers;

import com.logicerror.e_learning.dto.VideoDto;
import com.logicerror.e_learning.courses.entities.Course;
import com.logicerror.e_learning.entities.course.Section;
import com.logicerror.e_learning.entities.course.Video;
import com.logicerror.e_learning.requests.course.video.CreateVideoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "duration", ignore = true)
    @Mapping(target = "title", source = "request.title")
    @Mapping(target = "section", source = "section")
    @Mapping(target = "course", source = "course")
    Video createVideoRequestToVideo(CreateVideoRequest request, Section section, Course course);

    @Mapping(target = "id", source = "video.id")
    @Mapping(target = "title", source = "video.title")
    @Mapping(target = "duration", source = "video.duration")
    @Mapping(target = "url", source = "video.url")
    VideoDto videoToVideoDto(Video video);
}
