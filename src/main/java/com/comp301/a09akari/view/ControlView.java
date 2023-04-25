package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class ControlView implements FXComponent, ModelObserver {
  private final ClassicMvcController controller;
  private final Model model;

  public ControlView(Model model, ClassicMvcController controller) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    HBox pane = new HBox();
    pane.getChildren().clear();
    pane.setSpacing(5);

    // start over button
    Pane startOver = new HBox();
    Button reset = new Button("Begin Again");
    reset.setOnAction(
        (ActionEvent event) -> {
          controller.clickResetPuzzle();
        });
    startOver.getChildren().add(reset);
    HBox.setHgrow(startOver, Priority.ALWAYS);
    pane.getChildren().add(startOver);

    // random puzzle button
    Button random = new Button("Random Puzzle");
    random.setOnAction(
        (ActionEvent event) -> {
          controller.clickRandPuzzle();
        });
    pane.getChildren().add(random);

    // back button
    Button back = new Button("Go Back");
    back.setOnAction(
        (ActionEvent event) -> {
          controller.clickPrevPuzzle();
        });
    pane.getChildren().add(back);

    // next button
    Button next = new Button("Next Puzzle");
    next.setOnAction(
        (ActionEvent event) -> {
          controller.clickNextPuzzle();
        });
    pane.getChildren().add(next);

    return pane;
  }

  @Override
  public void update(Model model) {}
}
