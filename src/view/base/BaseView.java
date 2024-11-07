package view.base;

import main.Application;
import model.ObjectWrapper;
import model.Player;
import model.Room;
import view.GameView;
import view.components.AppDialog;
import view.components.AppDialogAction;

import javax.swing.*;
import java.util.Map;

public abstract class BaseView extends JPanel implements ComponentResizeListener {
    public abstract void onDataReceivedForView(ObjectWrapper data);

    public void onDataReceived(ObjectWrapper data) {
        switch (data.getPerformative()) {
            case ObjectWrapper.INVITE -> {
                if (this instanceof GameView) {
                    Application.getInstance().back();
                }

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
                final Room room = (Room) data.getData();

                final int id = Application.getInstance().getCurrentPlayerId();

                if (room.getFirstPlayer() != id && room.getSecondPlayer() != id) {
                    return;
                }

                AppDialog.getInstance().dispose();
                Application.getInstance().nextTo(new GameView());
            }
        }

        onDataReceivedForView(data);
    }

    ;

    public void showInviteDialog(Player from) {
        AppDialog.getInstance().showOptionDialog(
                "Mời chơi",
                from.getPlayerName() + " muốn mời bạn chơi, bạn có đồng ý không?",
                "Đồng ý",
                "Từ chối",
                new AppDialogAction() {
                    @Override
                    public void onYes() {
                        System.out.println("Invite accepted");
                        Map<String, Object> body = Map.of(
                                "playerId", from.getId()
                        );

                        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.ACCEPT_INVITATION, body);
                        Application.getInstance().sendData(data);
                    }

                    @Override
                    public void onNo() {
                        System.out.println("Decline invitation");
                        Map<String, Object> body = Map.of(
                                "playerId", from.getId()
                        );

                        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.DECLINE_INVITATION, body);
                        Application.getInstance().sendData(data);
                    }
                }
        );
    }

    public void showInviteDeclinedDialog(String opponentName) {
        AppDialog.getInstance().showMessageDialog("Mời chơi", opponentName + " đã từ chối mời chơi của bạn");
    }

    // when opponent cancel invite
    public void onInviteCanceled() {
        AppDialog.getInstance().dispose();
    }

    public void initState() {};

    public void onPause() {};

    public void onResume() {};

    public void dispose() {};
}
