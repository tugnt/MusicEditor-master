package cs3500.music.model;

/**
 * represents a tone
 */
public enum Tone {
  C, Csharp, D, Dsharp, E, F, Fsharp, G, Gsharp, A, Asharp, B;

  public String getString() {
    String result;
    switch (this.ordinal()) {
      case 0:
        result = "C";
        break;
      case 1:
        result = "C#";
        break;
      case 2:
        result = "D";
        break;
      case 3:
        result = "D#";
        break;
      case 4:
        result = "E";
        break;
      case 5:
        result = "F";
        break;
      case 6:
        result = "F#";
        break;
      case 7:
        result = "G";
        break;
      case 8:
        result = "G#";
        break;
      case 9:
        result = "A";
        break;
      case 10:
        result = "A#";
        break;
      case 11:
        result = "B";
        break;
      default:
        result = "Invalid pitch";
        break;
    }
    return result;
  }
}