package com.inheritedsequence.dto;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * A DTO for the {@link com.inheritedsequence.entity.Person} entity
 */
@Data
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public class PersonDto {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
}