package control;

import model.IPAddress;
import model.ObjectWrapper;
import view.BaseView;

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

    public ClientControl() {
        myFunction = new ArrayList<ObjectWrapper>();

        System.out.println("Client control created!");
    }

    public boolean openConnection() {
        try {
            socket = new Socket(serverAddress.getHost(), serverAddress.getPort());
            myListening = new ClientListening();
            myListening.start();
            System.out.println("Connected to the server at host: " + serverAddress.getHost() + ", port: " + serverAddress.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error when connecting to the server!");
            return false;
        }
        return true;
    }

    public boolean sendData(Object obj) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
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

    class ClientListening extends Thread {

        public ClientListening() {
            super();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Object obj = ois.readObject();
                    if (obj instanceof ObjectWrapper) {
                        baseView.onDataReceived((ObjectWrapper) obj);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
