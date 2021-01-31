package com.sehrish.accidentdetect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccidentDto {
    private long id;
    private String lat;
    private String lon;
    private long userId;
}
