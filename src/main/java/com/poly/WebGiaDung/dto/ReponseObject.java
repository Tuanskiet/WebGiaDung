package com.poly.WebGiaDung.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReponseObject {
    private Integer status;
    private Object data;
}
