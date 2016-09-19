package cs3500.music;

import cs3500.music.controller.Controller;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Tone;
import cs3500.music.util.MusicModelBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.CompositeView;
import cs3500.music.view.GuiView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.StringView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    MusicReader mr = new MusicReader();
    Scanner init = new Scanner(System.in);
    System.out.print("Enter the file you want to play: ");
    String file = init.nextLine();
    System.out.print("Enter the view to display: ");
    String view = init.nextLine();
    IMusicModel m = mr.parseFile(new FileReader(file), new MusicModelBuilder());
    StringView strView = null;
    GuiViewFrame guiView = null;
    MidiViewImpl midiView = null;
    CompositeView compositeView = null;
    Controller c;

    switch (view) {
      case "console":
        strView = new StringView(m);
        c = new Controller(m, strView);

        System.out.print(strView.getText());
        break;
      case "gui":
        guiView = new GuiViewFrame(m);
        c = new Controller(m, guiView);


        break;
      case "midi":
        midiView = new MidiViewImpl(m);
        c = new Controller(m, midiView);

        try {
          Thread.sleep(m.getTotalTime() * 200);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        break;
      case "composite":
        guiView = new GuiViewFrame(m);
        midiView = new MidiViewImpl(m);
        compositeView = new CompositeView(guiView, midiView);
        c = new Controller(m, compositeView);
      default:
        System.out.print("Invaid view");
        break;
    }


  }/*
  public static void main(String[] args) {
    IMusicModel m = null;
    try {
      m = new MusicReader().parseFile(new FileReader("mary-little-lamb.txt"), new MusicModelBuilder());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

   // GuiView gv = new GuiViewFrame(m);

    CompositeView gv = new CompositeView(new GuiViewFrame(m), new MidiViewImpl(m));

    //MidiViewImpl gv = new MidiViewImpl(m);
    Controller c = new Controller(m, gv);


  }*/
}
