package view.base;

import model.ObjectWrapper;

import javax.swing.*;

public abstract class BaseView extends JPanel implements ComponentResizeListener  {
    public abstract void onDataReceived(ObjectWrapper data);
}
