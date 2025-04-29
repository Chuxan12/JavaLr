package com.example.util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final Set<String> PUBLIC = Set.of(
        "/login", "/register", "/logout", "/main"
    );

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  r = (HttpServletRequest) req;
        HttpServletResponse s = (HttpServletResponse) res;

        String path = r.getRequestURI()
                       .substring(r.getContextPath().length());
                       
        if (path.startsWith("/static/")) {
            chain.doFilter(req, res);   // <-- пропускаем дальше
            return;
        }

        if (PUBLIC.contains(path)) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession sess = r.getSession(false);
        if (sess == null || sess.getAttribute("user") == null) {
            s.sendRedirect(r.getContextPath() + "/login");
        } else {
            chain.doFilter(req, res);
        }
    }
}