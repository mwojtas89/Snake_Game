package snake.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final static int MAX_ROW = 40;
    private final static int MAX_COL = 60;

    public List<GameElements> getSnake() {
        return snake;
    }

    public List<GameElements> getApples() {
        return apples;
    }

    public Integer getSCORE_COUNT() {
        return SCORE_COUNT;
    }

    private final static int NEW_APPLE_COUNT = 15;
    private int SCORE_COUNT = 0;

    private final List<GameElements> snake = new ArrayList<>();
    private final List<GameElements> apples = new ArrayList<>();
    private Integer moveCounter = 0;


    public Board() {
        snake.add(new GameElements(30,20));
        snake.add(new GameElements(31,20));
        snake.add(new GameElements(32,20));
    }

    public boolean makeMove(Direction direction) {

        moveCounter++;
        if (moveCounter >= NEW_APPLE_COUNT) {
            moveCounter = 0;
            generateNewApple();
        }
        int col = snake.get(snake.size() - 1).getCol();
        int row = snake.get(snake.size() - 1).getRow();
        if (direction == Direction.UP)
            row--;
        else if (direction == Direction.DOWN)
            row++;
        else if (direction == Direction.LEFT)
            col--;
        else
            col++;

        GameElements newElement = new GameElements(col, row);
        if(snake.stream().filter(el->el.equals(newElement)).count() !=0) {
            return true;
        } else {
            if(newElement.getCol() >= MAX_COL || newElement.getRow()>=MAX_ROW || newElement.getRow()<0 || newElement.getCol()<0){
                return true;
            } else {
                snake.add(newElement);
            }
        }
        if (apples.stream().filter(ge -> ge.equals(newElement)).count() == 0)
            snake.remove(0);
        else {
            apples.remove(newElement);
            SCORE_COUNT = SCORE_COUNT +10;
        }
        return false;
    }



    private void generateNewApple() {
        Random apple = new Random();
        GameElements ap = new GameElements(apple.nextInt(59),apple.nextInt(39));
        apples.add(ap);
        if(snake.stream().filter(a->a.equals(ap)).count()!=0){
            apples.remove(ap);
        }
    }
}
