package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;

public class PuzzleView implements FXComponent, ModelObserver {
  private final ClassicMvcController controller;
  private final Scene scene;
  private final Model model;

  public PuzzleView(Model model, ClassicMvcController controller) {
    this.controller = controller;
    this.model = model;
    this.scene = new Scene(render(), 600  , 600);
    // registering an observer
    model.addObserver(this);
  }

  @Override
  public Parent render() {
    BorderPane layout = new BorderPane();

    MessageView message = new MessageView(this.model, this.controller);
    layout.setTop(message.render());

    ControlView controller = new ControlView(this.model, this.controller);
    layout.setBottom(controller.render());

    GridPanel grid = new GridPanel(this.model, this.controller);
    layout.setCenter(grid.render());

    return layout;
  }

  public Scene getScene() {
    return scene;
  }

  @Override
  public void update(Model model) {
    scene.setRoot(render());
  }
}
