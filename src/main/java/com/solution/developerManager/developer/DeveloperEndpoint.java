package com.solution.developerManager.developer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/developer")
@RequiredArgsConstructor
public class DeveloperEndpoint {

    final DeveloperService developerService;
    final DeveloperMapper developerMapper;

    @GetMapping
    @Operation(description = "Find all developers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All developers"),
    })
    public List<?> getAllDevelopers() {
        return developerService.findAll().stream().map(developerMapper::toDeveloperDto).collect(Collectors.toList());
    }

    @GetMapping("/{developerEmail}")
    @Operation(description = "Get developer by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer found"),
            @ApiResponse(responseCode = "404", description = "Developer not found")
    })
    public DeveloperDto getDeveloper(@PathVariable String developerEmail) {
        return developerService.findDeveloperByEmail(developerEmail);
    }

    @PostMapping("/{developerEmail}")
    @Operation(description = "Update developer by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer updated"),
            @ApiResponse(responseCode = "404", description = "Developer not found")
    })
    public DeveloperDto updateDeveloper(@RequestBody DeveloperDto developerDto, @PathVariable String developerEmail) {
        return developerService.updateDeveloper(developerDto, developerEmail);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{developerEmail}")
    @Operation(description = "Delete developer by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer deleted"),
            @ApiResponse(responseCode = "404", description = "Developer not found")
    })
    public void deleteDeveloper(@PathVariable String developerEmail) {
        developerService.deleteDeveloperByEmail(developerEmail);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    @Operation(description = "Add new developer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer added"),
    })
    public DeveloperDto addDeveloper(@RequestBody DeveloperDto developerDto) {
        return developerService.saveDeveloper(developerDto);
    }

}
