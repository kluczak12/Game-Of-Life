package komponentowe;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameOfLifeBoardTest {
    @Test
    void GameOfLifeBoard() {
        GameOfLifeBoard testBoard = new GameOfLifeBoard(5, 7);
        assertNotNull(testBoard);
        assertEquals(5, testBoard.getSizeRows());
        assertEquals(7, testBoard.getSizeColumns());
    }

    @Test
    void GameOfLifeBoardBegin() {
        GameOfLifeBoard testBoardRB1 = new GameOfLifeBoard(7, 8);
        GameOfLifeBoard testBoardRB2 = new GameOfLifeBoard(7, 8);
        assertFalse(Arrays.deepEquals(testBoardRB1.getBoard(), testBoardRB2.getBoard()));
    }

    @Test
    void setBoard() {
        GameOfLifeBoard testCustomBoard = new GameOfLifeBoard(8, 8);
        testCustomBoard.setBoard(1, 4, true);
        assertTrue(testCustomBoard.getBoard()[1][4].getCellValue());
        testCustomBoard.setBoard(7, 7, false);
        assertFalse(testCustomBoard.getBoard()[7][7].getCellValue());
        try {
            testCustomBoard.setBoard(-7, -7, false);
            fail();
        } catch (IndexOutOfBoundsException blad) {
            // Test przechodzi, ponieważ wyjątek został rzucony
        }
    }

    @Test
    void setCell() {
        GameOfLifeBoard testCustomBoard = new GameOfLifeBoard(8, 8);
        testCustomBoard.setCell(1, 4, true);
        assertTrue(testCustomBoard.getBoard()[1][4].getCellValue());
        testCustomBoard.setCell(7, 7, false);
        assertFalse(testCustomBoard.getBoard()[7][7].getCellValue());
        try {
            testCustomBoard.setCell(-7, -7, false);
            fail();
        } catch (IndexOutOfBoundsException blad) {
            // Test przechodzi, ponieważ wyjątek został rzucony
        }
    }

    //metoda pomocnicza w celu ustawienia stanu początkowego planszy przed testami metod doStep, countAliveNeighbours
    void fillWithFalse(GameOfLifeBoard testBoardFullFalse) {
        for (int i = 0; i < testBoardFullFalse.getSizeRows(); i++) {
            for (int j = 0; j < testBoardFullFalse.getSizeColumns(); j++) {
                testBoardFullFalse.setBoard(i, j, false);
            }
        }
    }

    @Test
    void doStepFalseLess() {
        GameOfLifeBoard testBoardF = new GameOfLifeBoard(8, 8);
        fillWithFalse(testBoardF);
        testBoardF.setBoard(3, 1, true);
        testBoardF.setBoard(3, 2, true);
        testBoardF.doSimulationStep();
        assertFalse(testBoardF.getBoard()[3][2].getCellValue());       //śmierć przez samotność
    }

    @Test
    void doStepFalseMore() {
        GameOfLifeBoard testBoardFM = new GameOfLifeBoard(8, 8);
        fillWithFalse(testBoardFM);
        testBoardFM.setBoard(3, 1, true);
        testBoardFM.setBoard(3, 2, true);
        testBoardFM.setBoard(4, 2, true);
        testBoardFM.setBoard(2, 2, true);
        testBoardFM.setBoard(3, 3, true);
        testBoardFM.doSimulationStep();
        assertFalse(testBoardFM.getBoard()[3][2].getCellValue());      //śmierć przez zatłoczenie
    }

    @Test
    void doStepTrue() {
        GameOfLifeBoard testBoardF = new GameOfLifeBoard(8, 8);
        fillWithFalse(testBoardF);
        testBoardF.setBoard(3, 1, true);
        testBoardF.setBoard(3, 2, true);
        testBoardF.setBoard(3, 3, true);
        testBoardF.setBoard(2, 2, true);
        testBoardF.doSimulationStep();
        assertTrue(testBoardF.getBoard()[3][2].getCellValue());        //3 żywych sąsiadów
    }

    @Test
    void doStepTrueIfAlive() {
        GameOfLifeBoard testBoardFA = new GameOfLifeBoard(8, 8);
        fillWithFalse(testBoardFA);
        testBoardFA.setBoard(3, 1, true);
        testBoardFA.setBoard(2, 2, true);
        testBoardFA.setBoard(3, 2, true);
        testBoardFA.doSimulationStep();
        assertTrue(testBoardFA.getBoard()[3][2].getCellValue());        //2 żywych sąsiadów
    }

    @Test
    void getRow() {
        GameOfLifeBoard testBoardGR = new GameOfLifeBoard(3, 3);
        fillWithFalse(testBoardGR);
        testBoardGR.setBoard(2, 0, true);
        testBoardGR.setBoard(2, 1, true);
        testBoardGR.setBoard(2, 2, true);

        GameOfLifeRow testRowGR = testBoardGR.getRow(2);

        assertTrue(testRowGR.getCell(0));
        assertTrue(testRowGR.getCell(1));
        assertTrue(testRowGR.getCell(2));

        for (int i = 0; i < testBoardGR.getSizeColumns(); i++) {
            assertEquals(testRowGR.getCell(i), testBoardGR.getCell(2, i).getCellValue());
        }
    }

    @Test
    void getColumn() {
        GameOfLifeBoard testBoardGR = new GameOfLifeBoard(3, 3);
        fillWithFalse(testBoardGR);
        testBoardGR.setBoard(0, 1, true);
        testBoardGR.setBoard(1, 1, true);
        testBoardGR.setBoard(2, 1, true);

        GameOfLifeColumn testColumnAC = testBoardGR.getColumn(1);

        assertTrue(testColumnAC.getCell(0));
        assertTrue(testColumnAC.getCell(1));
        assertTrue(testColumnAC.getCell(2));

        for (int i = 0; i < testBoardGR.getSizeColumns(); i++) {
            assertEquals(testColumnAC.getCell(i), testBoardGR.getCell(i, 1).getCellValue());
        }
    }

//    @Test
//    void testToString() {
//        GameOfLifeBoard pomString = new GameOfLifeBoard(3, 3);
//
//        String naszString = pomString.toString();
//
//        assertNotNull(naszString);
//    }

    @Test
    void testEquals() {
        GameOfLifeBoard testBoardNS = new GameOfLifeBoard(4, 4);
        fillWithFalse(testBoardNS);
        testBoardNS.setBoard(0, 0, true);
        testBoardNS.setBoard(0, 1, true);

        GameOfLifeBoard testBoardNS2 = new GameOfLifeBoard(4, 4);
        fillWithFalse(testBoardNS2);
        testBoardNS2.setBoard(0, 0, true);
        testBoardNS2.setBoard(0, 1, true);

        assertEquals(testBoardNS.hashCode(), testBoardNS.hashCode());
        assertEquals(testBoardNS.hashCode(), testBoardNS2.hashCode());

        assertFalse(testBoardNS.equals(null));

        assertEquals(testBoardNS, testBoardNS);
        assertEquals(testBoardNS, testBoardNS2);

        testBoardNS2.setBoard(0, 2, true);
        assertNotEquals(testBoardNS, testBoardNS2);
        assertNotEquals(testBoardNS, testBoardNS2.getBoard()[0][1]);
        assertNotEquals(null, testBoardNS);
    }

    @Test
    void hash() {
        GameOfLifeBoard testBoardNS = new GameOfLifeBoard(4, 4);
        fillWithFalse(testBoardNS);
        testBoardNS.setBoard(0, 0, true);
        testBoardNS.setBoard(0, 1, true);
        int x = testBoardNS.hashCode();
        int y = testBoardNS.hashCode();
        assertEquals(x, y);

        GameOfLifeBoard testBoardNS2 = new GameOfLifeBoard(4, 4);
        fillWithFalse(testBoardNS2);
        assertNotEquals(testBoardNS.hashCode(), testBoardNS2.hashCode());
    }

    @Test
    void testClone() {
        GameOfLifeBoard originalBoard = new GameOfLifeBoard(10, 10);
        GameOfLifeBoard clonedBoard = null;
        try {
            clonedBoard = originalBoard.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        assertNotSame(originalBoard, clonedBoard);
        assertNotSame(originalBoard.getBoard(), clonedBoard.getBoard());

        for (int i = 0; i < originalBoard.getSizeRows(); i++) {
            for (int j = 0; j < originalBoard.getSizeColumns(); j++) {
                assertEquals(originalBoard.getCell(i, j), clonedBoard.getCell(i, j));
            }
        }

        assertEquals(originalBoard.getSizeRows(), clonedBoard.getSizeRows());
        assertEquals(originalBoard.getSizeColumns(), clonedBoard.getSizeColumns());

        assertNotSame(originalBoard, clonedBoard);
        assertNotSame(originalBoard.getBoard(), clonedBoard.getBoard());

        clonedBoard.setCell(1,1,!clonedBoard.getCell(1,1).getCellValue());
        assertNotEquals(originalBoard.getCell(1, 1), clonedBoard.getCell(1, 1));

        for (int i = 0; i < originalBoard.getSizeRows(); i++) {
            for (int j = 0; j < originalBoard.getSizeColumns(); j++) {
                assertEquals(originalBoard.getCell(i, j).getNeighbours(), clonedBoard.getCell(i, j).getNeighbours());
            }
        }
    }


}