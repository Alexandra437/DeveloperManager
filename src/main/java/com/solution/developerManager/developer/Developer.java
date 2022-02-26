package com.solution.developerManager.developer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "developer")
public class Developer {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "name", nullable = false)
    @Length(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z].+", message = "The word starting with letter")
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

}
