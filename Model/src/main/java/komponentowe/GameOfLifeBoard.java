package komponentowe;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.*;

public class GameOfLifeBoard implements Serializable, Cloneable {
    private GameOfLifeCell[][] board;
    private GameOfLifeSimulator simulator;


    public GameOfLifeBoard(int sizeRows, int sizeColumns) {
        this.board = new GameOfLifeCell[sizeRows][sizeColumns];
        this.simulator = new PlainGameOfLifeSimulation();
        List<Boolean> losowanie = new ArrayList<>();
        losowanie.add(Boolean.TRUE);
        losowanie.add(Boolean.FALSE);
        Collections.shuffle(losowanie);
        for (int i = 0; i < sizeRows; i++) {
            for (int j = 0; j < sizeColumns; j++) {
                Collections.shuffle(losowanie);
                board[i][j] = new GameOfLifeCell(losowanie.getFirst());
                //komórka ma 50% szans do bycia żywą na starcie
            }
        }
        for (int i = 0; i < getSizeRows(); i++) {
            for (int j = 0; j < getSizeColumns(); j++) {
                board[i][j].setNeighbours(this, i, j);
            }
        }
    }

    public int getSizeRows() {
        return this.board.length;
    }

    public int getSizeColumns() {
        return this.board[0].length;
    }

    //Array.copyOf tworzy kopię pojedynczej tablicy czyli rzędu dwuwymiarowej
    public GameOfLifeCell[][] getBoard() {
        GameOfLifeCell[][] boardPom = new GameOfLifeCell[getSizeRows()][getSizeColumns()];
        for (int i = 0; i < board.length; i++) {
            boardPom[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return boardPom;
    }

    public void setBoard(int x, int y, boolean value) throws IndexOutOfBoundsException {
        board[x][y].updateState(value);
    }

    public GameOfLifeCell getCell(int x, int y) {
        return board[x][y];
    }

    public void setCell(int x, int y, boolean value) throws IndexOutOfBoundsException {
        board[x][y].updateState(value);
    }

    public void doSimulationStep() {
        simulator.doStep(this);
    }

    public GameOfLifeRow getRow(int y) {
        return new GameOfLifeRow(board[y]);
    }

    public GameOfLifeColumn getColumn(int x) {
        GameOfLifeCell[] columns = new GameOfLifeCell[getSizeColumns()];
        for (int i = 0; i < getSizeColumns(); i++) {
            columns[i] = board[i][x];
        }
        return new GameOfLifeColumn(columns);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("board", getBoard())
                .append("simulator", simulator)
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
        GameOfLifeBoard pom = (GameOfLifeBoard) obj;
        return new EqualsBuilder()
                .append(this.board, pom.board)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 23)
                .append(Arrays.deepHashCode(this.board))
                .toHashCode();
    }

    @Override
    public GameOfLifeBoard clone() throws CloneNotSupportedException {
        GameOfLifeBoard clone = (GameOfLifeBoard) super.clone();
        clone.board = new GameOfLifeCell[getSizeRows()][getSizeColumns()];
        for (int i = 0; i < getSizeRows(); i++) {
            for (int j = 0; j < getSizeColumns(); j++) {
                clone.board[i][j] = board[i][j].clone();
            }
        }
        for (int i = 0; i < getSizeRows(); i++) {
            for (int j = 0; j < getSizeColumns(); j++) {
                clone.board[i][j].setNeighbours(clone, i, j);
            }
        }
        clone.simulator = this.simulator;

        return clone;
    }
}