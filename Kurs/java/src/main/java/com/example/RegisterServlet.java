package com.example;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.dao.UserDao;
import com.example.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDao userDao;

    @Override public void init() {
        userDao = (UserDao) getServletContext().getAttribute("userDao");
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req,resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login     = req.getParameter("login");
        String nickname  = req.getParameter("nickname");
        String password  = req.getParameter("password");

        if (login==null||nickname==null||password==null||login.isBlank()||nickname.isBlank()||password.isBlank()) {
            resp.sendRedirect("/register?error=empty");
            return;
        }

        String hash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        boolean ok  = userDao.register(new User(login,nickname,hash));

        if (ok) resp.sendRedirect("/login?registered=1");
        else    resp.sendRedirect("/register?error=exists");
    }
}