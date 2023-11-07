package com.poly.WebGiaDung.filter;
import com.poly.WebGiaDung.entity.MyCategory;
import com.poly.WebGiaDung.service.MyCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GlobalDataFilter implements  Filter {
    private final MyCategoryService myCategoryService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest hRequest = (HttpServletRequest) request;
        HttpServletResponse hResponse = (HttpServletResponse) response;

        if (hRequest.getSession().getAttribute("dataCategory") == null) {
            List<MyCategory> myCategoryList = myCategoryService.getAllCategoryActive();
            hRequest.getSession().setAttribute("dataCategory", myCategoryList);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
