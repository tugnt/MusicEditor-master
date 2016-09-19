package cs3500.music.view;

import java.awt.event.ActionListener;

import cs3500.music.model.IMusicModel;

/**
 * represents a view to represent its model in the console
 */
public class StringView implements IView {

  private String text;
  IMusicModel m;

  public StringView(IMusicModel m) {
    this.text = "";
    this.m = m;
  }

  /**
   * Gets the text view
   *
   * @return a string representing the text view
   */
  public String getText() {
    return this.text;
  }


  @Override
  public void display() {
    this.text = m.toString();
  }

  @Override
  public void addActionListener(ActionListener a) {

  }

 /* @Override
  public void addActionListener(ActionListener a) {

  }*/

  /*@Override
  public void addKeyListener(KeyboardHandler kbd) {

  }*/

  @Override
  public void resetFocus() {

  }

  @Override
  public String whatView() {
    return "console";
  }

  @Override
  public void displayAddNote() {

  }

  @Override
  public void displayRemoveNote() {

  }

  @Override
  public void updatePanel(IMusicModel m) {

  }

  @Override
  public void panView(String pan) {

  }

}