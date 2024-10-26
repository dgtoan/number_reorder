package model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Room implements Serializable {
    @Serial
    private static final long serialVersionUID = 1905L;

    private int id;  // Kiểu int thay vì String
    private int firstPlayer;
    private int secondPlayer;
    private int timeLeft;
    private LocalDateTime createAt;
    private LocalDateTime endAt;
    private int winner;
    private String firstProblem;
    private String firstArray;
    private String secondProblem;
    private String secondArray;
    private int eloChange;
    private int totalNumbers;

    // Constructor
    public Room(int id, int firstPlayer, int secondPlayer, int timeLeft, LocalDateTime createAt, LocalDateTime endAt, int winner, String firstArray, String secondArray, int eloChange, int totalNumbers, String firstProblem, String secondProblem) {
        this.id = id;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.timeLeft = timeLeft;
        this.createAt = createAt;
        this.endAt = endAt;
        this.winner = winner;
        this.firstArray = firstArray;
        this.secondArray = secondArray;
        this.eloChange = eloChange;
        this.totalNumbers = totalNumbers;
        this.firstProblem = firstProblem;
        this.secondProblem = secondProblem;
    }

    public Room(int firstPlayer, int secondPlayer, int timeLeft, int totalNumbers) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.timeLeft = timeLeft;
        this.totalNumbers = totalNumbers;
        this.winner = -1;
        this.eloChange = 0;
        this.firstArray = "";
        this.secondArray = "";
        this.createAt = LocalDateTime.now();
        this.endAt = null;
        this.firstProblem = getTheProblem(totalNumbers);
        this.secondProblem = getTheProblem(totalNumbers);

    }

    private String getTheProblem(int totalNumbers) {
        ArrayList<Integer> problemArray = new ArrayList<>();
        for (int i = 0; i < totalNumbers; i++) {
            problemArray.add(i);
        }
        for (int i = 0; i < totalNumbers; i++) {
            int randomIndexToSwap = (int) (Math.random() * totalNumbers);
            int temp = problemArray.get(randomIndexToSwap);
            problemArray.set(randomIndexToSwap, problemArray.get(i));
            problemArray.set(i, temp);
        }
        return String.join(",", problemArray.toString());
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(int firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public int getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(int secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public String getFirstArray() {
        return firstArray;
    }

    public void setFirstArray(String firstArray) {
        this.firstArray = firstArray;
    }

    public String getSecondArray() {
        return secondArray;
    }

    public void setSecondArray(String secondArray) {
        this.secondArray = secondArray;
    }

    public int getEloChange() {
        return eloChange;
    }

    public void setEloChange(int eloChange) {
        this.eloChange = eloChange;
    }

    public int getTotalNumbers() {
        return totalNumbers;
    }

    public void setTotalNumbers(int totalNumbers) {
        this.totalNumbers = totalNumbers;
    }

    public String getFirstProblem() {
        return firstProblem;
    }

    public void setFirstProblem(String firstProblem) {
        this.firstProblem = firstProblem;
    }

    public String getSecondProblem() {
        return secondProblem;
    }

    public void setSecondProblem(String secondProblem) {
        this.secondProblem = secondProblem;
    }
}
