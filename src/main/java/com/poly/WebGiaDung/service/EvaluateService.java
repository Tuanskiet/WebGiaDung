package com.poly.WebGiaDung.service;

import com.poly.WebGiaDung.dto.EvaluateDto;
import com.poly.WebGiaDung.entity.Evaluate;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.UserApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EvaluateService {
    Evaluate create(EvaluateDto evaluateDto, UserApp currentUser);

    List<Evaluate> findByProduct(Product product);

    Page<Evaluate> findByKeyword(String keyword, Integer productId, Pageable pageable);

    Page<Evaluate> getCommentsWithPagination( Integer productId, Pageable pageable);

    void deleteById(Integer id);
}
