package komponentowe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeRowTest {
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
    void constructorRow() {
        GameOfLifeRow testRow = testBoardFullFalse.getRow(5);
        assertEquals(8, testRow.array.size());
        for (int i = 0; i < testBoardFullFalse.getSizeRows(); i++) {
            assertFalse(testRow.getCell(i));
        }
    }

    @Test
    void countAliveCellsRow() {
        testBoardFullFalse.setBoard(1, 3, true);
        testBoardFullFalse.setBoard(2, 2, true);
        testBoardFullFalse.setBoard(2, 3, true);

        GameOfLifeRow testRowAC = testBoardFullFalse.getRow(1);
        GameOfLifeRow testRowAC2 = testBoardFullFalse.getRow(2);
        GameOfLifeRow testRowAC3 = testBoardFullFalse.getRow(3);

        assertEquals(1, testRowAC.countAliveCells());
        assertEquals(2, testRowAC2.countAliveCells());
        assertEquals(0, testRowAC3.countAliveCells());
    }

    @Test
    void countDeadCellsRow() {
        testBoardFullFalse.setBoard(1, 3, true);
        testBoardFullFalse.setBoard(2, 2, true);
        testBoardFullFalse.setBoard(2, 3, true);

        GameOfLifeRow testRowAC = testBoardFullFalse.getRow(1);
        GameOfLifeRow testRowAC2 = testBoardFullFalse.getRow(2);
        GameOfLifeRow testRowAC3 = testBoardFullFalse.getRow(3);

        assertEquals(7, testRowAC.countDeadCells());
        assertEquals(6, testRowAC2.countDeadCells());
        assertEquals(8, testRowAC3.countDeadCells());
    }

//    @Test
//    void testToString() {
//        GameOfLifeRow testRowString = testBoardFullFalse.getRow(0);
//
//        String naszString = testRowString.toString();
//
//        assertNotNull(naszString);
//    }

    @Test
    void equals() {
        testBoardFullFalse.setBoard(0, 0, true);
        testBoardFullFalse.setBoard(1, 0, true);

        GameOfLifeRow testRowEquals = testBoardFullFalse.getRow(0);
        GameOfLifeRow testRowEquals1 = testBoardFullFalse.getRow(1);
        GameOfLifeRow testRowEquals2 = testBoardFullFalse.getRow(2);


        assertTrue(testRowEquals.equals(testRowEquals));
        assertTrue(testRowEquals.equals(testRowEquals1));
        assertEquals(testRowEquals.hashCode(), testRowEquals1.hashCode());

        assertFalse(testRowEquals.equals(testRowEquals2));
        assertFalse(testRowEquals.equals(null));
        assertFalse(testRowEquals.equals(testBoardFullFalse));
    }

    @Test
    void hash() {
        testBoardFullFalse.setBoard(0, 0, true);
        GameOfLifeRow testRowHash = testBoardFullFalse.getRow(0);
        GameOfLifeRow testRowHash2 = testBoardFullFalse.getRow(2);

        int x = testRowHash.hashCode();
        int y = testRowHash.hashCode();
        assertEquals(x, y);

        assertNotEquals(testRowHash.hashCode(), testRowHash2.hashCode());
    }

    @Test
    void testCloneRow() throws CloneNotSupportedException {
        GameOfLifeCell[] cells = {
                new GameOfLifeCell(true),
                new GameOfLifeCell(false),
                new GameOfLifeCell(true)
        };
        GameOfLifeRow originalRow = new GameOfLifeRow(cells);
        GameOfLifeRow clonedRow = (GameOfLifeRow) originalRow.clone();

        assertNotSame(originalRow, clonedRow);
        assertNotSame(originalRow.array, clonedRow.array);

        for (int i = 0; i < originalRow.array.size(); i++) {
            assertEquals(originalRow.array.get(i).getCellValue(), clonedRow.array.get(i).getCellValue());
        }

        clonedRow.array.get(1).updateState(!originalRow.array.get(1).getCellValue());
        assertNotEquals(originalRow.array.get(1).getCellValue(), clonedRow.array.get(1).getCellValue());
    }
}