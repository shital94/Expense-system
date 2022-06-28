package com.revature.ers.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//This switch statement is required when a user is not yet logged in nor registered
@WebFilter(urlPatterns = "*.do")
public class FilterController implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        //This switch statement is required when a user is not yet logged in nor registered
        switch (req.getRequestURI()) {
            case "/ERS-System/login.do":
                filterChain.doFilter(servletRequest, servletResponse);
                break;
            case "/ERS-system/register.do":
                filterChain.doFilter(servletRequest, servletResponse);
                break;
            default:
                if (req.getSession().getAttribute("userData") != null) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    HttpServletResponse resp = (HttpServletResponse) servletResponse;
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
        }
    }

    @Override
    public void destroy() {

    }
}
