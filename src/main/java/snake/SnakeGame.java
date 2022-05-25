package snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import snake.logic.Board;
import snake.logic.Direction;
import snake.logic.GameElements;


public class SnakeGame extends Application {

    private final Image backGround = new Image("file:/Users/humptydumpty/IdeaProjects/kodilla-snake/src/main/resources/7e2d7bea4ac21388c4a96e1371f375c4ce00094b.jpeg");
    private final Image apple = new Image("file:/Users/humptydumpty/IdeaProjects/kodilla-snake/src/main/resources/258560.png");
    private final Image snakeElement = new Image("file:/Users/humptydumpty/IdeaProjects/kodilla-snake/src/main/resources/square (1).png");
    private final Image snakeHead = new Image("file:/Users/humptydumpty/IdeaProjects/kodilla-snake/src/main/resources/red-button-icons-png-230831.png");
    private Direction direction = Direction.RIGHT;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BackgroundSize backgroundSize = new BackgroundSize(600, 400, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(backGround, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background background = new Background(backgroundImage);
        menu(primaryStage, background);

    }

    private void menu(Stage primaryStage, Background background) {
        primaryStage.setTitle("Snake Menu");
        Button b = new Button ();
        b.setFont(Font.font("verdana"));
        b.setText("Play");
        StackPane r = new StackPane();
        EventHandler<ActionEvent> event = e -> snakeRun(primaryStage, background);
        b.setOnAction(event);
        r.getChildren().add(b);
        Scene sc = new Scene(r, 200, 200);
        primaryStage.setScene(sc);
        primaryStage.show();
    }

    private void snakeRun(Stage primaryStage, Background background) {
        GridPane grid = new GridPane();
        grid.setBackground(background);

        for (int n = 0; n < 60; n++) {
            grid.getColumnConstraints().add(new ColumnConstraints(10));
        }
        for (int n = 0; n < 43; n++) {
            grid.getRowConstraints().add(new RowConstraints(10));
        }

        Scene scene = new Scene(grid, 600, 430, Color.BLACK);
        Board board = new Board();
        userDialogs(scene);

        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        timmerRun(grid, board, primaryStage);
        scene.getRoot().requestFocus();
    }

    private void timmerRun(GridPane grid, Board board, Stage primaryStage) {
        AnimationTimer at = new AnimationTimer() {
            private long count = 0;

            @Override
            public void handle(long now) {
                if (count >= 8) {
                    count = 0;
                    if (!board.makeMove(direction)) displayOnGrid(grid, board);
                    else {
                        Label label = new Label("Result: " + board.getSCORE_COUNT());
                        Popup popup = new Popup();
                        label.setStyle(" -fx-background-color: white;");
                        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
                        popup.getContent().add(label);
                        popup.show(primaryStage);
                        stop();
                    }
                } else count++;
            }
        };
        at.start();

    }

    private void userDialogs(Scene scene) {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case RIGHT:
                    direction = (direction != Direction.LEFT) ? Direction.RIGHT : direction;
                    break;
                case DOWN:
                    direction = (direction != Direction.UP) ? Direction.DOWN : direction;
                    break;
                case LEFT:
                    direction = (direction != Direction.RIGHT) ? Direction.LEFT : direction;
                    break;
                case UP:
                    direction = (direction != Direction.DOWN) ? Direction.UP : direction;
            }
        });
    }

    private void displayOnGrid(GridPane grid, Board board) {
        grid.getChildren().clear();
        for(int n=0;n<board.getSnake().size();n++){
            GameElements snake = board.getSnake().get(n);
            ImageView imageView;
            if(n==board.getSnake().size()-1) {
                imageView = new ImageView(snakeHead);
            } else {
                imageView = new ImageView(snakeElement);
            }
            grid.add(imageView, snake.getCol(), snake.getRow());
        }

        for (GameElements ap : board.getApples()) {
            ImageView imageView = new ImageView(apple);
            grid.add(imageView, ap.getCol(), ap.getRow());
        }
        Text text = new Text();
        text.setText("Score: " + board.getSCORE_COUNT());
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        text.setFill(Color.WHITE);
        grid.add(text,27,41);
    }
}

