package com.sehrish.accidentdetect.dto;

import lombok.Data;

import java.util.List;

@Data
public class Result {
    public String business_status ;
    public String formatted_address ;
    public Geometry geometry ;
    public String icon ;
    public String name ;
    public List<Object> obfuscated_type;
    public OpeningHours opening_hours ;
    public List<Photo> photos ;
    public String place_id ;
    public double rating;
    public String reference;
    public List<String> types;
    public int user_ratings_total;

}
