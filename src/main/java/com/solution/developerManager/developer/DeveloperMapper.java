package com.solution.developerManager.developer;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeveloperMapper {

    DeveloperDto toDeveloperDto(Developer developer);

    Developer toDeveloper(DeveloperDto developerDto);
}
