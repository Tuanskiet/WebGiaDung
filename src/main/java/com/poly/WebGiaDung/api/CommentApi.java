package com.poly.WebGiaDung.api;

import com.poly.WebGiaDung.dto.CartDto;
import com.poly.WebGiaDung.dto.OrderDto;
import com.poly.WebGiaDung.dto.OrderItemResponse;
import com.poly.WebGiaDung.dto.ProductResponse;
import com.poly.WebGiaDung.entity.Product;
import com.poly.WebGiaDung.security.MyUserDetails;
import com.poly.WebGiaDung.service.*;
import com.poly.WebGiaDung.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentApi {
    private final EvaluateService evaluateService;

    @DeleteMapping("/admin/manager-comment/{productId}/delete")
    public ResponseEntity<?> doDeleteProduct(
            @PathVariable(name = "productId") Integer productId,
            @RequestParam(name = "id") Integer idComment){
        try{
            evaluateService.deleteById(idComment);
        }catch(Exception e){
            return ResponseEntity.status(500).body(MessageUtils.Product.ERROR_FOREIGN_KEY);
        }
        return ResponseEntity.status(204).body("DELETED");
    }
}
