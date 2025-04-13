package komponentowe;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GameOfLifeLine implements Serializable, Cloneable {
    protected List<GameOfLifeCell> array;

    public GameOfLifeLine(GameOfLifeCell[] line) {
        this.array = Arrays.asList(line);
    }

    public int countAliveCells() {
        int count = 0;
        for (GameOfLifeCell cell : array) {
            if (cell.getCellValue()) {
                count++;
            }
        }
        return count;
    }

    public int countDeadCells() {
        return array.size() - countAliveCells();
    }

    public boolean getCell(int position) {
        return array.get(position).getCellValue();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("array", array)
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
        GameOfLifeLine pom = (GameOfLifeLine) obj;
        return new EqualsBuilder()
                .append(this.array, pom.array)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.array)
                .toHashCode();
    }

    @Override
    public GameOfLifeLine clone() throws CloneNotSupportedException {
        GameOfLifeLine copy = (GameOfLifeLine) super.clone();

        copy.array = new ArrayList<>();

        for (GameOfLifeCell cell : array) {
            copy.array.add(cell.clone());
        }

        return copy;
    }
}

