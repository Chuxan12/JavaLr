package com.example.page_servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

import com.example.db.dao.LeaderboardDao;

@WebServlet("/leaderboard")
public class LeaderboardServlet extends HttpServlet {
    private LeaderboardDao dao;

    @Override public void init() {
        dao = (LeaderboardDao) getServletContext().getAttribute("leaderboardDao");
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("entries", dao.top(20));
        req.getRequestDispatcher("/WEB-INF/views/leaderboard.jsp").forward(req,resp);
    }
}