package com.gzu.system.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

/**
 * 防止未登录的访问者访问用户页面，以及将访问错误目录的用户重定向
 */
@WebFilter({"/people/*", "/place/*", "/agency/*"})
public class SessionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        if (session.getAttribute("userLoginMap") == null) {
            resp.sendRedirect("/");
            return;
        }
        String userType = ((HashMap<String, String>) session.getAttribute("userLoginMap")).get("userType").toLowerCase();
        if (!req.getRequestURI().startsWith("/" + userType)) {
            resp.sendRedirect("/" + userType);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
