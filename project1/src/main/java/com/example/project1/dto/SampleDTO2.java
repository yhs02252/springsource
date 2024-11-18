package com.example.project1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class SampleDTO2 {

    private Long mno;
    private String firstName;
    private String lastName;
}
