package view.base;

import main.Application;
import model.ObjectWrapper;
import model.Player;
import view.GameView;

import javax.swing.*;
import java.util.Map;

public abstract class BaseView extends JPanel implements ComponentResizeListener {
    public abstract void onDataReceivedForView(ObjectWrapper data);

    public void onDataReceived(ObjectWrapper data) {
        switch (data.getPerformative()) {
            case ObjectWrapper.INVITE -> {
                int to = (int) ((Map<String, Object>) data.getData()).get("to");
                if (to != Application.getInstance().getCurrentPlayerId()) return;


                Player from = (Player) ((Map<String, Object>) data.getData()).get("from");

                System.out.println("Invite received from " + from.getPlayerName());
                showInviteDialog(from);
            }
            case ObjectWrapper.DECLINE_INVITATION -> {
                int to = (int) ((Map<String, Object>) data.getData()).get("to");
                if (to != Application.getInstance().getCurrentPlayerId()) return;

                Player from = (Player) ((Map<String, Object>) data.getData()).get("from");

                System.out.println("Invite declined from " + from.getPlayerName());
                showInviteDeclinedDialog(from.getPlayerName());
            }
            case ObjectWrapper.CANCEL_INVITATION -> {
                int to = (int) ((Map<String, Object>) data.getData()).get("to");
                if (to != Application.getInstance().getCurrentPlayerId()) return;

                System.out.println("Invite canceled");
                onInviteCanceled();
            }
            case ObjectWrapper.ACCEPT_INVITATION -> {
                Application.getInstance().closeAllDialogs();
                Application.getInstance().nextTo(new GameView());
            }
        }

        onDataReceivedForView(data);
    }

    ;

    public void showInviteDialog(Player from) {
        Application.getInstance().closeAllDialogs();
        int res = JOptionPane.showConfirmDialog(Application.getInstance(), from.getPlayerName() + " muốn mời bạn chơi, bạn có đồng ý không?", "Mời chơi", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            Map<String, Object> body = Map.of(
                    "playerId", Application.getInstance().getCurrentPlayerId()
            );

            ObjectWrapper data = new ObjectWrapper(ObjectWrapper.ACCEPT_INVITATION, body);
            Application.getInstance().sendData(data);
        } else {
            Map<String, Object> body = Map.of(
                    "playerId", Application.getInstance().getCurrentPlayerId()
            );

            ObjectWrapper data = new ObjectWrapper(ObjectWrapper.DECLINE_INVITATION, body);
            Application.getInstance().sendData(data);
        }
    }

    public void showInviteDeclinedDialog(String opponentName) {
        Application.getInstance().closeAllDialogs();
        JOptionPane.showConfirmDialog(Application.getInstance(), opponentName + " đã từ chối mời chơi của bạn", "Mời chơi", JOptionPane.DEFAULT_OPTION);
    }

    // when opponent cancel invite
    public void onInviteCanceled() {
        Application.getInstance().closeAllDialogs();
    }
}
