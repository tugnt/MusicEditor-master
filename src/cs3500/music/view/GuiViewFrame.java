package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs3500.music.controller.Controller;
import cs3500.music.model.IMusicModel;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements GuiView {

  protected ConcreteGuiViewPanel displayPanel; // You may want to refine this to a subtype of JPanel
  Controller c;
  JFrame temp;
  JComboBox jcb;
  JTextField jtf1;
  JTextField jtf2;
  JTextField jtf3;
  JTextField jtf4;
  JTextField jtf5;

  /**
   * Creates new GuiView
   */
  public GuiViewFrame(IMusicModel m) {
    this.displayPanel = new ConcreteGuiViewPanel(m);

  }

  //  @Override
  public void initialize() {
    this.setVisible(true);
  }


  /**
   * Displays the GUI view
   */
  @Override
  public void display() {
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    this.displayPanel.setPreferredSize(new Dimension(this.displayPanel.setWidth(),
            this.displayPanel.setHeight()));
    /*
    ButtonGroup addRemove = new ButtonGroup();
    JRadioButton add = new JRadioButton("Add");
    JRadioButton remove = new JRadioButton("Remove");
    addRemove.add(add);
    addRemove.add(remove);
    this.displayPanel.add(add);
    this.displayPanel.add(remove);
    */
    JScrollPane scroll = new JScrollPane(displayPanel);
    scroll.setPreferredSize(new Dimension(800, 400));
    scroll.getHorizontalScrollBar();
    scroll.getVerticalScrollBar();
    this.add(scroll);
    this.pack();
    setVisible(true);

  }

  @Override
  public void addActionListener(ActionListener listener) {

  }

  @Override
  public void panView(String s) {

  }

  @Override
  public void displayRemoveNote() {
    this.temp = new JFrame();
    JPanel main = new JPanel();
    main.setLayout(new FlowLayout());
    JPanel t = new JPanel();
    JButton jb = new JButton();
    t.add(new JLabel("Pick a tone: "));
    DefaultComboBoxModel options = new DefaultComboBoxModel();
    options.addElement("C");
    options.addElement("C#");
    options.addElement("D");
    options.addElement("D#");
    options.addElement("E");
    options.addElement("F");
    options.addElement("F#");
    options.addElement("G");
    options.addElement("G#");
    options.addElement("A");
    options.addElement("A#");
    options.addElement("B");
    this.jcb= new JComboBox(options);
    t.add(jcb);
    JPanel o = new JPanel();
    o.add(new JLabel("Input an Octave: "));
    this.jtf1 = new JTextField();
    jtf1.setColumns(5);
    o.add(jtf1);
    JPanel b = new JPanel();
    b.add(new JLabel("Input number of beats: "));
    this.jtf2 = new JTextField();
    jtf2.setColumns(5);
    b.add(jtf2);
    //JPanel v = new JPanel();
   // v.add(new JLabel("Input volume: "));
    this.jtf3 = new JTextField();
    jtf3.setColumns(5);
   // v.add(jtf3);
  //  JPanel i = new JPanel();
  //  i.add(new JLabel("Input instrument: "));
    this.jtf4 = new JTextField();
    jtf4.setColumns(5);

    JPanel zz = new JPanel();
    zz.add(new JLabel("Input start time: "));
    this.jtf5 = new JTextField();
    jtf5.setColumns(5);
    zz.add(jtf5);
    jb.add(new JLabel("Remove it"));
    jb.setActionCommand("del");
    System.out.println(getController());
    jb.addActionListener(getController());
  //  i.add(jtf4);
  //  main.add(v);
  //  main.add(i);
    main.add(b);
    main.add(t);
    main.add(o);
    main.add(jb);
    main.add(zz);
    temp.add(main);

    temp.setSize(new Dimension(500, 200));
    temp.setVisible(true);



  }

  @Override
  public void displayAddNote() {

    this.temp = new JFrame();
    JPanel main = new JPanel();
    main.setLayout(new FlowLayout());
    JPanel t = new JPanel();
    JButton jb = new JButton();
    t.add(new JLabel("Pick a tone: "));
    DefaultComboBoxModel options = new DefaultComboBoxModel();
    options.addElement("C");
    options.addElement("C#");
    options.addElement("D");
    options.addElement("D#");
    options.addElement("E");
    options.addElement("F");
    options.addElement("F#");
    options.addElement("G");
    options.addElement("G#");
    options.addElement("A");
    options.addElement("A#");
    options.addElement("B");
    this.jcb= new JComboBox(options);
    t.add(jcb);
    JPanel o = new JPanel();
    o.add(new JLabel("Input an Octave: "));
    this.jtf1 = new JTextField();
    jtf1.setColumns(5);
    o.add(jtf1);
    JPanel b = new JPanel();
    b.add(new JLabel("Input number of beats: "));
    this.jtf2 = new JTextField();
    jtf2.setColumns(5);
    b.add(jtf2);
    JPanel v = new JPanel();
    v.add(new JLabel("Input volume: "));
    this.jtf3 = new JTextField();
    jtf3.setColumns(5);
    v.add(jtf3);
    JPanel i = new JPanel();
    i.add(new JLabel("Input instrument: "));
    this.jtf4 = new JTextField();
    jtf4.setColumns(5);

    JPanel zz = new JPanel();
    zz.add(new JLabel("Input start time: "));
    this.jtf5 = new JTextField();
    jtf5.setColumns(5);
    zz.add(jtf5);
    jb.add(new JLabel("Add it"));
    jb.setActionCommand("add");
    System.out.println(getController());
    jb.addActionListener(getController());
    i.add(jtf4);
    main.add(v);
    main.add(i);
    main.add(b);
    main.add(t);
    main.add(o);
    main.add(jb);
    main.add(zz);
    temp.add(main);

    temp.setSize(new Dimension(500, 200));
    temp.setVisible(true);
  }

  @Override
  public void displayLine() {

  }

  @Override
  public ConcreteGuiViewPanel getPanel() {
    return this.displayPanel;
  }

  @Override
  public void updatePanel(IMusicModel m) {
    this.displayPanel.updateBeat();

  }


  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public String whatView() {
    return "gui";
  }

  public Controller getController() {
    return c;
  }
  public void setController(Controller c) {
    this.c = c;
  }

  public JFrame getTemp() { return temp; }

  public String[] getInfo() {
    String[] res = new String[6];
    res[0] = jtf1.getText();
    res[1] = jtf2.getText();
    res[2] = jtf3.getText();
    res[3] = jtf4.getText();
    res[4] = (String) jcb.getSelectedItem();
    res[5] = jtf5.getText();
    return res;
  }

}
