package com.example;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.fasterxml.jackson.annotation.JsonIgnore; 

public class GameState {
    private List<Point> snake;
    private Direction direction;
    private Point target;
    private int score;
    private boolean gameOver;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private long startTime;

    public GameState() {
        snake = new ArrayList<>();
        snake.add(new Point(10, 10));
        direction = Direction.RIGHT;
        generateNewTarget();
        score = 0;
        startTime = System.currentTimeMillis();
        gameOver = false;
    }

    @JsonIgnore
    private Random random = new Random();

    public int getLength() {
        return snake.size();
    }
    
    public long getElapsedSeconds() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }
    public List<Point> getSnake() { return snake; }
    public Point getTarget() { return target; }
    public Direction getDirection() { return direction; }
    public int getScore() { return score; }
    public boolean isGameOver() { return gameOver; }

    public void setDirection(Direction newDir) {
        // Проверка на противоположное направление должна быть здесь
        if (!newDir.isOpposite(this.direction)) {
            this.direction = newDir;
            System.out.println("[GameState] Direction changed to: " + newDir);
        } else {
            System.out.println("[GameState] Blocked opposite direction: " + newDir);
        }
    }

    public void generateNewTarget() {
        do {
            target = new Point(random.nextInt(WIDTH), random.nextInt(HEIGHT));
        } while (snake.contains(target));
    }

    public void update() {
        if (gameOver) return;
    
        Point head = new Point(snake.get(0));
        switch (direction) {
            case UP: head.y = (head.y - 1 + HEIGHT) % HEIGHT; break;
            case DOWN: head.y = (head.y + 1) % HEIGHT; break;
            case LEFT: head.x = (head.x - 1 + WIDTH) % WIDTH; break;
            case RIGHT: head.x = (head.x + 1) % WIDTH; break;
        }
    
        // Проверка на самопересечение
        if (snake.stream().anyMatch(p -> p.equals(head))) {
            gameOver = true;
            return;
        }
    
        snake.add(0, head);
        if (head.equals(target)) {
            score += 10;
            generateNewTarget();
        } else {
            snake.remove(snake.size() - 1);
        }
    }
}