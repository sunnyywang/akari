package com.comp301.a09akari.controller;

import java.util.Random;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;


public class ControllerImpl implements ClassicMvcController {
  private Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    //if not at the end
    if (model.getActivePuzzleIndex() != model.getPuzzleLibrarySize() - 1)
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
    else model.setActivePuzzleIndex(0);
  }

  @Override
  public void clickPrevPuzzle() {
    //if at first puzzle
    if (model.getActivePuzzleIndex() == 0)
      model.setActivePuzzleIndex(model.getPuzzleLibrarySize() - 1); //set to last puzzle
    else model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
  }

  @Override
  public void clickRandPuzzle() {
    int nextPuzzle = model.getActivePuzzleIndex(); //keeps track of which puzzle we are at
    Random random = new Random(); //initialize random
    while (model.getActivePuzzleIndex() == nextPuzzle)
      nextPuzzle = random.nextInt(model.getPuzzleLibrarySize());
    //set to diff puzzle
    model.setActivePuzzleIndex(nextPuzzle);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.isLamp(r, c))
      model.removeLamp(r, c); //remove lamp if we click on a lamp
    else
      model.addLamp(r, c);
  }

}
