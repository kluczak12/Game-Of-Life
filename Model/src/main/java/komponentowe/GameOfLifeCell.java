package komponentowe;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.*;

public class GameOfLifeCell implements Serializable, Cloneable, Comparable<GameOfLifeCell> {
    private boolean value;
    private List<GameOfLifeCell> neighbours;

    public GameOfLifeCell(boolean value) {
        this.value = value;
        this.neighbours = Arrays.asList(new GameOfLifeCell[8]);
    }

    public boolean getCellValue() {
        return value;
    }

    public void setNeighbours(GameOfLifeBoard board, int rowCell, int colCell) {
        int index = 0;
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (k == 0 && l == 0) {
                    continue;
                }
                neighbours.set(index, board.getCell((rowCell + k + board.getSizeRows()) % board.getSizeRows(),
                        (colCell + l + board.getSizeColumns()) % board.getSizeColumns()));
                index++;
            }
        }
    }

    public List<GameOfLifeCell> getNeighbours() {
        return neighbours;
    }

    public boolean nextState() {
        boolean nextValue;
        int alive = 0;
        for (GameOfLifeCell neighbour : neighbours) {
            if (neighbour.getCellValue()) {
                alive++;
            }
        }

        if (alive < 2 || alive > 3) {
            nextValue = false;
        } else if (alive == 3) {
            nextValue = true;
        } else {
            nextValue = value;
        }

        return nextValue;
    }

    public void updateState(boolean newState) {
        value = newState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("neighbours", neighbours)
                .toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        GameOfLifeCell pom = (GameOfLifeCell) obj;
        return new EqualsBuilder()
                .append(this.getCellValue(), pom.getCellValue())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }

    @Override
    public int compareTo(GameOfLifeCell o) {
        if (o == null) {
            throw new NullPointerException();
        }
        return Boolean.compare(this.value, o.value);
    }

    @Override
    public GameOfLifeCell clone() throws CloneNotSupportedException {
        GameOfLifeCell cloned = (GameOfLifeCell) super.clone();
        return cloned;
    }
}