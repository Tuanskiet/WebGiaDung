package com.poly.WebGiaDung.api;

import com.poly.WebGiaDung.dto.EvaluateDto;
import com.poly.WebGiaDung.security.MyUserDetails;
import com.poly.WebGiaDung.service.EvaluateService;
import com.poly.WebGiaDung.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EvaluateApi {

    private final EvaluateService evaluateService;

    @PostMapping("/rate")
    public String viewHomePage(@RequestBody EvaluateDto evaluateDto,
                               @AuthenticationPrincipal MyUserDetails myUserDetails,
                               Model model){
        try {
            evaluateService.create(evaluateDto, myUserDetails.getUserApp());
            return "OK";
        }catch (Exception e){
            return "UN_AUTHORIZATION";
        }

    }

    @DeleteMapping("/admin/manager-comment/delete")
    public ResponseEntity<?> doDeleteProduct(@RequestParam(name = "id") Integer id){
        try{
            evaluateService.deleteById(id);
        }catch(Exception e){
            return ResponseEntity.status(500).body(MessageUtils.Product.ERROR_FOREIGN_KEY);
        }
        return ResponseEntity.status(204).body("DELETED");
    }
}
