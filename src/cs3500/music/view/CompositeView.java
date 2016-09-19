package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.*;

import cs3500.music.controller.Controller;
import cs3500.music.model.IMusicModel;

/**
 * Created by brendanreed on 6/22/16.
 */
public class CompositeView implements GuiView {

  private GuiView gui;
  private IView midi;

  public CompositeView(GuiView g, IView m) {
    this.gui = g;
    this.midi = m;
  }

  @Override
  public void display() {


    gui.display();

   midi.display();
  }

  public ConcreteGuiViewPanel getPanel() {
    return gui.getPanel();
  }

  @Override
  public void updatePanel(IMusicModel m) {
    gui.updatePanel(m);

  }

  @Override
  public void addActionListener(ActionListener a) {

  }

 /*@Override
  public void addKeyListener(KeyListener listener) {

  }*/

 /* @Override
  public void addActionListener(ActionListener a) {

  }*/

  @Override
  public void addKeyListener(KeyListener listener) {

  }

  @Override
  public void panView(String s) {

  }

  @Override
  public void displayRemoveNote() {
    gui.displayRemoveNote();

  }

  @Override
  public void displayAddNote() {
    gui.displayAddNote();

  }

  @Override
  public void displayLine() {


  }


  @Override
  public void resetFocus() {

  }

  @Override
  public String whatView() {
    return "composite";
  }

  public void setController(Controller c){
    gui.setController(c);
  }

  public Controller getController() {
    return gui.getController();
  }

  @Override
  public String[] getInfo() {
    return gui.getInfo();
  }

  public JFrame getTemp() {
    return gui.getTemp();
  }


}
