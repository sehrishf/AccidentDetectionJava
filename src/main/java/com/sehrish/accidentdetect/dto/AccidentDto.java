package com.sehrish.accidentdetect.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccidentDto {
    private long id;
    private String lat;
    private String lon;
    private long userId;
    private Date createDate;

    @JsonProperty
    private boolean processed ;
}
