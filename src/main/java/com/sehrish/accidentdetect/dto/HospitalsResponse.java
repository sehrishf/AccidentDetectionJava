package com.sehrish.accidentdetect.dto;

import lombok.Data;

import java.util.List;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */

@Data
public class HospitalsResponse {
    public List<Object> html_attributions;
    public String next_page_token ;
    public List<Result> results ;
    public String status;
}







