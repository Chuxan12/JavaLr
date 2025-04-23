package com.example;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.dao.UserDao;
import com.example.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDao userDao;

    @Override public void init() {
        userDao = (UserDao) getServletContext().getAttribute("userDao");
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login    = req.getParameter("login");
        String password = req.getParameter("password");
        User user       = userDao.findByLogin(login);

        if (user!=null && BCrypt.verifyer().verify(password.toCharArray(), user.getPasswordHash()).verified) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/login?error=1");
        }
    }
}