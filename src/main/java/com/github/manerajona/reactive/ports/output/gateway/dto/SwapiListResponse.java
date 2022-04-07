package com.github.manerajona.reactive.ports.output.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwapiListResponse {
    @JsonProperty("message")
    private String message;
    @JsonProperty("total_records")
    private Integer totalRecords;
    @JsonProperty("total_pages")
    private Integer totalPages;
    @JsonProperty("previous")
    private Object previous;
    @JsonProperty("next")
    private String next;
    @JsonProperty("results")
    private List<ResultDto> results = new ArrayList<>();
}
