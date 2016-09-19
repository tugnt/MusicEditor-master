package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * represents a basic music model
 */
public class MusicModel implements IMusicModel {

  private int time;
  private List<Queue<Note>> notes;
  private int totalSize;
  private int tempo;

  /**
   * constructs a music model
   */
  public MusicModel() {
    this.time = 0;
    this.notes = new ArrayList<>();
    for (int q = 0; q < 12; q++) {
      notes.add(new PriorityQueue<>());
    }
    int totalSize = 0;

    tempo = 0;
  }


  /**
   * constructs a note out of the things that determine how a note is
   *
   * @param tone     the tone of the note
   * @param duration the duration, in beats, of the note
   * @param octave   the octave of the note
   */
  public void write(Tone tone, int duration, int octave) {
    if (tone == null) throw new IllegalArgumentException("no null");
    if (octave < 1 || octave > 10) throw new IllegalArgumentException("invalid range octave");
    if (octave == 10 && tone.ordinal() > 7) throw new IllegalArgumentException("nah");
    if (duration < 1) throw new IllegalArgumentException("invalid duration: " + duration);
    totalSize++;
    notes.get(tone.ordinal()).add(new Note(tone, duration, octave, getCurrentTime()));
  }

  public void write(Tone tone, int duration, int octave, int volume) {
    if (tone == null) throw new IllegalArgumentException("no null");
    if (octave < 1 || octave > 10) throw new IllegalArgumentException("invalid range octave");
    if (octave == 10 && tone.ordinal() > 7) throw new IllegalArgumentException("nah");
    if (duration < 1) throw new IllegalArgumentException("invalid duration: " + duration);
    totalSize++;
    notes.get(tone.ordinal()).add(new Note(tone, duration, octave, getCurrentTime(), volume));
  }

  public void write(Tone tone, int duration, int octave, int volume, int instrument) {
    if (tone == null) throw new IllegalArgumentException("no null");
    if (octave < 1 || octave > 10) throw new IllegalArgumentException("invalid range octave");
    if (octave == 10 && tone.ordinal() > 7) throw new IllegalArgumentException("nah");
    if (duration < 0) throw new IllegalArgumentException("invalid duration: " + duration);
    if (instrument < 0) throw new IllegalArgumentException("ewfrgethryjt");
    totalSize++;
    notes.get(tone.ordinal())
            .add(new Note(tone, duration, octave, getCurrentTime(), volume, instrument));
  }


  /**
   * edits the note equivalent to old and replaces it with new; if old note doesn't exist, it won't
   * change anything
   *
   * @param oldNote the note to be replaced
   * @param newNote the note to replace with
   */
  public void edit(Note oldNote, Note newNote) {
    if (totalSize == 0) throw new IllegalArgumentException("no");

    int p1 = oldNote.getTone().ordinal();
    int p2 = newNote.getTone().ordinal();

    if (notes.get(p1).remove(oldNote)) {
      notes.get(p2).add(newNote);
    }
  }

  /**
   * removes the given note from the piece, if the note doesn't exist, it does nothing
   *
   * @param note the note to be removed
   */
  public void remove(Note note) {
    if (totalSize == 0) throw new IllegalArgumentException("no");

    int place = note.getTone().ordinal();
    if (notes.get(place).remove(note)) {
      totalSize--;
    }
  }

  /**
   * Returns the current song, as a 2D List of notes
   *
   * @return A 2D list of notes representing the current song
   */
  public List<List<Note>> getMusic() {
    List<List<Note>> cc = new ArrayList<>();

    Object[] temp;

    for (int i = 0; i < 12; i++) {
      int len = notes.get(i).size();
      temp = notes.get(i).toArray();
      Note n;
      Note n1;

      cc.add(new ArrayList<>());
      for (int j = 0; j < len; j++) {
        n1 = (Note) temp[j];
        n = new Note(n1.getTone(), n1.getDuration(), n1.getOctave(), n1.getStart(),
                n1.getVolume(), n1.getInstrument());
        cc.get(i).add(n);
      }
    }
    return cc;
  }

  /**
   * returns true if there are notes left to play
   */
  @Override
  public boolean isPieceOver() {
    for (int i = 0; i < 12; i++) {
      if (notes.get(i).size() > 0) return false;
    }
    return true;
  }

  /**
   * advances the beat by one
   */
  @Override
  public void advance() {
    time++;
  }

  /**
   * reduces the beat by one
   */
  public void back() {
    time--;
  }

  /**
   * gets the current time
   */
  public int getCurrentTime() {
    return time;
  }

  /**
   * combines this piece and the given piece into one by playing them simultaneously
   *
   * NOTE: if making implementations of this interface it is highly recommended you overwrite this
   * yes, I know it's stupid, but I'll come up with a better idea when I have time
   *
   * @param music the given music to be played with this music
   */
  public void combine(IMusicModel music) {
    List<List<Note>> second = music.getMusic();
    int len;

    for (int i = 0; i < 12; i++) {
      len = second.get(i).size();
      for (int j = 0; j < len; j++) {
        notes.get(i).add(second.get(i).get(j));
        totalSize++;
      }

    }
  }

  /**
   * appends the given piece to the end of this piece
   *
   * @param music the given music to be played after this music
   */
  public void append(IMusicModel music) {
    List<List<Note>> second = music.getMusic();
    int end = getTotalTime();

    int len;
    Note n;

    for (int i = 0; i < 12; i++) {
      len = second.get(i).size();
      for (int j = 0; j < len; j++) {
        n = second.get(i).get(j);
        notes.get(i).add(new Note(n.getTone(), n.getDuration(), n.getOctave(), n.getStart() + end));
        totalSize++;
      }
    }
  }

  /**
   * gets the amount of time this song takes to play
   */
  public int getTotalTime() {
    List<List<Note>> first = getMusic();
    int end = 0;
    int l = 0;
    for (int a1 = 0; a1 < 12; a1++) {
      l = first.get(a1).size();
      for (int a2 = 0; a2 < l; a2++) {
        if (first.get(a1).get(a2).getStart() + first.get(a1).get(a2).getDuration() > end) {
          end = first.get(a1).get(a2).getStart() + first.get(a1).get(a2).getDuration();
        }
      }
    }

    return end;
  }


  /**
   * used to write a note at the specified time.
   */

  public void writeTime(Tone tone, int duration, int octave, int start, int volume,
                        int instrument) {
    if (start < 0) throw new IllegalArgumentException("invalid time");
    int temp = time;
    time = start;
    write(tone, duration, octave, volume, instrument);
    time = temp;
  }

  /**
   * gets the tempo
   */
  public int getTempo() {
    return tempo;
  }

  /**
   * sets tempo
   */
  public void setTempo(int t) {
    tempo = t;
  }


  /**
   * a string representation, looks like the thing on the website
   */
  @Override
  public String toString() {
    String res = "╔";

    int maxO = -1;
    int minO = 11;
    int maxT = 0;
    int minT = 11;

    List<List<Note>> cc = getMusic();


    int len;
    Note n;
    for (int i = 0; i < 12; i++) {
      len = cc.get(i).size();
      for (int j = 0; j < len; j++) {
        n = cc.get(i).get(j);
        if (n.getOctave() >= maxO) {
          maxO = n.getOctave();
        }
        if (n.getOctave() <= minO) {
          minO = n.getOctave();
        }
      }


    }

    for (int a4 = 0; a4 < 12; a4++) {
      len = cc.get(a4).size();
      for (int a5 = 0; a5 < len; a5++) {
        n = cc.get(a4).get(a5);
        if (n.getOctave() == maxO) {
          if (n.getTone().ordinal() > maxT) maxT = n.getTone().ordinal();
        }
        if (n.getOctave() == minO) {
          if (n.getTone().ordinal() < minT) minT = n.getTone().ordinal();
        }
      }
    }

    int end = getTotalTime();

    String lastLineHolder = "╚";

    int wid = ("" + end).length();
    for (int s = 0; s < wid; s++) {
      res += "═";
      lastLineHolder += "═";
    }


    int width = (12 - minT) + (1 + maxT) + (12 * (maxO - minO - 1));
    for (int a0 = 0; a0 < width; a0++) {
      res += "═════";
      lastLineHolder += "═════";
    }
    res += "╗\n║";
    lastLineHolder += "╝";

    for (int s = 0; s < wid; s++) {
      res += " ";
    }

    int currO = minO;
    int currT = minT;
    for (int a1 = 0; a1 < width; a1++) {
      if (currT == 1 || currT == 3 || currT == 6 || currT == 8 || currT == 10) res += " ";
      else if (currO == 10) res += " ";
      else res += "  ";
      switch (currT) {
        case 0:
          res += "C";
          break;
        case 1:
          res += "C#";
          break;
        case 2:
          res += "D";
          break;
        case 3:
          res += "D#";
          break;
        case 4:
          res += "E";
          break;
        case 5:
          res += "F";
          break;
        case 6:
          res += "F#";
          break;
        case 7:
          res += "G";
          break;
        case 8:
          res += "G#";
          break;
        case 9:
          res += "A";
          break;
        case 10:
          res += "A#";
          break;
        case 11:
          res += "B";
          break;
      }
      if ((currO == 10) && (currT == 1 || currT == 3 || currT == 6 || currT == 8 || currT == 10))
        res += "10";
      else res += currO + " ";

      if (currT == 11) {
        currT = 0;
        currO++;
      } else currT++;
    }

    res += "║\n";


    int currBeat = 0;
    boolean breaker = false;
    boolean winner = false;


    boolean tailCheck = false;
    boolean headCheck = false;


    int count = 0; // count notes processed
    while (count != totalSize) {
      res += "║";
      if ((currBeat + "").length() < wid) {
        for (int d = (currBeat + "").length(); d < wid; d++) {
          res += " ";
        }
      }
      res += "" + currBeat;

      currO = minO;
      currT = minT;

      for (int a = 0; a < width; a++) {

        tailCheck = false;
        headCheck = false;
        len = cc.get(currT).size();
        winner = false;
        for (int b = 0; b < len; b++) {
          n = cc.get(currT).get(b);
          if (n.getStart() <= currBeat &&
                  n.getOctave() == currO && n.getStart() + n.getDuration() - 1 >= currBeat) {
            winner = true;
            if (n.getStart() == currBeat) {
              headCheck = true;
            } else tailCheck = true;
          }
          if (n.getStart() + n.getDuration() == currBeat) {
            cc.get(currT).remove(b);
            count++;
            len--;
            if (b > 1) b -= 2;
            else b = -1;
          }
        }
        if (headCheck) res += "  X  ";
        if (!headCheck && tailCheck) res += "  |  ";
        if (!winner) res += "     ";
        if (currT == 11) {
          currT = 0;
          currO++;
        } else currT++;
      }
      currBeat++;
      res += "║\n";


    }

    return res + lastLineHolder + "\n";
  }

}
