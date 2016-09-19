package cs3500.music.model;

import java.util.List;

/**
 * an interface representing basic capabilities of music models
 */
public interface IMusicModel {

  /**
   * constructs a note out of the things that determine how a note is
   *
   * @param tone     the tone of the note
   * @param duration the duration, in beats, of the note
   * @param octave   the octave of the note
   */
  public void write(Tone tone, int duration, int octave);

  public void write(Tone tone, int duration, int octave, int volume);

  public void write(Tone tone, int duration, int octave, int volume, int instrument);

  /**
   * edits the note equivalent to old and replaces it with new
   *
   * @param oldNote the note to be replaced
   * @param newNote the note to replace with
   */
  public void edit(Note oldNote, Note newNote);


  /**
   * removes the given note from the piece
   *
   * @param note the note to be removed
   */
  public void remove(Note note);

  /**
   * gets the current time
   */
  public int getCurrentTime();


  /**
   * combines this piece and the given piece into one by playing them simultaneously
   *
   * @param music the given music to be played with this music
   */
  public void combine(IMusicModel music);


  /**
   * appends the given piece to the end of this piece
   *
   * @param music the given music to be played after this music
   */
  public void append(IMusicModel music);


  /**
   * returns the list of notes the piece contains
   */
  public List<List<Note>> getMusic();

  /**
   * returns true if there are notes left to play
   */
  public boolean isPieceOver();

  /**
   * advances the beat by one
   */
  public void advance();

  /**
   * Gets the total time of the song, in beats
   * @return an int representing the total time of the song
   */
  public int getTotalTime();

  /**
   * Gets the tempo of the song
   * @return an int representing the tempo of the song
   */
  public int getTempo();

  /**
   * Sets the tempo to the input
   * @param t the input tempo
   */
  public void setTempo(int t);

  /**
   * Writes a note at the specified time, accounting for editing in the song
   * @param tone Tone of the note
   * @param duration Duration of the note
   * @param octave Octave of the note
   * @param start Start time of the note
   * @param volume Volume of the note
   * @param instrument Instrument of the note
   */
  public void writeTime(Tone tone, int duration, int octave, int start, int volume, int instrument);


}
