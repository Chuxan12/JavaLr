package com.example;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  r = (HttpServletRequest) req;
        HttpServletResponse h = (HttpServletResponse) res;
        String uri = r.getRequestURI().substring(r.getContextPath().length()); // "/static/style.css" → "/static/style.css"
    
        // 1️⃣ Отдаём статику без авторизации
        if (uri.startsWith("/static/")) {
            chain.doFilter(req, res);
            return;
        }
    
        // 2️⃣ Разрешаем публичные страницы
        if (uri.equals("/login") || uri.equals("/register") || uri.equals("/logout")) {
            chain.doFilter(req, res);
            return;
        }
    
        // 3️⃣ Защищённые URL → проверяем сессию
        if (r.getSession(false) != null && r.getSession(false).getAttribute("user") != null) {
            chain.doFilter(req, res);
        } else {
            h.sendRedirect(r.getContextPath() + "/login");
        }
    }
}