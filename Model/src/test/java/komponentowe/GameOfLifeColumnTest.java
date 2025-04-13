package komponentowe;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeColumnTest {
    private GameOfLifeBoard testBoardFullFalse;
    //metoda pomocnicza w celu ustawienia stanu początkowego planszy przed testami
    @BeforeEach
    void fillWithFalse() {
        testBoardFullFalse = new GameOfLifeBoard(8, 8);
        for (int i = 0; i < testBoardFullFalse.getSizeRows(); i++) {
            for (int j = 0; j < testBoardFullFalse.getSizeColumns(); j++) {
                testBoardFullFalse.setBoard(i, j, false);
            }
        }
    }

    @Test
    void constructorColumn() {
        GameOfLifeColumn testColumn = testBoardFullFalse.getColumn(5);
        assertEquals(8, testColumn.array.size());
        for (int i = 0; i < testBoardFullFalse.getSizeColumns(); i++) {
            assertFalse(testColumn.getCell(i));
        }
    }

    @Test
    void countAliveCellsColumn() {
        testBoardFullFalse.setCell(3, 1, true);
        testBoardFullFalse.setCell(2, 2, true);
        testBoardFullFalse.setCell(3, 2, true);

        GameOfLifeColumn testColumnAC = testBoardFullFalse.getColumn(1);
        GameOfLifeColumn testColumnAC2 = testBoardFullFalse.getColumn(2);
        GameOfLifeColumn testColumnAC3 = testBoardFullFalse.getColumn(3);

        assertEquals(1, testColumnAC.countAliveCells());
        assertEquals(2, testColumnAC2.countAliveCells());
        assertEquals(0, testColumnAC3.countAliveCells());
    }

    @Test
    void countDeadCellsColumn() {
        testBoardFullFalse.setCell(3, 1, true);
        testBoardFullFalse.setCell(2, 2, true);
        testBoardFullFalse.setCell(3, 2, true);

        GameOfLifeColumn testColumnAC = testBoardFullFalse.getColumn(1);
        GameOfLifeColumn testColumnAC2 = testBoardFullFalse.getColumn(2);
        GameOfLifeColumn testColumnAC3 = testBoardFullFalse.getColumn(3);

        assertEquals(7, testColumnAC.countDeadCells());
        assertEquals(6, testColumnAC2.countDeadCells());
        assertEquals(8, testColumnAC3.countDeadCells());
    }

//    @Test
//    void testToString() {
//        GameOfLifeColumn testColumnString = testBoardFullFalse.getColumn(0);
//
//        String naszString = testColumnString.toString();
//
//        assertNotNull(naszString);
//    }

    @Test
    void equals() {
        testBoardFullFalse.setBoard(0, 0, true);
        testBoardFullFalse.setBoard(0, 1, true);

        GameOfLifeColumn testColumnEquals = testBoardFullFalse.getColumn(0);
        GameOfLifeColumn testColumnEquals1 = testBoardFullFalse.getColumn(1);
        GameOfLifeColumn testColumnEquals2 = testBoardFullFalse.getColumn(2);

        assertTrue(testColumnEquals.equals(testColumnEquals));
        assertTrue(testColumnEquals.equals(testColumnEquals1));
        assertEquals(testColumnEquals.hashCode(), testColumnEquals1.hashCode());

        assertFalse(testColumnEquals.equals(testColumnEquals2));
        assertFalse(testColumnEquals.equals(null));
        assertFalse(testColumnEquals.equals(testBoardFullFalse));
    }

    @Test
    void hash() {
        testBoardFullFalse.setBoard(0, 0, true);
        GameOfLifeColumn testColumnHash = testBoardFullFalse.getColumn(0);
        GameOfLifeColumn testColumnHash2 = testBoardFullFalse.getColumn(2);

        int x = testColumnHash.hashCode();
        int y = testColumnHash.hashCode();
        assertEquals(x, y);

        assertNotEquals(testColumnHash.hashCode(), testColumnHash2.hashCode());
    }

    @Test
    void testCloneColumn() throws CloneNotSupportedException {
        GameOfLifeCell[] cells = {
                new GameOfLifeCell(false),
                new GameOfLifeCell(true),
                new GameOfLifeCell(false)
        };
        GameOfLifeColumn originalColumn = new GameOfLifeColumn(cells);
        GameOfLifeColumn clonedColumn = (GameOfLifeColumn) originalColumn.clone();

        assertNotSame(originalColumn, clonedColumn);
        assertNotSame(originalColumn.array, clonedColumn.array);

        for (int i = 0; i < originalColumn.array.size(); i++) {
            assertEquals(originalColumn.array.get(i).getCellValue(), clonedColumn.array.get(i).getCellValue());
        }

        clonedColumn.array.get(1).updateState(!originalColumn.array.get(1).getCellValue());
        assertNotEquals(originalColumn.array.get(1).getCellValue(), clonedColumn.array.get(1).getCellValue());
    }

}