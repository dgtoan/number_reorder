package control;

import model.IPAddress;
import model.ObjectWrapper;
import model.Player;
import view.base.BaseView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientControl {
    private Socket socket;
    private ClientListening myListening;
    private ArrayList<ObjectWrapper> myFunction;
    private IPAddress serverAddress = new IPAddress("localhost", 8001);
    private BaseView baseView;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Integer currentPlayerId;
    private ArrayList<Player> playerList;

    public ClientControl() {
        try {
            myFunction = new ArrayList<ObjectWrapper>();
            openConnection();
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Client control created!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error when creating client control!");
        }
    }

    public void openConnection() {
        try {
            socket = new Socket(serverAddress.getHost(), serverAddress.getPort());
            myListening = new ClientListening();
            myListening.start();
            System.out.println("Connected to the server at host: " + serverAddress.getHost() + ", port: " + serverAddress.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error when connecting to the server!");
        }
    }

    public boolean sendData(Object obj) {
        try {
            oos.writeObject(obj);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Error when sending data to the server!");
            return false;
        }
        return true;
    }

    public boolean closeConnection() {
        try {
            if (myListening != null) {
                myListening.interrupt();
            }
            if (socket != null) {
                socket.close();
                System.out.println("Disconnected from the server!");
            }
            myFunction.clear();
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Error when disconnecting from the server!");
            return false;
        }
        return true;
    }

    public BaseView getBaseView() {
        return baseView;
    }

    public void setBaseView(BaseView baseView) {
        this.baseView = baseView;
    }

    public Integer getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(Integer currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    class ClientListening extends Thread {

        public ClientListening() {
            super();
        }

        @Override
        public void run() {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                while (true) {
                    Object obj = ois.readObject();
                    if (baseView != null & obj instanceof ObjectWrapper) {
                        if (((ObjectWrapper) obj).getPerformative() == ObjectWrapper.LOGIN) {
                            try {
                                Player player = (Player) ((ObjectWrapper) obj).getData();
                                currentPlayerId = player.getId();
                            } catch (Exception e) {
                                System.out.println("Login failed!");
                            }
                        }

                        if (((ObjectWrapper) obj).getPerformative() == ObjectWrapper.PLAYER_LIST) {
                            playerList = (ArrayList<Player>) ((ObjectWrapper) obj).getData();
                        }

                        baseView.onDataReceived((ObjectWrapper) obj);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
