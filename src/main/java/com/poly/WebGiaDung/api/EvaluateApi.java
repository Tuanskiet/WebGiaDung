package com.poly.WebGiaDung.api;

import com.poly.WebGiaDung.dto.EvaluateDto;
import com.poly.WebGiaDung.security.MyUserDetails;
import com.poly.WebGiaDung.service.EvaluateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
