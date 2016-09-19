package cs3500.music.util;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Tone;

/**
 * Created by avparmar on 6/18/16.
 */
public class MusicModelBuilder implements CompositionBuilder<IMusicModel> {
  int tempo;
  List<Note> notes = new ArrayList<>();

  /**
   * Constructs an actual composition, given the notes that have been added
   *
   * @return The new composition
   */
  @Override
  public IMusicModel build() {

    IMusicModel m = new MusicModel();
    m.setTempo(tempo);
    Note n;

    int len = notes.size();
    for (int i = 0; i < len; i++) {
      n = notes.get(i);
      m.writeTime(n.getTone(), n.getDuration(), n.getOctave(),
              n.getStart(), n.getVolume(), n.getInstrument());
    }

    return m;
  }

  /**
   * Sets the tempo of the piece
   *
   * @param tempo The speed, in microseconds per beat
   * @return This builder
   */
  @Override
  public CompositionBuilder<IMusicModel> setTempo(int tempo) {
    this.tempo = tempo;
    return this;
  }

  /**
   * Adds a new note to the piece
   *
   * @param start      The start time of the note, in beats
   * @param end        The end time of the note, in beats
   * @param instrument The instrument number (to be interpreted by MIDI)
   * @param pitch      The pitch (in the range [0, 127], where 60 represents C4, the middle-C on a
   *                   piano)
   * @param volume     The volume (in the range [0, 127])
   */
  @Override
  public CompositionBuilder<IMusicModel> addNote(int start, int end, int instrument,
                                                 int pitch, int volume) {
    Tone t;

    switch (pitch % 12) {
      case 0:
        t = Tone.C;
        break;
      case 1:
        t = Tone.Csharp;
        break;
      case 2:
        t = Tone.D;
        break;
      case 3:
        t = Tone.Dsharp;
        break;
      case 4:
        t = Tone.E;
        break;
      case 5:
        t = Tone.F;
        break;
      case 6:
        t = Tone.Fsharp;
        break;
      case 7:
        t = Tone.G;
        break;
      case 8:
        t = Tone.Gsharp;
        break;
      case 9:
        t = Tone.A;
        break;
      case 10:
        t = Tone.Asharp;
        break;
      case 11:
        t = Tone.B;
        break;
      default:
        t = Tone.C;
    }

    notes.add(new Note(t, end - start, pitch / 12 - 1, start, volume, instrument));
    return this;
  }
}
