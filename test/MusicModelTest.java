import org.junit.Test;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.Synthesizer;

import cs3500.music.controller.Controller;
import cs3500.music.controller.KeyboardHandler;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Tone;
import cs3500.music.view.GuiView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.StringView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


/**
 * tests for music model
 */
public class MusicModelTest {

  // test for notes

  @Test
  public void testNoteEquality() {
    Note n1 = new Note(Tone.A, 1, 3, 0);

    Note n2 = new Note(Tone.A, 1, 3, 0);

    Note n3 = new Note(Tone.B, 1, 3, 0);

    assertEquals(n1.equals(n2), true);
    assertNotEquals(n2.equals(n3), true);
  }


  // test for music models

  // tests for music model construction

  @Test
  public void testStringBasic() {
    MusicModel mm = new MusicModel();


    assertEquals("╔═╗\n" +
            "║ ║\n" +
            "╚═╝\n", mm.toString());
  }


  // test for write

  @Test
  public void testWriteBasic() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 1, 3);


    assertEquals(1, mm.getMusic().get(9).size());
    assertEquals(true, mm.getMusic().get(9).get(0).equals(new Note(Tone.A, 1, 3, 0)));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testWriteExc0() {
    MusicModel mm = new MusicModel();

    mm.write(null, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWriteExc2() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWriteExc3() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 1, 11);
  }

  @Test
  public void testWriteBasic2() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 1, 3);
    mm.write(Tone.D, 2, 3);
    mm.advance();
    mm.write(Tone.A, 1, 3);


    assertEquals(2, mm.getMusic().get(9).size());
    assertEquals(true, mm.getMusic().get(9).get(0).equals(new Note(Tone.A, 1, 3, 0)));
    assertEquals(true, mm.getMusic().get(9).get(1).equals(new Note(Tone.A, 1, 3, 1)));

  }


  // test toString

  @Test
  public void testStringBasic2() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 1, 3);
    mm.write(Tone.Dsharp, 2, 3);
    mm.advance();
    mm.write(Tone.B, 1, 4);


    assertEquals(
            "╔══════════════════════════════════════════════════════════════════════════════" +
                    "════════════════════════════╗\n" +
                    "║  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4" +
                    "   F4  F#4   G4  G#4   A4  A#4   B4 ║\n" +
                    "║0  X                             X                                    " +
                    "                                    ║\n" +
                    "║1  |                                                                  " +
                    "                                 X  ║\n" +
                    "║2                                                                     " +
                    "                                    ║\n" +
                    "╚══════════════════════════════════════════════════════════════════════" +
                    "════════════════════════════════════╝\n", mm.toString());
  }

  @Test
  public void testBeatSpacingCorrect() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 1, 3);
    mm.write(Tone.D, 2, 3);
    mm.advance();
    mm.write(Tone.B, 12, 4);

    assertEquals(
            "╔══════════════════════════════════════════════════════════════════════════════" +
                    "══════════════════════════════════╗\n" +
                    "║    D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  " +
                    "D#4   E4   F4  F#4   G4  G#4   A4  A#4   B4 ║\n" +
                    "║ 0  X                                  X                             " +
                    "                                           ║\n" +
                    "║ 1  |                                                                " +
                    "                                        X  ║\n" +
                    "║ 2                                                                   " +
                    "                                        |  ║\n" +
                    "║ 3                                                                   " +
                    "                                        |  ║\n" +
                    "║ 4                                                                   " +
                    "                                        |  ║\n" +
                    "║ 5                                                                   " +
                    "                                        |  ║\n" +
                    "║ 6                                                                   " +
                    "                                        |  ║\n" +
                    "║ 7                                                                   " +
                    "                                        |  ║\n" +
                    "║ 8                                                                   " +
                    "                                        |  ║\n" +
                    "║ 9                                                                    " +
                    "                                       |  ║\n" +
                    "║10                                                                    " +
                    "                                       |  ║\n" +
                    "║11                                                                    " +
                    "                                       |  ║\n" +
                    "║12                                                                    " +
                    "                                       |  ║\n" +
                    "║13                                                                    " +
                    "                                          ║\n" +
                    "╚═════════════════════════════════════════════════════════════════════" +
                    "═══════════════════════════════════════════╝\n", mm.toString());
  }


  @Test
  public void testStringOverlappingNotes() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 3, 3);
    mm.write(Tone.D, 2, 3);
    mm.advance();
    mm.write(Tone.B, 12, 4);
    mm.write(Tone.A, 3, 3);

    assertEquals(
            "╔═══════════════════════════════════════════════════════════════════════════════" +
                    "═════════════════════════════════╗\n" +
                    "║    D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4 " +
                    "  E4   F4  F#4   G4  G#4   A4  A#4   B4 ║\n" +
                    "║ 0  X                                  X                                " +
                    "                                        ║\n" +
                    "║ 1  |                                  X                                " +
                    "                                     X  ║\n" +
                    "║ 2                                     |                                " +
                    "                                     |  ║\n" +
                    "║ 3                                     |                                " +
                    "                                     |  ║\n" +
                    "║ 4                                                                      " +
                    "                                     |  ║\n" +
                    "║ 5                                                                      " +
                    "                                     |  ║\n" +
                    "║ 6                                                                      " +
                    "                                     |  ║\n" +
                    "║ 7                                                                      " +
                    "                                     |  ║\n" +
                    "║ 8                                                                      " +
                    "                                     |  ║\n" +
                    "║ 9                                                                      " +
                    "                                     |  ║\n" +
                    "║10                                                                      " +
                    "                                     |  ║\n" +
                    "║11                                                                      " +
                    "                                     |  ║\n" +
                    "║12                                                                      " +
                    "                                     |  ║\n" +
                    "║13                                                                      " +
                    "                                        ║\n" +
                    "╚════════════════════════════════════════════════════════════════════════" +
                    "════════════════════════════════════════╝\n", mm.toString());
  }


  @Test
  public void testDuplicateNote() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 3, 3);
    mm.write(Tone.D, 2, 3);
    mm.write(Tone.A, 3, 3);

    mm.advance();
    mm.write(Tone.B, 12, 4);

    assertEquals("╔═════════════════════════════════════════════════════════════════════════" +
            "═══════════════════════════════════════╗\n" +
            "║    D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4  " +
            " D4  D#4   E4   F4  F#4   G4  G#4   A4  A#4   B4 ║\n" +
            "║ 0  X                                  X                        " +
            "                                                ║\n" +
            "║ 1  |                                  |                        " +
            "                                             X  ║\n" +
            "║ 2                                     |                        " +
            "                                             |  ║\n" +
            "║ 3                                                               " +
            "                                            |  ║\n" +
            "║ 4                                                              " +
            "                                             |  ║\n" +
            "║ 5                                                              " +
            "                                             |  ║\n" +
            "║ 6                                                             " +
            "                                              |  ║\n" +
            "║ 7                                                             " +
            "                                              |  ║\n" +
            "║ 8                                                             " +
            "                                              |  ║\n" +
            "║ 9                                                             " +
            "                                              |  ║\n" +
            "║10                                                             " +
            "                                              |  ║\n" +
            "║11                                                              " +
            "                                             |  ║\n" +
            "║12                                                              " +
            "                                             |  ║\n" +
            "║13                                                              " +
            "                                                ║\n" +
            "╚════════════════════════════════════════════════════════════════" +
            "════════════════════════════════════════════════╝\n", mm.toString());
  }


  // test remove


  @Test
  public void testRemoveBasic() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 3, 3);
    mm.write(Tone.D, 2, 3);
    mm.advance();
    mm.write(Tone.B, 12, 4);
    mm.write(Tone.A, 43, 3);

    mm.remove(new Note(Tone.A, 43, 3, 1));

    assertEquals(1, mm.getMusic().get(9).size());

    assertEquals("╔══════════════════════════════════════════════════════════════════════" +
            "══════════════════════════════════════════╗\n" +
            "║    D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4  " +
            " E4   F4  F#4   G4  G#4   A4  A#4   B4 ║\n" +
            "║ 0  X                                  X                                    " +
            "                                    ║\n" +
            "║ 1  |                                  |                                    " +
            "                                 X  ║\n" +
            "║ 2                                     |                                    " +
            "                                 |  ║\n" +
            "║ 3                                                                          " +
            "                                 |  ║\n" +
            "║ 4                                                                            " +
            "                               |  ║\n" +
            "║ 5                                                                             " +
            "                              |  ║\n" +
            "║ 6                                                                            " +
            "                               |  ║\n" +
            "║ 7                                                                            " +
            "                               |  ║\n" +
            "║ 8                                                                             " +
            "                              |  ║\n" +
            "║ 9                                                                             " +
            "                              |  ║\n" +
            "║10                                                                             " +
            "                              |  ║\n" +
            "║11                                                                             " +
            "                              |  ║\n" +
            "║12                                                                              " +
            "                             |  ║\n" +
            "║13                                                                             " +
            "                                 ║\n" +
            "╚═══════════════════════════════════════════════════════════════════════════════" +
            "═════════════════════════════════╝\n", mm.toString());
  }

  @Test
  public void testRemovalUpdatesStringOutputCorrectly() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 3, 3);
    mm.write(Tone.D, 2, 3);
    mm.advance();
    mm.write(Tone.B, 12, 4);

    assertEquals("╔══════════════════════════════════════════════════════════════════════" +
            "══════════════════════════════════════════╗\n" +
            "║    D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4  " +
            " E4   F4  F#4   G4  G#4   A4  A#4   B4 ║\n" +
            "║ 0  X                                  X                             " +
            "                                           ║\n" +
            "║ 1  |                                  |                         " +
            "                                            X  ║\n" +
            "║ 2                                     |                  " +
            "                                                   |  ║\n" +
            "║ 3                                                        " +
            "                                                   |  ║\n" +
            "║ 4                                                            " +
            "                                               |  ║\n" +
            "║ 5                                                                 " +
            "                                          |  ║\n" +
            "║ 6                                                                  " +
            "                                         |  ║\n" +
            "║ 7                                                                         " +
            "                                  |  ║\n" +
            "║ 8                                                                         " +
            "                                  |  ║\n" +
            "║ 9                                                                         " +
            "                                  |  ║\n" +
            "║10                                                                            " +
            "                               |  ║\n" +
            "║11                                                                            " +
            "                               |  ║\n" +
            "║12                                                                             " +
            "                              |  ║\n" +
            "║13                                                                             " +
            "                                 ║\n" +
            "╚══════════════════════════════════════════════════════════════════════════════" +
            "══════════════════════════════════╝\n", mm.toString());


    mm.remove(new Note(Tone.A, 12, 3, 1));

    assertEquals("╔═══════════════════════════════════════════════════════════════════════════" +
            "═════════════════════════════════════╗\n" +
            "║    D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   " +
            "F4  F#4   G4  G#4   A4  A#4   B4 ║\n" +
            "║ 0  X                                  X                                        " +
            "                                ║\n" +
            "║ 1  |                                  |                                        " +
            "                             X  ║\n" +
            "║ 2                                     |                                       " +
            "                              |  ║\n" +
            "║ 3                                                                              " +
            "                             |  ║\n" +
            "║ 4                                                                              " +
            "                             |  ║\n" +
            "║ 5                                                                              " +
            "                             |  ║\n" +
            "║ 6                                                                             " +
            "                              |  ║\n" +
            "║ 7                                                                              " +
            "                             |  ║\n" +
            "║ 8                                                                             " +
            "                              |  ║\n" +
            "║ 9                                                                             " +
            "                              |  ║\n" +
            "║10                                                                             " +
            "                              |  ║\n" +
            "║11                                                                             " +
            "                              |  ║\n" +
            "║12                                                                              " +
            "                             |  ║\n" +
            "║13                                                                             " +
            "                                 ║\n" +
            "╚═══════════════════════════════════════════════════════════════════════════════" +
            "═════════════════════════════════╝\n", mm.toString());
  }


  // tests for edit


  // note: this particular test also tests that overlapping notes display correctly
  @Test
  public void testEditDoesntAffectSize() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 3, 3);
    mm.write(Tone.A, 2, 3);
    mm.advance();
    mm.write(Tone.A, 12, 4);

    mm.edit(new Note(Tone.A, 12, 4, 1), new Note(Tone.A, 2, 4, 1));

    assertEquals(mm.getMusic().get(9).size(), 3);

    assertEquals("╔══════════════════════════════════════════════════════════════════╗\n" +
                    "║   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4   G4  G#4   A4 ║\n" +
                    "║0  X                                                              ║\n" +
                    "║1  |                                                           X  ║\n" +
                    "║2  |                                                           |  ║\n" +
                    "║3                                                                 ║\n" +
                    "╚══════════════════════════════════════════════════════════════════╝\n",
            mm.toString());

  }

  @Test
  public void testEditDoesntChangeIfInvalidNote() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 3, 3);
    mm.write(Tone.A, 2, 3);
    mm.advance();
    mm.write(Tone.A, 4, 4);

    mm.edit(new Note(Tone.A, 4, 4, 1), new Note(Tone.A, 12, 4, 1));

    assertEquals("╔═══════════════════════════════════════════════════════════════════╗\n" +
                    "║    A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4   G4  G#4   A4 ║\n" +
                    "║ 0  X                                                              ║\n" +
                    "║ 1  |                                                           X  ║\n" +
                    "║ 2  |                                                           |  ║\n" +
                    "║ 3                                                              |  ║\n" +
                    "║ 4                                                              |  ║\n" +
                    "║ 5                                                              |  ║\n" +
                    "║ 6                                                              |  ║\n" +
                    "║ 7                                                              |  ║\n" +
                    "║ 8                                                              |  ║\n" +
                    "║ 9                                                              |  ║\n" +
                    "║10                                                              |  ║\n" +
                    "║11                                                              |  ║\n" +
                    "║12                                                              |  ║\n" +
                    "║13                                                                 ║\n" +
                    "╚═══════════════════════════════════════════════════════════════════╝\n",
            mm.toString());
  }


  @Test
  public void testAddingToFirstInColumn() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 3, 3);
    mm.write(Tone.A, 2, 3);
    mm.advance();
    mm.write(Tone.A, 12, 4);

    mm.edit(new Note(Tone.A, 12, 4, 1), new Note(Tone.C, 2, 4, 4));

    assertEquals("╔═════════════════════╗\n" +
                    "║   A3  A#3   B3   C4 ║\n" +
                    "║0  X                 ║\n" +
                    "║1  |                 ║\n" +
                    "║2  |                 ║\n" +
                    "║3                    ║\n" +
                    "║4                 X  ║\n" +
                    "║5                 |  ║\n" +
                    "║6                    ║\n" +
                    "╚═════════════════════╝\n",
            mm.toString());
  }


  // tests combine

  @Test
  public void testCombine() {
    MusicModel mm1 = new MusicModel();

    MusicModel mm2 = new MusicModel();

    mm1.write(Tone.A, 1, 3);
    mm2.write(Tone.Dsharp, 2, 3);
    mm1.advance();
    mm2.advance();
    mm1.write(Tone.B, 1, 4);
    mm2.write(Tone.E, 4, 3);

    mm1.combine(mm2);

    assertEquals("╔═══════════════════════════════════════════════════════════════════════════" +
            "═══════════════════════════════╗\n" +
            "║  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4" +
            "   G4  G#4   A4  A#4   B4 ║\n" +
            "║0  X                             X                                              " +
            "                          ║\n" +
            "║1  |    X                                                                       " +
            "                       X  ║\n" +
            "║2       |                                                                      " +
            "                           ║\n" +
            "║3       |                                                                      " +
            "                           ║\n" +
            "║4       |                                                                      " +
            "                           ║\n" +
            "║5                                                                              " +
            "                           ║\n" +
            "╚═══════════════════════════════════════════════════════════════════════════════" +
            "═══════════════════════════╝\n", mm1.toString());


  }

  // tests for append


  // not quite working yet
  @Test
  public void testAppendBasic() {
    MusicModel mm1 = new MusicModel();

    MusicModel mm2 = new MusicModel();

    mm1.write(Tone.A, 1, 3);
    mm2.write(Tone.Dsharp, 2, 3);
    mm1.advance();
    mm2.advance();
    mm1.write(Tone.B, 5, 4);
    mm2.write(Tone.E, 4, 3);

    mm1.append(mm2);

    assertEquals("╔══════════════════════════════════════════════════════════════════════════" +
            "═════════════════════════════════╗\n" +
            "║   D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  " +
            "F#4   G4  G#4   A4  A#4   B4 ║\n" +
            "║ 0                                X                                            " +
            "                            ║\n" +
            "║ 1                                                                             " +
            "                         X  ║\n" +
            "║ 2                                                                             " +
            "                         |  ║\n" +
            "║ 3                                                                            " +
            "                          |  ║\n" +
            "║ 4                                                                             " +
            "                         |  ║\n" +
            "║ 5                                                                             " +
            "                         |  ║\n" +
            "║ 6  X                                                                          " +
            "                            ║\n" +
            "║ 7  |    X                                                                      " +
            "                           ║\n" +
            "║ 8       |                                                                      " +
            "                           ║\n" +
            "║ 9       |                                                                      " +
            "                           ║\n" +
            "║10       |                                                                      " +
            "                           ║\n" +
            "║11                                                                              " +
            "                           ║\n" +
            "╚════════════════════════════════════════════════════════════════════════════════" +
            "═══════════════════════════╝\n", mm1.toString());
  }


  @Test
  public void testMHALL() {

    MusicModel m1 = new MusicModel();

    m1.write(Tone.G, 7, 3);
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.C, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 7, 3);
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 3, 4);
    m1.advance();
    m1.advance();
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 8, 3);
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 4, 4);
    m1.advance();
    m1.advance();
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 2, 3);
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 4, 4);
    m1.advance();
    m1.advance();
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 8, 3);
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.C, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 8, 3);
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 8, 3);
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 8, 3);
    m1.write(Tone.C, 8, 4);


    assertEquals("╔══════════════════════════════════════════════════════════════════════════════════╗\n" +
            "║    E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4   G4 ║\n" +
            "║ 0                 X                                            X                 ║\n" +
            "║ 1                 |                                            |                 ║\n" +
            "║ 2                 |                                  X                           ║\n" +
            "║ 3                 |                                  |                           ║\n" +
            "║ 4                 |                        X                                     ║\n" +
            "║ 5                 |                        |                                     ║\n" +
            "║ 6                 |                                  X                           ║\n" +
            "║ 7                                                    |                           ║\n" +
            "║ 8                 X                                            X                 ║\n" +
            "║ 9                 |                                            |                 ║\n" +
            "║10                 |                                            X                 ║\n" +
            "║11                 |                                            |                 ║\n" +
            "║12                 |                                            X                 ║\n" +
            "║13                 |                                            |                 ║\n" +
            "║14                 |                                            |                 ║\n" +
            "║15                                                                                ║\n" +
            "║16                 X                                  X                           ║\n" +
            "║17                 |                                  |                           ║\n" +
            "║18                 |                                  X                           ║\n" +
            "║19                 |                                  |                           ║\n" +
            "║20                 |                                  X                           ║\n" +
            "║21                 |                                  |                           ║\n" +
            "║22                 |                                  |                           ║\n" +
            "║23                 |                                  |                           ║\n" +
            "║24                 X                                            X                 ║\n" +
            "║25                 |                                            |                 ║\n" +
            "║26                                                                             X  ║\n" +
            "║27                                                                             |  ║\n" +
            "║28                                                                             X  ║\n" +
            "║29                                                                             |  ║\n" +
            "║30                                                                             |  ║\n" +
            "║31                                                                             |  ║\n" +
            "║32                 X                                            X                 ║\n" +
            "║33                 |                                            |                 ║\n" +
            "║34                 |                                  X                           ║\n" +
            "║35                 |                                  |                           ║\n" +
            "║36                 |                        X                                     ║\n" +
            "║37                 |                        |                                     ║\n" +
            "║38                 |                                  X                           ║\n" +
            "║39                 |                                  |                           ║\n" +
            "║40                 X                                            X                 ║\n" +
            "║41                 |                                            |                 ║\n" +
            "║42                 |                                            X                 ║\n" +
            "║43                 |                                            |                 ║\n" +
            "║44                 |                                            X                 ║\n" +
            "║45                 |                                            |                 ║\n" +
            "║46                 |                                            X                 ║\n" +
            "║47                 |                                            |                 ║\n" +
            "║48                 X                                  X                           ║\n" +
            "║49                 |                                  |                           ║\n" +
            "║50                 |                                  X                           ║\n" +
            "║51                 |                                  |                           ║\n" +
            "║52                 |                                            X                 ║\n" +
            "║53                 |                                            |                 ║\n" +
            "║54                 |                                  X                           ║\n" +
            "║55                 |                                  |                           ║\n" +
            "║56  X                                       X                                     ║\n" +
            "║57  |                                       |                                     ║\n" +
            "║58  |                                       |                                     ║\n" +
            "║59  |                                       |                                     ║\n" +
            "║60  |                                       |                                     ║\n" +
            "║61  |                                       |                                     ║\n" +
            "║62  |                                       |                                     ║\n" +
            "║63  |                                       |                                     ║\n" +
            "║64                                                                                ║\n" +
            "╚══════════════════════════════════════════════════════════════════════════════════╝\n", m1.toString());
  }


  @Test
  public void testMHALL2() {

    MusicModel m1 = new MusicModel();


    m1.write(Tone.G, 8, 3);
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 4, 4);
    m1.advance();
    m1.advance();
    m1.advance();
    m1.advance();

    m1.write(Tone.G, 2, 3);
    m1.write(Tone.E, 2, 4);


    assertEquals("╔════════════════════════════════════════════════════╗\n" +
            "║    G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4 ║\n" +
            "║ 0  X                                  X            ║\n" +
            "║ 1  |                                  |            ║\n" +
            "║ 2  |                                  X            ║\n" +
            "║ 3  |                                  |            ║\n" +
            "║ 4  |                                  X            ║\n" +
            "║ 5  |                                  |            ║\n" +
            "║ 6  |                                  |            ║\n" +
            "║ 7  |                                  |            ║\n" +
            "║ 8  X                                            X  ║\n" +
            "║ 9  |                                            |  ║\n" +
            "║10                                                  ║\n" +
            "╚════════════════════════════════════════════════════╝\n", m1.toString());
  }


  @Test
  public void testMHALL3() {
    MusicModel m1 = new MusicModel();

    m1.write(Tone.A, 2, 3);
    m1.advance();
    m1.write(Tone.A, 4, 3);
    //  m1.advance();

    assertEquals("╔══════╗\n" +
            "║   A3 ║\n" +
            "║0  X  ║\n" +
            "║1  X  ║\n" +
            "║2  |  ║\n" +
            "║3  |  ║\n" +
            "║4  |  ║\n" +
            "║5     ║\n" +
            "╚══════╝\n", m1.toString());
  }

  @Test
  public void testStrView() {
    MusicModel m1 = new MusicModel();

    m1.write(Tone.G, 7, 3);
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.C, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 7, 3);
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 3, 4);
    m1.advance();
    m1.advance();
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 8, 3);
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 4, 4);
    m1.advance();
    m1.advance();
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 2, 3);
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 4, 4);
    m1.advance();
    m1.advance();
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 8, 3);
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.C, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 8, 3);
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.G, 8, 3);
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.D, 2, 4);
    m1.advance();
    m1.advance();
    m1.write(Tone.E, 8, 3);
    m1.write(Tone.C, 8, 4);

    StringView s = new StringView(m1);
    s.display();
    assertEquals(s.getText(), m1.toString());

  }

  @Test
  public void testMidiView() {
    MusicModel mm = new MusicModel();

    mm.write(Tone.A, 1, 3);
    mm.write(Tone.D, 2, 3);
    mm.advance();
    mm.write(Tone.B, 12, 4);
    MidiViewImpl midi = new MidiViewImpl(mm, "test");
    midi.display();
    Synthesizer s = midi.getSynth();

    assertEquals("Message: [B@3b192d32\n" +
            "Message: [B@16f65612\n" +
            "Message: [B@311d617d\n" +
            "Message: [B@7c53a9eb\n" +
            "Message: [B@ed17bee\n" +
            "Message: [B@2a33fae0\n" +
            "Message: [B@707f7052\n" +
            "Message: [B@11028347\n" +
            "Message: [B@14899482\n", midi.getSynth().toString());


  }

  @Test
  public void testKeyboardHandler() {
    IMusicModel m = new MusicModel();
    m.write(Tone.A, 1, 3);
    m.write(Tone.D, 2, 3);
    m.advance();
    m.write(Tone.B, 12, 4);
    GuiView gui = new GuiViewFrame(m);
    Controller c = new Controller(m, gui);
    KeyboardHandler temp = new KeyboardHandler(c);

    class NextBeat implements Runnable {
      public void run() {
        m.advance();
      }
    }

    Runnable tempNext = new NextBeat();

    Map<Integer, Runnable> tempMap = new HashMap<Integer, Runnable>();
    tempMap.put(KeyEvent.VK_N, tempNext);
    temp.setKeyPressedMap(tempMap);

    assertEquals(temp.getKeyPressedMap().get(KeyEvent.VK_N), tempNext);

  }

  @Test
  public void testController() {
    IMusicModel m = new MusicModel();
    GuiView gui = new GuiViewFrame(m);
    Controller c = new Controller(m, gui);
    KeyboardHandler temp = new KeyboardHandler(c);

    class NextBeat implements Runnable {
      public void run() {
        m.advance();
      }
    }

    Runnable tempNext = new NextBeat();
    Map<Integer, Runnable> tempMap = new HashMap<Integer, Runnable>();
    tempMap.put(KeyEvent.VK_N, tempNext);
    temp.setKeyPressedMap(tempMap);
    c.setKeyboardHandler(temp);
    c.setMode(tempNext);

    assertEquals(c.getMode(), tempNext);

    class NextMeasure implements Runnable {
      public void run() {
        m.advance();
        m.advance();
        m.advance();
        m.advance();
      }
    }

    Runnable tempNextMeasure = new NextMeasure();
    c.setMode(tempNextMeasure);
    assertEquals(c.getMode(), tempNextMeasure);

    assertEquals(c.getView(), gui);

    MidiViewImpl midi = new MidiViewImpl(m);
    c.setView(midi);

    assertEquals(c.getView(), midi);




  }




}
