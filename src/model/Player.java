package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Player implements Serializable {
    private int id;  // Kiểu int thay vì String
    private String playerName;
    private int elo;
    private boolean isPlaying;
    private LocalDateTime lastSeen;
    private boolean isOnline;
    private int totalGames;
    private int winGames;

    // Constructor
    public Player(int id, String playerName, int elo, boolean isPlaying, LocalDateTime lastSeen, boolean isOnline, int totalGames, int winGames) {
        this.id = id;
        this.playerName = playerName;
        this.elo = elo;
        this.isPlaying = isPlaying;
        this.lastSeen = lastSeen;
        this.isOnline = isOnline;
        this.totalGames = totalGames;
        this.winGames = winGames;
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

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getWinGames() {
        return winGames;
    }

    public void setWinGames(int winGames) {
        this.winGames = winGames;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", playerName='" + playerName + '\'' +
                ", elo=" + elo +
                ", isPlaying=" + isPlaying +
                ", lastSeen=" + lastSeen +
                ", isOnline=" + isOnline +
                ", totalGames=" + totalGames +
                ", winGames=" + winGames +
                '}';
    }
}
