package com.poly.WebGiaDung.handler;

import com.poly.WebGiaDung.dto.CartDto;
import com.poly.WebGiaDung.entity.CartItem;
import com.poly.WebGiaDung.entity.MyCategory;
import com.poly.WebGiaDung.entity.UserApp;
import com.poly.WebGiaDung.security.MyUserDetails;
import com.poly.WebGiaDung.service.CartItemService;
import com.poly.WebGiaDung.service.MyCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final MyCategoryService myCategoryService;
    private final CartItemService cartItemService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        this.setGlobalData(request, authentication);
        //redirect page by role
        this.redirectAfterLogin(request,response,authentication);
    }

    private void setGlobalData(HttpServletRequest request, Authentication authentication) {

        // set global category in session
        List<MyCategory> myCategoryList = myCategoryService.getAllCategoryActive();
        request.getSession().setAttribute("dataCategory", myCategoryList);
    }

    public void redirectAfterLogin(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        boolean hasUserRole = false;
        boolean hasAdminRole = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            System.out.println(grantedAuthority.getAuthority());
            if (grantedAuthority.getAuthority().equals("user")) {
                hasUserRole = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("admin")) {
                hasAdminRole = true;
                break;
            }
        }

        if (hasUserRole) {
            redirectStrategy.sendRedirect(request, response, "/");
        } else if (hasAdminRole) {
            redirectStrategy.sendRedirect(request, response, "/admin");
        } else {
            throw new IllegalStateException();
        }
    }

    public UserApp getCurrentUser(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return userDetails.getUserApp();
    }
}