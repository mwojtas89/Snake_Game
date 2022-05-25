package snake.logic;

import java.util.Objects;

public class GameElements {
    private int col;
    private int row;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameElements that = (GameElements) o;
        return col == that.col && row == that.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public GameElements(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
