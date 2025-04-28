package com.example;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/game")
public class GuessServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(true);

        // 1-й заход (из index.jsp)  ───────────────────────────────────────────
        if (session.getAttribute("low") == null) {
            int low  = Integer.parseInt(req.getParameter("min"));
            int high = Integer.parseInt(req.getParameter("max"));

            if (low >= high)
                throw new IllegalArgumentException("Минимум должен быть меньше максимума!");

            session.setAttribute("low",  low);
            session.setAttribute("high", high);
        }
        else {
            // 2+ заходы: анализ ответа пользователя ───────────────────────────
            String feedback = req.getParameter("answer");   // LESS / MORE / EQUAL
            int   guess    = (int) session.getAttribute("guess");
            int   low      = (int) session.getAttribute("low");
            int   high     = (int) session.getAttribute("high");

            switch (feedback) {
                case "LESS"  -> high = guess - 1;
                case "MORE"  -> low  = guess + 1;
                case "EQUAL" -> {
                    session.invalidate();
                    req.getRequestDispatcher("/win.jsp").forward(req, resp);
                    return;
                }
            }

            /* Проверка на «жульничество» – диапазон схлопнулся,
               а пользователь всё ещё не дал EQUAL */
            if (low > high) {
                session.invalidate();
                req.getRequestDispatcher("/cheat.jsp").forward(req, resp);
                return;
            }

            session.setAttribute("low",  low);
            session.setAttribute("high", high);
        }

        // Считаем новое (или первое) предположение и переходим на guess.jsp
        int low  = (int) session.getAttribute("low");
        int high = (int) session.getAttribute("high");
        int guess = low + (high - low) / 2;        // серединка
        session.setAttribute("guess", guess);

        req.setAttribute("guess", guess);
        req.getRequestDispatcher("/guess.jsp").forward(req, resp);
    }

    // Перенаправляем GET-запросы на POST ради простоты
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }
}
