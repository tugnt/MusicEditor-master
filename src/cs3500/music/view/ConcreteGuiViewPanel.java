package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Tone;

/**
 * A dummy view that simply draws a string
 */
public class ConcreteGuiViewPanel extends JPanel {

  private IMusicModel m; //= new MusicModel();
  private int beat;

  public ConcreteGuiViewPanel(IMusicModel m) {
    this.m = m;
    this.beat = 0;
  }


  @Override
  public void paintComponent(Graphics g) {

    // Handle the default painting
    super.paintComponent(g);

    List<List<Note>> curMusic = m.getMusic();


    int maxO = -1;
    int minO = 11;
    int maxT = 0;
    int minT = 11;


    int len;
    Note n;
    for (int i = 0; i < 12; i++) {
      len = curMusic.get(i).size();
      for (int j = 0; j < len; j++) {
        n = curMusic.get(i).get(j);
        if (n.getOctave() >= maxO) {
          maxO = n.getOctave();
        }
        if (n.getOctave() <= minO) {
          minO = n.getOctave();
        }
      }


    }

    for (int a4 = 0; a4 < 12; a4++) {
      len = curMusic.get(a4).size();
      for (int a5 = 0; a5 < len; a5++) {
        n = curMusic.get(a4).get(a5);
        if (n.getOctave() == maxO) {
          if (n.getTone().ordinal() > maxT) maxT = n.getTone().ordinal();
        }
        if (n.getOctave() == minO) {
          if (n.getTone().ordinal() < minT) minT = n.getTone().ordinal();
        }
      }
    }

    int width = (12 - minT) + (1 + maxT) + (12 * (maxO - minO - 1));


    int totalTime = m.getTotalTime();

    int first = 50;
    for (int i = 0; i <= totalTime; i += 16) {
      String temp = Integer.toString(i);
      g.drawString(temp, 50 + (15 * i), 25);

    }


    int x = 50;
    int y = 30;
    Graphics cube = g.create();

    for (int a0 = 0; a0 < 12; a0++) {
      len = curMusic.get(a0).size();
      for (int a9 = 0; a9 < len; a9++) {
        n = curMusic.get(a0).get(a9);
        cube.setColor(Color.black);
        y = 30 + ((maxO - n.getOctave()) * 12 + (maxT - n.getTone().ordinal())) * 20;
        x = 50 + n.getStart() * 15;
        cube.drawRect(x, y, 15, 20);
        cube.fillRect(x, y, 15, 20);

        cube.setColor(Color.ORANGE);
        cube.drawRect(x + 15, y, 15 * (n.getDuration() - 1), 20);
        cube.fillRect(x + 15, y, 15 * (n.getDuration() - 1), 20);
      }
    }

    for (x = 0; x < totalTime / 4; x++) {
      for (y = 0; y < width; y++) {
        g.drawRect(x * 60 + 50, y * 20 + 30, 60, 20);
      }
    }


    int currO = maxO;
    int currT = maxT;
    String tone = "";
    String oct = "";
    int border = 45;
    for (int a3 = 0; a3 < width; a3++) {
      tone = "";
      oct = "";
      switch (currT) {
        case 0:
          tone += "C";
          break;
        case 1:
          tone += "C#";
          break;
        case 2:
          tone += "D";
          break;
        case 3:
          tone += "D#";
          break;
        case 4:
          tone += "E";
          break;
        case 5:
          tone += "F";
          break;
        case 6:
          tone += "F#";
          break;
        case 7:
          tone += "G";
          break;
        case 8:
          tone += "G#";
          break;
        case 9:
          tone += "A";
          break;
        case 10:
          tone += "A#";
          break;
        case 11:
          tone += "B";
          break;
      }
      oct += currO;

      g.drawString(tone + oct, 25, border);

      if (currT == 0) {
        currT = 11;
        currO--;
        //      g.drawL
      } else currT--;
      border += 20;
    }

    Graphics line = g.create();

    line.setColor(Color.red);


    line.drawRect(50 + (beat * 15), 30, 1, border - +42);
    line.fillRect(50 + (beat * 15), 30, 1, border - +42);




  }


  /**
   * Sets the correct width for the view based on the amount of beats in the song
   *
   * @return an int represeting the proper width of the view
   */
  public int setWidth() {
    return 5 * 15 + m.getTotalTime() * 15;

  }

  /**
   * Sets the correct height for the view based on the amount of unique notes in the song
   *
   * @return an int representing the proper height of the view
   */
  public int setHeight() {
    int x;
    x = m.getMusic().size() * 100 + 5 * 15;
    return x;
  }

  public void updateBeat() {
    this.beat += 1;
    this.repaint();
  }

  public void updatePanel() {


  }


}
