package com.comp301.a09akari.view;

import javafx.application.Application;
import javafx.stage.Stage;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    // populating puzzle library with all puzzles from sample puzzles
    PuzzleLibrary library = new PuzzleLibraryImpl();
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));

    // new model!
    Model model = new ModelImpl(library);
    // new controller
    ClassicMvcController controller = new ControllerImpl(model);
    // new puzzle view
    PuzzleView puzzle = new PuzzleView(controller);

    stage.setScene(puzzle.getScene());

    // registering an active ModelObserver w/ lambda expression
    // model.addObserver(
    //      (Model m) -> {
    //      scene.setRoot(puzzle.render());
    //   stage.sizeToScene();
    // });

    stage.setTitle("Welcome to Akari");
    stage.show();
  }
}
