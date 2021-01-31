package com.sehrish.accidentdetect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    public String formatted_address;
    public Geometry geometry;
    public String name;
    public int rating;
}
