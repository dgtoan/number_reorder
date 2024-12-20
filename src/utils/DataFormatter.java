package utils;

import jdk.jshell.execution.LoaderDelegate;
import main.Application;
import model.Player;

import java.time.LocalDateTime;

public class DataFormatter {
    public static String getStatus(Player player) {
        int currentPlayerId = Application.getInstance().getCurrentPlayerId();

        if (player.getId() == currentPlayerId) {
            return "You";
        } else if (player.isOnline()) {
            if (player.isPlaying()) {
                return "Playing";
            } else {
                return "Online";
            }
        } else {
            LocalDateTime lastSeen = player.getLastSeen();
            LocalDateTime now = LocalDateTime.now();
            long minutes = lastSeen.until(now, java.time.temporal.ChronoUnit.MINUTES);
            if (minutes < 1) {
                return "Just now";
            } else if (minutes < 60) {
                return minutes + " minutes ago";
            } else {
                long hours = minutes / 60;
                if (hours < 24) {
                    return hours + " hours ago";
                } else {
                    long days = hours / 24;
                    return days + " days ago";
                }
            }
        }
    }
}
