package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.*;

import cs3500.music.controller.Controller;
import cs3500.music.model.IMusicModel;

/**
 * Created by brendanreed on 6/21/16.
 */
public interface GuiView extends IView {

  /**
   * this is to force the view to have a method to set up the keyboard. The name has been chosen
   * deliberately. This is the same method signature to add a key listener in Java Swing.
   *
   * Thus our Swing-based implementation of this interface will already have such a method.
   */
  void addKeyListener(KeyListener listener);

 // void addActionListener(ActionListener listener);

  void panView(String s);

  void displayRemoveNote();
  void displayAddNote();

  void displayLine();

  ConcreteGuiViewPanel getPanel();

  void updatePanel(IMusicModel m);

  void setController(Controller c);
  Controller getController();

   String[] getInfo();

  JFrame getTemp();
}
