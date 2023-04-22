package com.comp301.a09akari.model;

import java.util.List;
import java.util.ArrayList;

public class ModelImpl implements Model {

  private PuzzleLibrary library;
  private Puzzle puzzle;
  private int activePuzzle;
  private int[][] lights;
  private List<ModelObserver> observers;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) throw new IllegalArgumentException();
    this.library = library;
    this.puzzle = library.getPuzzle(0);
    this.activePuzzle = 0;
    this.lights = new int[puzzle.getHeight()][puzzle.getWidth()];
    for (int r = 0; r < puzzle.getHeight(); r++) {
      for (int c = 0; c < puzzle.getWidth(); c++) {
        lights[r][c] = 0;
      }
    }
    observers = new ArrayList<>();
  }

  @Override
  public void addLamp(int r, int c) {
    if (r >= puzzle.getHeight() || c >= puzzle.getWidth() || c < 0 || r < 0)
      throw new IndexOutOfBoundsException();
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) throw new IllegalArgumentException();

    if (lights[r][c] == 0) {
      lights[r][c] = 1; // add lamp if one doesn't already exist to the active puzzle in that cell
      notifyObservers();
    }
  }

  @Override
  public void removeLamp(int r, int c) {

    if (r >= puzzle.getHeight() || c >= puzzle.getWidth() || c < 0 || r < 0)
      throw new IndexOutOfBoundsException();
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) throw new IllegalArgumentException();

    if (lights[r][c]
        == 1) { // remove lamp if one already exists from the active puzzle in that cell
      lights[r][c] = 0;
      notifyObservers();
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r >= puzzle.getHeight() || c >= puzzle.getWidth() || c < 0 || r < 0)
      throw new IndexOutOfBoundsException();
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) throw new IllegalArgumentException();

    for (int i = c; i < puzzle.getWidth(); i++) {
      if (puzzle.getCellType(r, i) == CellType.CLUE || puzzle.getCellType(r, i) == CellType.WALL)
        break; // if blocked

      if (lights[r][i] == 1) return true;
    }
    for (int i = c; i >= 0; i--) {
      if (puzzle.getCellType(r, i) == CellType.CLUE || puzzle.getCellType(r, i) == CellType.WALL)
        break; // if blocked

      if (lights[r][i] == 1) return true;
    }

    for (int i = r; i < puzzle.getHeight(); i++) {
      if (puzzle.getCellType(i, c) == CellType.CLUE || puzzle.getCellType(i, c) == CellType.WALL)
        break;

      if (lights[i][c] == 1) return true;
    }

    for (int i = r; i >= 0; i--) {
      if (puzzle.getCellType(i, c) == CellType.CLUE || puzzle.getCellType(i, c) == CellType.WALL)
        break;
      if (lights[i][c] == 1) return true;
    }

    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r >= puzzle.getHeight() || c >= puzzle.getWidth() || c < 0 || r < 0)
      throw new IndexOutOfBoundsException();
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) throw new IllegalArgumentException();
    if (lights[r][c] == 1) return true;
    return false;
  }

  @Override
  public boolean isLampIllegal(int r, int c) { // ip
    if (this.lights[r][c] != 1) // not a lamp
    throw new IllegalArgumentException();
    if (r >= puzzle.getHeight() || c >= puzzle.getWidth() || c < 0 || r < 0)
      throw new IndexOutOfBoundsException();
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) throw new IllegalArgumentException();
    if (c > 0) {
      for (int i = c - 1; i >= 0; i--) {
        if (puzzle.getCellType(r, i) == CellType.CLUE || puzzle.getCellType(r, i) == CellType.WALL)
          break; // blocked
        if (lights[r][i] == 1) return true;
      }
    }
    if (c < puzzle.getWidth() - 1) {
      for (int i = c + 1; i < puzzle.getWidth(); i++) {
        if (puzzle.getCellType(r, i) == CellType.CLUE || puzzle.getCellType(r, i) == CellType.WALL)
          break; // blocked
        if (lights[r][i] == 1) return true;
      }
    }
    if (r > 0) {
      for (int i = r - 1; i >= 0; i--) {
        if (puzzle.getCellType(i, c) == CellType.CLUE || puzzle.getCellType(i, c) == CellType.WALL)
          break; // blocked
        if (lights[i][c] == 1) return true;
      }
    }
    if (r < puzzle.getHeight() - 1) {
      for (int i = r + 1; i < puzzle.getHeight(); i++) {
        if (puzzle.getCellType(i, c) == CellType.CLUE || puzzle.getCellType(i, c) == CellType.WALL)
          break; // blocked
        if (lights[i][c] == 1) return true;
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return puzzle;
  }

  @Override
  public int getActivePuzzleIndex() {
    return activePuzzle;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= getPuzzleLibrarySize()) throw new IndexOutOfBoundsException();
    this.activePuzzle = index;
    this.puzzle = library.getPuzzle(activePuzzle);
    this.lights = new int[puzzle.getHeight()][puzzle.getWidth()];
    // sets up puzzle
    for (int r = 0; r < puzzle.getHeight(); r++) {
      for (int c = 0; c < puzzle.getWidth(); c++) {
        lights[r][c] = 0;
      }
    }
    notifyObservers();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    for (int r = 0; r < puzzle.getHeight(); r++) {
      for (int c = 0; c < puzzle.getWidth(); c++) {
        lights[r][c] = 0;
      }
    }
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    for (int r = 0; r < puzzle.getHeight(); r++) {
      for (int c = 0; c < puzzle.getWidth(); c++) {
        if (puzzle.getCellType(r, c) == CellType.CORRIDOR) {
          if (!isLit(r, c)) return false;
          if (lights[r][c] == 1 && isLampIllegal(r, c)) return false;
        }
        if (puzzle.getCellType(r, c) == CellType.CLUE && !isClueSatisfied(r, c)) return false;
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r >= puzzle.getHeight() || c >= puzzle.getWidth() || c < 0 || r < 0)
      throw new IndexOutOfBoundsException();
    if (puzzle.getCellType(r, c) != CellType.CLUE) throw new IllegalArgumentException();
    int numLights = 0;
    if (r > 0 && lights[r - 1][c] == 1) numLights++;
    if (r < puzzle.getHeight() - 1 && lights[r + 1][c] == 1) numLights++;
    if (c > 0 && lights[r][c - 1] == 1) numLights++;
    if (c < puzzle.getWidth() - 1 && lights[r][c + 1] == 1) numLights++;
    return numLights == puzzle.getClue(r, c);
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private void notifyObservers() {
    for (ModelObserver mo : observers) mo.update(this);
  }
}
