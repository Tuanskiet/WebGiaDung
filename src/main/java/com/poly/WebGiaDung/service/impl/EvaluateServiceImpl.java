package com.poly.WebGiaDung.service.impl;

import com.poly.WebGiaDung.dto.EvaluateDto;
import com.poly.WebGiaDung.entity.Evaluate;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.entity.UserApp;
import com.poly.WebGiaDung.repo.EvaluateRepo;
import com.poly.WebGiaDung.service.EvaluateService;
import com.poly.WebGiaDung.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluateServiceImpl implements EvaluateService {
    private final EvaluateRepo evaluateRepo;
    private final ProductService productService;

    @Override
    public Evaluate create(EvaluateDto evaluateDto, UserApp currentUser) {
        Evaluate newEvaluate = new Evaluate();
        newEvaluate.setContent(evaluateDto.getContent());
        newEvaluate.setNumStar(evaluateDto.getNumStar());
        newEvaluate.setProduct(productService.findById(evaluateDto.getProductId()).get());
        newEvaluate.setNameUser(currentUser.getFullName());
        return evaluateRepo.save(newEvaluate);
    }

    @Override
    public List<Evaluate> findByProduct(Product product) {
        return evaluateRepo.findAllByProduct(product);
    }
}
