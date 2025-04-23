package com.example.model;

import java.time.Instant;
import java.util.Date;

public class LeaderboardEntry {
    private final String nickname;
    private final int score;
    private final Instant playedAt;

    public LeaderboardEntry(String nickname, int score, Instant playedAt) {
        this.nickname = nickname;
        this.score = score;
        this.playedAt = playedAt;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }

    /** JSTL <fmt:formatDate> expects java.util.Date */
    public Date getPlayedAt() {
        return Date.from(playedAt);
    }

    /** Convenience for JSON / tests */
    public long getPlayedAtMillis() {
        return playedAt.toEpochMilli();
    }
}