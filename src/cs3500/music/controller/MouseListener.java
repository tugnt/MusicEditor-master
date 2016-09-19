package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.util.Map;

/**
 *
 */
public class MouseListener implements java.awt.event.MouseListener {

  private Map<MouseEvent, Runnable> mouseClickedMap;
  private Controller c;

  public MouseListener(Controller c) {
    this.c = c;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Integer x = e.getXOnScreen();
    Integer y = e.getYOnScreen();
    if (c.getMode() != null) {
      c.getMode().run();
    }

  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
