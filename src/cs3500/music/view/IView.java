package cs3500.music.view;

import java.awt.event.ActionListener;

import cs3500.music.model.IMusicModel;

/**
 * represents a view
 */
public interface IView {

  /**
   * Displays the view
   */
  void display();

  void addActionListener(ActionListener a);


//  void addKeyListener(KeyboardHandler kbd);

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached to
   * it, so that keyboard events will still flow through.
   */
  void resetFocus();

  String whatView();

  void displayAddNote();

  void displayRemoveNote();

  void updatePanel(IMusicModel m);

  void panView(String pan);
}
