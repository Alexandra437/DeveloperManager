package com.solution.developerManager.developer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
public class DeveloperDto {

    @Length(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z].+", message = "The word starting with letter")
    private String name;
    @Email
    private String email;
}
