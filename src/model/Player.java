package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Player implements Serializable {
    private int id;  // Kiểu int thay vì String
    private String playerName;
    private int elo;
    private boolean isPlaying;
    private LocalDateTime lastSeen;

    // Constructor
    public Player(int id, String playerName, int elo, boolean isPlaying, LocalDateTime lastSeen) {
        this.id = id;
        this.playerName = playerName;
        this.elo = elo;
        this.isPlaying = isPlaying;
        this.lastSeen = lastSeen;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }
}
