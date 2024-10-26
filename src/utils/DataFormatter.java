package utils;

import jdk.jshell.execution.LoaderDelegate;
import main.Application;
import model.Player;

import java.time.LocalDateTime;

public class DataFormatter {
    public static String getStatus(Player player) {
        System.out.println(player);
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
            long seconds = lastSeen == null ? 0 : lastSeen.until(now, java.time.temporal.ChronoUnit.SECONDS);
            if (seconds < 60) {
                return "Just now";
            } else if (seconds < 3600) {
                return seconds / 60 + " minutes ago";
            } else if (seconds < 86400) {
                return seconds / 3600 + " hours ago";
            } else {
                return seconds / 86400 + " days ago";
            }
        }
    }
}
