package view.base;

import main.Application;
import model.ObjectWrapper;
import view.GameView;

import javax.swing.*;

public abstract class BaseView extends JPanel implements ComponentResizeListener  {
    public abstract void onDataReceivedForView(ObjectWrapper data);

    public void onDataReceived(ObjectWrapper data) {
        // TODO: on invite received => showInviteDialog
        // TODO: on invite declined => showInviteDeclinedDialog
        // TODO: on invite canceled => onInviteCanceled

        onDataReceivedForView(data);
    };

    public void showInviteDialog(String opponentName) {
        Application.getInstance().closeAllDialogs();
        int res = JOptionPane.showConfirmDialog(Application.getInstance(), opponentName + " muốn mời bạn chơi, bạn có đồng ý không?", "Mời chơi", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            // TODO: accept invite => start game
            Application.getInstance().nextTo(new GameView());
        } else {
            // TODO: cancel invite
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
