package com.example;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@jakarta.servlet.annotation.WebServlet("/init")
public class InitServlet extends jakarta.servlet.http.HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws IOException {
        jakarta.servlet.http.HttpSession session = request.getSession(true); // Важно!
        session.setAttribute("game", new GameState());
        response.sendRedirect("game.jsp");
    }
}