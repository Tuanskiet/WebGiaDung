package com.poly.WebGiaDung.service;

import com.poly.WebGiaDung.dto.EvaluateDto;
import com.poly.WebGiaDung.entity.Evaluate;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.UserApp;

import java.util.List;

public interface EvaluateService {
    Evaluate create(EvaluateDto evaluateDto, UserApp currentUser);

    List<Evaluate> findByProduct(Product product);
}
