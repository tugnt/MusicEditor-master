package cs3500.music.view;

import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.sound.midi.VoiceStatus;

/**
 * Created by brendanreed on 6/19/16.
 */
public class MockSynth implements MidiDevice, Synthesizer {

  protected StringBuilder s;

  public MockSynth(StringBuilder s) {
    this.s = s;
  }



  @Override
  public Info getDeviceInfo() {
    throw new IllegalArgumentException("Unsupported");
  }

  @Override
  public void open() throws MidiUnavailableException {
    throw new IllegalArgumentException("Unsupported");
  }

  @Override
  public void close() {
    throw new IllegalArgumentException("Unsupported");
  }

  @Override
  public boolean isOpen() {
    throw new IllegalArgumentException("Unsupported");
  }

  @Override
  public long getMicrosecondPosition() {
    throw new IllegalArgumentException("Unsupported");
  }

  @Override
  public int getMaxReceivers() {
    throw new IllegalArgumentException("Unsupported");
  }

  @Override
  public int getMaxTransmitters() {
    throw new IllegalArgumentException("Unsupported");
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return new MockRecv(this.s);
  }

  @Override
  public List<Receiver> getReceivers() {
    throw new IllegalArgumentException("Unsupported");
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    throw new IllegalArgumentException("Unsupported");
  }

  @Override
  public List<Transmitter> getTransmitters() {
    throw new IllegalArgumentException("Unsupported");
  }

  @Override
  public int getMaxPolyphony() {
    return 0;
  }

  @Override
  public long getLatency() {
    return 0;
  }

  @Override
  public MidiChannel[] getChannels() {
    return new MidiChannel[0];
  }

  @Override
  public VoiceStatus[] getVoiceStatus() {
    return new VoiceStatus[0];
  }

  @Override
  public boolean isSoundbankSupported(Soundbank soundbank) {
    return false;
  }

  @Override
  public boolean loadInstrument(Instrument instrument) {
    return true;
  }

  @Override
  public void unloadInstrument(Instrument instrument) {

  }

  @Override
  public boolean remapInstrument(Instrument from, Instrument to) {
    return false;
  }

  @Override
  public Soundbank getDefaultSoundbank() {
    return null;
  }

  @Override
  public Instrument[] getAvailableInstruments() {
    try {
      return MidiSystem.getSynthesizer().getAvailableInstruments();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
      System.out.println("lol");
      return null;
    }
  }

  @Override
  public Instrument[] getLoadedInstruments() {
    return new Instrument[0];
  }

  @Override
  public boolean loadAllInstruments(Soundbank soundbank) {
    return false;
  }

  @Override
  public void unloadAllInstruments(Soundbank soundbank) {

  }

  @Override
  public boolean loadInstruments(Soundbank soundbank, Patch[] patchList) {
    return false;
  }

  @Override
  public void unloadInstruments(Soundbank soundbank, Patch[] patchList) {

  }

  @Override
  public String toString() { return s.toString(); }

  public StringBuilder getStringBuilder() {
    return this.s;
  }
}
