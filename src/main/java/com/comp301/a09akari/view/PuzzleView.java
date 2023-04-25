package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;

public class PuzzleView implements FXComponent {
  private final ClassicMvcController controller;
  private final Scene scene;

  public PuzzleView(ClassicMvcController controller) {
    this.controller = controller;
    this.scene = new Scene(render(), 500, 500);
  }

  @Override
  public Parent render() {
    BorderPane layout = new BorderPane();

    MessageView message = new MessageView(controller);
    layout.setTop(message.render());

    ControlView controller = new ControlView(this.controller);
    layout.setBottom(controller.render());

    GridPanel grid = new GridPanel(this.controller);
    layout.setCenter(grid.render());

    return layout;
  }

  public Scene getScene() {
    return scene;
  }
}
