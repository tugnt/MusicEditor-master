package cs3500.music.view;

import java.awt.event.ActionListener;
import java.util.List;

import javax.sound.midi.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements IView {
  private  IMusicModel m;
  private  Synthesizer synth;
  private  Receiver receiver;
  private Instrument[] band;


  public MidiViewImpl(IMusicModel m) {
    Synthesizer synth1;
    Receiver r1;
    this.m = m;

    try {

      synth1 = MidiSystem.getSynthesizer();
      r1 = synth1.getReceiver();

      synth1.open();

      band = synth1.getAvailableInstruments();

    } catch (MidiUnavailableException e) {
      e.printStackTrace();
      synth1 = null;
      r1 = null;

    }
    synth = synth1;
    receiver = r1;

  }

  public MidiViewImpl(IMusicModel m, String s) {
    if (s == "test") {
      Synthesizer ms;
      Receiver mr;
      this.m = m;

      try {

        ms = new MockSynth(new StringBuilder());
        mr = ms.getReceiver();
        band = MidiSystem.getSynthesizer().getAvailableInstruments();

      } catch (MidiUnavailableException e) {
        e.printStackTrace();
        ms = null;
        mr = null;

      }
      synth = ms;
      receiver = mr;

    } else if (s == "reg") {
      Synthesizer synth1;
      Receiver r1;
      this.m = m;

      try {

        synth1 = MidiSystem.getSynthesizer();
        r1 = synth1.getReceiver();

        synth1.open();

        band = synth1.getAvailableInstruments();

      } catch (MidiUnavailableException e) {
        e.printStackTrace();
        synth1 = null;
        r1 = null;

      }
      synth = synth1;
      receiver = r1;

    }
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library: <ul> <li>{@link
   * MidiSystem#getSynthesizer()}</li> <li>{@link Synthesizer} <ul> <li>{@link
   * Synthesizer#open()}</li> <li>{@link Synthesizer#getReceiver()}</li> <li>{@link
   * Synthesizer#getChannels()}</li> </ul> </li> <li>{@link Receiver} <ul> <li>{@link
   * Receiver#send(MidiMessage, long)}</li> <li>{@link Receiver#close()}</li> </ul> </li> <li>{@link
   * MidiMessage}</li> <li>{@link ShortMessage}</li> <li>{@link MidiChannel} <ul> <li>{@link
   * MidiChannel#getProgram()}</li> <li>{@link MidiChannel#programChange(int)}</li> </ul> </li>
   * </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI"> https://en.wikipedia.org/wiki/General_MIDI
   * </a>
   */

  public void playNote() throws InvalidMidiDataException {
    // shortmessage(state, ~~, pitch, volume)

    //  sequencer.setTempoInMPQ(200000);
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 64, 72);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 64, 72);
    this.receiver.send(start, 0);
    this.receiver.send(stop, 400000);

    start = new ShortMessage(ShortMessage.NOTE_ON, 0, 62, 72);
    stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 62, 72);

    this.receiver.send(start, 400000);

    this.receiver.send(stop, 800000);

    start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 71);
    stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 71);

    this.receiver.send(start, 800000);

    this.receiver.send(stop, 1200000);


    this.receiver.close(); // Only call this once you're done playing *all* notes
  }


  @Override
  public void display() {
    long tempo = m.getTempo();
    List<List<Note>> cc = m.getMusic();

    MidiMessage start;
    MidiMessage stop;
    MidiMessage insChange;

    MidiChannel[] channel = synth.getChannels();


    Note n;
    int len;
    for (int i = 0; i < 12; i++) {
      len = cc.get(i).size();
      for (int j = 0; j < len; j++) {
        n = cc.get(i).get(j);
        try {
          Instrument[] iewf = band;
          synth.loadInstrument(band[n.getInstrument()]);

          insChange = new ShortMessage(ShortMessage.PROGRAM_CHANGE,
                  1,
                  n.getInstrument(),
                  n.getInstrument());
          start = new ShortMessage(ShortMessage.NOTE_ON,
                  1,
                  n.getOctave() * 12 + n.getTone().ordinal() + 12, n.getVolume());
          stop = new ShortMessage(ShortMessage.NOTE_OFF,
                  1,
                  n.getOctave() * 12 + n.getTone().ordinal() + 12, n.getVolume());

          this.receiver.send(insChange, 0);
          this.receiver.send(start, n.getStart() * tempo);
          this.receiver.send(stop, (n.getStart() + n.getDuration()) * tempo);
        } catch (InvalidMidiDataException e) {
          e.printStackTrace();
        }


      }
    }

    this.receiver.close();

  }

  @Override
  public void addActionListener(ActionListener a) {

  }


 /* @Override
  public void addKeyListener(KeyboardHandler kbd) {

  }*/

  @Override
  public void resetFocus() {

  }

  @Override
  public String whatView() {
    return "midi";
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

  public Synthesizer getSynth()
  {
    return this.synth;
  }





}
