package com.solution.developerManager.endpoint;

import com.solution.developerManager.developer.DeveloperDto;
import com.solution.developerManager.developer.DeveloperMapper;
import com.solution.developerManager.developer.DeveloperService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "javainuseapi")
@RequestMapping("/developer")
@RequiredArgsConstructor
@ApiOperation(value = "", authorizations = @Authorization(value = "Authorization"))
public class DeveloperEndpoint {

    final DeveloperService developerService;
    final DeveloperMapper developerMapper;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER','HR')")
    @GetMapping
    @Operation(description = "Find all developers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All developers"),
    })
    @ApiOperation(value = "Find all developers", authorizations = @Authorization(value = "Authorization"))
    public List<?> getAllDevelopers() {
        return developerService.findAll().stream().map(developerMapper::toDeveloperDto).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER','HR')")
    @GetMapping("/{developerEmail}")
    @Operation(description = "Get developer by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer found"),
            @ApiResponse(responseCode = "404", description = "Developer not found")
    })
    @ApiOperation(value = "Get developer by email", authorizations = @Authorization(value = "Authorization"))
    public DeveloperDto getDeveloper(@PathVariable String developerEmail) {
        return developerService.findDeveloperByEmail(developerEmail);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{developerEmail}")
    @Operation(description = "Update developer by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer updated"),
            @ApiResponse(responseCode = "404", description = "Developer not found")
    })
    @ApiOperation(value = "Update developer by email", authorizations = @Authorization(value = "Authorization"))
    public DeveloperDto updateDeveloper(@RequestBody DeveloperDto developerDto, @PathVariable String developerEmail) {
        return developerService.updateDeveloper(developerDto, developerEmail);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{developerEmail}")
    @Operation(description = "Delete developer by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer deleted"),
            @ApiResponse(responseCode = "404", description = "Developer not found")
    })
    @ApiOperation(value = "Delete developer by email", authorizations = @Authorization(value = "Authorization"))
    public void deleteDeveloper(@PathVariable String developerEmail) {
        developerService.deleteDeveloperByEmail(developerEmail);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    @Operation(description = "Add new developer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer added"),
            @ApiResponse(responseCode = "500", description = "Developer not add")
    })
    @ApiOperation(value = "Add new developer", authorizations = @Authorization(value = "Authorization"))
    public DeveloperDto addDeveloper(@RequestBody DeveloperDto developerDto) {
        return developerService.saveDeveloper(developerDto);
    }
}
