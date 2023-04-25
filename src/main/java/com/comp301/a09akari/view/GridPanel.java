package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GridPanel implements FXComponent, ModelObserver {
  private final ClassicMvcController controller;
  private final Model model;
  private final Paint pink = new Color(1, .9, .9, .9);
  private final Paint blue = new Color(.4, .7, .9, .7);

  public GridPanel(Model model, ClassicMvcController controller) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    GridPane board = new GridPane();
    board.setAlignment(Pos.CENTER);
    board.setGridLinesVisible(true);
    board.setPrefSize(130, 130);
    for (int i = 0; i < model.getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < model.getActivePuzzle().getWidth(); j++) {
        StackPane stack = new StackPane();
        Rectangle corridor = new Rectangle(40, 40);
        switch (model.getActivePuzzle().getCellType(i, j)) {
          case CLUE:
            Label clue = new Label(Integer.toString(model.getActivePuzzle().getClue(i, j)));
            if (model.isClueSatisfied(i, j)) {
              corridor.setFill(Color.LIGHTBLUE);
            } else {
              corridor.setFill(Color.LIGHTGREEN);
              clue.setTextFill(Color.WHITE);
            }
            stack.getChildren().add(corridor);
            stack.getChildren().add(clue);
            break;

          case WALL:
            corridor.setFill(Color.BLACK);
            stack.getChildren().add(corridor);
            break;

          case CORRIDOR:
            int row = i;
            int col = j;
            corridor.setOnMouseClicked(
                event -> {
                  controller.clickCell(row, col);
                });
            if (model.isLit(i, j)) {
              corridor.setFill(pink);
            } else {
              corridor.setFill(Color.TRANSPARENT);
            }
            if (model.isLamp(i, j)) {
              Image lamp = new Image("light-bulb.png", 14, 14, false, false);
              ImageView lampPic = new ImageView(lamp);
              lampPic.setOnMouseClicked(
                  event -> {
                    controller.clickCell(row, col);
                  });
              if (!model.isLampIllegal(i, j)) {
                corridor.setFill(Color.YELLOW);
                stack.getChildren().add(corridor);

              } else {
                corridor.setFill(blue);
                stack.getChildren().add(corridor);
              }
              stack.getChildren().add(lampPic);
            } else {
              stack.getChildren().add(corridor);
            }
            break;
        }
        board.add(stack, j, i);
      }
    }
    return board;
  }

  @Override
  public void update(Model model) {}
}
