package com.poly.WebGiaDung.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataCompare {
    @JsonProperty(value = "names")
    private List<String> listProductName;

    @JsonProperty(value = "conditions")
    private Map<String, List<String>> listCompare;
}
