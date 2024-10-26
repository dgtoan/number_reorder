package model;

import java.io.Serial;
import java.io.Serializable;

public class Account implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1906L;
    private String username;
    private String pass;
    private int playerId;

    // Constructor
    public Account(String username, String password, int playerId) {
        this.username = username;
        this.pass = password;
        this.playerId = playerId;
    }

    public Account(String username, String password) {
        this.username = username;
        this.pass = password;
    }

    // Getters và Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return pass;
    }

    public void setPassword(String password) {
        this.pass = password;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
