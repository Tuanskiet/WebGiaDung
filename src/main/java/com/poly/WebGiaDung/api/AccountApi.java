package com.poly.WebGiaDung.api;


import com.poly.WebGiaDung.entity.UserApp;
import com.poly.WebGiaDung.service.CartItemService;
import com.poly.WebGiaDung.service.MyUserService;
import com.poly.WebGiaDung.service.SendEmailService;
import com.poly.WebGiaDung.utils.MessageUtils;
import com.poly.WebGiaDung.utils.RandomStringGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountApi {
    private final MyUserService myUserService;
    private final CartItemService cartItemService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SendEmailService sendEmail;

    @PostMapping("/login-with-ajax")
    public String loginWithModal(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password
    ) {

        try{
            Boolean checkLogin = myUserService.doLogin(username, password);
            if(checkLogin){
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                return "INVALID";
            }
        }catch(UsernameNotFoundException ex){
            ex.getMessage();
            return "NOT_FOUND";
        }
        return "OK";
    }

    @PostMapping(value = "/signup-with-ajax")
    public String loginWithModal(@RequestBody UserApp userApp){
        try{
            myUserService.create(userApp);
        }catch(Exception e) {
            return "ALREADY_EXIST";
        }
        return "OK";
    }

    @PostMapping(value = "/forgot-password/send-code")
    public String sendCodeForgotPassword(@RequestParam String email){
        String codeGenerate = RandomStringGenerator.generateRandomString(6);
        String bodySend = "Xin chào, mã xác nhận của bạn là : "+ codeGenerate;

        UserApp userApp =  myUserService.findByEmail(email);
        if(ObjectUtils.isEmpty(userApp)) return "NOT FOUND";
        myUserService.updatePassword(userApp, codeGenerate);
        try{
            sendEmail.sendMail(MessageUtils.Account.SUBJECT_MAIL_FORGOT_PASSWORD.getVal(),email, bodySend);
        }catch(Exception e) {
            return "ERROR";
        }
        return "OK";
    }

    @PostMapping(value = "/forgot-password")
    @ResponseBody
    public String forgotPassword(@RequestParam(name = "email") String email,
                                 @RequestParam(name = "code") String codeConfirm,
                                 @RequestParam(name = "newPass") String newPassword){

        UserApp userApp =  myUserService.findByEmail(email);
        if(ObjectUtils.isEmpty(userApp)) return "NOT FOUND";
        if(!passwordEncoder.matches(codeConfirm, userApp.getPassword())){
            return "NOT MATCH";
        }
        myUserService.updatePassword(userApp, newPassword);
        return "OK";
    }

}
