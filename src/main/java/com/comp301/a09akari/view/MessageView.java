package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MessageView implements FXComponent, ModelObserver {
  private final ClassicMvcController controller;
  private final Model model;

  public MessageView(Model model, ClassicMvcController controller) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    VBox pane = new VBox();
    pane.setSpacing(10);
    StackPane middle = new StackPane();
    // title
    Label title = new Label("Akari");
    title.setFont(new Font("Times New Roman", 30));
    title.setTextFill(Color.SLATEGRAY);
    middle.getChildren().add(title);
    pane.getChildren().add(middle);

    // tells you which puzzle you are on
    StackPane solveLine = new StackPane();
    Label solved =
        new Label(
            "Puzzle " + (model.getActivePuzzleIndex() + 1) + " of " + model.getPuzzleLibrarySize());
    solved.setFont(new Font("Times New Roman", 20));
    solved.setTextFill(Color.BLUE);
    solved.setAlignment(Pos.CENTER);

    if (model.isSolved()) {
      solved = new Label("Great job! You solved this puzzle!");
      solved.setFont(new Font("Times New Roman", 20));
      solved.setTextFill(Color.HOTPINK);
    }

    solveLine.getChildren().add(solved);
    pane.getChildren().add(solveLine);

    return pane;
  }

  @Override
  public void update(Model model) {}
}
