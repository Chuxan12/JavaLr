package com.example;

import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/init")
public class InitServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws IOException {
        HttpSession session = request.getSession(true); // Важно!
        session.setAttribute("game", new GameState());
        response.sendRedirect("game.jsp");
    }
}