package com.example;

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

        // путь без ctx-prefix
        String path = r.getRequestURI()
                       .substring(r.getContextPath().length());

        /* 1️⃣  CSS/JS/картинки */
        if (path.startsWith("/static/")) {
            chain.doFilter(req, res);   // <-- пропускаем дальше
            return;
        }

        /* 2️⃣ гостевые страницы */
        if (PUBLIC.contains(path)) {
            chain.doFilter(req, res);
            return;
        }

        /* 3️⃣ защита приватных страниц */
        HttpSession sess = r.getSession(false);
        if (sess == null || sess.getAttribute("user") == null) {
            s.sendRedirect(r.getContextPath() + "/login");
        } else {
            chain.doFilter(req, res);
        }
    }
}