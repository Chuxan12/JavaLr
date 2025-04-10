package com.example;

import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper; // <--- Добавьте эту строку
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("\n=== NEW REQUEST ===");
        
        try {
            // Чтение тела запроса вручную (для отладки)
            String body = request.getReader().lines().collect(Collectors.joining());
            System.out.println("Raw request body: " + body);
    
            // Парсим параметры из тела запроса
            Map<String, String> params = parseFormData(body);
            String dirParam = params.get("direction");
    
            System.out.println("Parsed direction: " + dirParam);
    
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendError(401, "Сессия не найдена");
                return;
            }
    
            GameState game = (GameState) session.getAttribute("game");
            if (game == null) {
                response.sendError(400, "Игра не инициализирована");
                return;
            }
    
            if (dirParam != null && !dirParam.isEmpty()) {
                try {
                    Direction newDir = Direction.valueOf(dirParam.toUpperCase());
                    System.out.println("Попытка изменить направление на: " + newDir);
                    
                    if (!newDir.isOpposite(game.getDirection())) {
                        game.setDirection(newDir);
                        System.out.println("Направление изменено на: " + newDir);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Некорректное направление: " + dirParam);
                }
            }
    
            game.update();
    
            // Отправка обновлённого состояния
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json; charset=UTF-8");
            mapper.writeValue(response.getWriter(), game);
    
        } catch (Exception e) {
            System.out.println("Критическая ошибка:");
            e.printStackTrace();
            response.sendError(500, "Внутренняя ошибка сервера");
        }
    }
    
    private Map<String, String> parseFormData(String formData) {
        return Arrays.stream(formData.split("&"))
            .map(pair -> pair.split("="))
            .collect(Collectors.toMap(
                pair -> decode(pair[0]), 
                pair -> pair.length > 1 ? decode(pair[1]) : "",
                (oldVal, newVal) -> newVal, // обработчик коллизий
                LinkedHashMap::new
            ));
    }

    private String decode(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }
}