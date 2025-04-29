package com.example.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;

import com.example.db.dao.UserDao;
import com.example.db.model.User;

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
        String password2 = req.getParameter("password2");

        // simple validation
        if (login==null||nickname==null||password==null||password2==null ||
            login.isBlank()||nickname.isBlank()||password.isBlank()) {
            resp.sendRedirect(req.getContextPath()+"/register?error=empty");
            return;
        }

        if (!password.equals(password2)) {
            resp.sendRedirect(req.getContextPath()+"/register?error=pass");
            return;
        }

        String hash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        boolean ok  = userDao.register(new User(login,nickname,hash));

        if (ok) resp.sendRedirect(req.getContextPath()+"/login?registered=1");
        else    resp.sendRedirect(req.getContextPath()+"/register?error=exists");
    }
}