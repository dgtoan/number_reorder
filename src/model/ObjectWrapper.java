package model;
 
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class ObjectWrapper implements Serializable {
    @Serial
    private static final long serialVersionUID = 20210811011L;

    public static final int CONNECT = 0;

    public static final int LOGIN = 1;
    public static final int REGISTER = 2;

    public static final int PLAYER = 3;
    public static final int PLAYER_LIST = 4;
    public static final int ROOM = 5;
    public static final int ROOM_LIST = 6;

    public static final int INVITE = 7;
    public static final int CANCEL_INVITATION = 8;
    public static final int ACCEPT_INVITATION = 9;
    public static final int DECLINE_INVITATION = 10;

    public static final int UPDATE_NUMBERS = 11;

    public static final ArrayList<Integer> UNAUTHORIZED = new ArrayList<Integer>() {
        {
            add(LOGIN);
            add(REGISTER);
        }
    };

    private int performative;
    private Object data;
    public ObjectWrapper() {
        super();
    }
    public ObjectWrapper(int performative, Object data) {
        super();
        this.performative = performative;
        this.data = data;
    }
    public int getPerformative() {
        return performative;
    }
    public void setPerformative(int performative) {
        this.performative = performative;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }   
}