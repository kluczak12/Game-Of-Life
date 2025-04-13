package komponentowe;

import komponentowe.modelexceptions.ClonesException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeCellTest {

    //metoda pomocnicza w celu ustawienia stanu początkowego planszy przed testami metod doStep, countAliveNeighbours
    void fillWithFalse(GameOfLifeBoard testBoardFullFalse) {
        for (int i = 0; i < testBoardFullFalse.getSizeRows(); i++) {
            for (int j = 0; j < testBoardFullFalse.getSizeColumns(); j++) {
                testBoardFullFalse.setBoard(i, j, false);
            }
        }
    }

    @Test
    void getCellValue() {
        GameOfLifeBoard testBoardGC = new GameOfLifeBoard(1, 1);
        testBoardGC.setBoard(0, 0, true);
        assertTrue(testBoardGC.getBoard()[0][0].getCellValue());
        testBoardGC.setBoard(0, 0, false);
        assertFalse(testBoardGC.getBoard()[0][0].getCellValue());
    }

    @Test
    void nextState() {
        GameOfLifeBoard testBoardNS = new GameOfLifeBoard(3, 3);
        fillWithFalse(testBoardNS);
        testBoardNS.setBoard(0, 0, true);
        testBoardNS.setBoard(0, 1, true);
        testBoardNS.setBoard(1, 0, true);
        assertTrue(testBoardNS.getCell(1, 1).nextState());
    }

    @Test
    void updateState() {
        GameOfLifeBoard testBoardUS = new GameOfLifeBoard(1, 1);
        testBoardUS.setBoard(0, 0, true);
        assertTrue(testBoardUS.getBoard()[0][0].getCellValue());
        testBoardUS.getCell(0, 0).updateState(false);
        assertFalse(testBoardUS.getBoard()[0][0].getCellValue());
    }

    @Test
    void setCell() {
        GameOfLifeBoard testBoardC = new GameOfLifeBoard(8, 8);
        testBoardC.setBoard(1, 4, true);
        assertTrue(testBoardC.getBoard()[1][4].getCellValue());
    }

    @Test
    void setNeighbor() {
        GameOfLifeBoard testBoardSN = new GameOfLifeBoard(3, 3);
        fillWithFalse(testBoardSN);
        testBoardSN.setBoard(0, 0, true);
        testBoardSN.setBoard(0, 1, true);
        testBoardSN.setBoard(0, 2, true);
        testBoardSN.setBoard(1, 0, true);
        for (int i = 0; i < 4; i++) {
            assertTrue(testBoardSN.getCell(1, 1).getNeighbours().get(i).getCellValue());
        }

        for (int i = 4; i < 8; i++) {
            assertFalse(testBoardSN.getCell(1, 1).getNeighbours().get(i).getCellValue());
        }
    }

//    @Test
//    void testToString() {
//        GameOfLifeBoard pomString = new GameOfLifeBoard(3, 3);
//        fillWithFalse(pomString);
//        pomString.setBoard(0, 0, true);
//        String naszString = pomString.getCell(0,0).toString();
//        assertNotNull(naszString);
//    }

    @Test
    void equals() {
        GameOfLifeBoard testBoardNS = new GameOfLifeBoard(4, 4);
        fillWithFalse(testBoardNS);
        testBoardNS.setBoard(0, 0, true);
        testBoardNS.setBoard(0, 1, true);

        assertTrue(testBoardNS.getBoard()[0][0].equals(testBoardNS.getBoard()[0][0]));
        assertTrue(testBoardNS.getBoard()[0][0].equals(testBoardNS.getBoard()[0][1]));
        assertFalse(testBoardNS.getBoard()[0][0].equals(testBoardNS.getBoard()[0][2]));

        assertFalse(testBoardNS.getBoard()[0][0].equals(null));

        assertFalse(testBoardNS.getBoard()[0][0].equals(testBoardNS));

        assertEquals(testBoardNS.getBoard()[0][0].hashCode(), testBoardNS.getBoard()[0][1].hashCode());
    }

    @Test
    void hash() {
        GameOfLifeBoard testBoardNS = new GameOfLifeBoard(4, 4);
        fillWithFalse(testBoardNS);
        testBoardNS.setBoard(0, 0, true);
        testBoardNS.setBoard(0, 1, true);
        int x = testBoardNS.getBoard()[0][0].hashCode();
        int y = testBoardNS.getBoard()[0][0].hashCode();
        assertEquals(x, y);

        assertNotEquals(testBoardNS.getBoard()[0][1].hashCode(), testBoardNS.getBoard()[0][2].hashCode());
    }


    @Test
    void testClone() throws ClonesException {
        GameOfLifeCell originalCell = new GameOfLifeCell(true);
        GameOfLifeCell clonedCell = null;
        try {
            clonedCell = originalCell.clone();
        } catch (CloneNotSupportedException e) {
            throw new ClonesException();
        }

        assertNotSame(originalCell, clonedCell);
        assertEquals(originalCell.getCellValue(), clonedCell.getCellValue());

        clonedCell.updateState(!originalCell.getCellValue());
        assertNotEquals(originalCell.getCellValue(), clonedCell.getCellValue());
    }

    @Test
    void testCompareTo() {
        GameOfLifeCell cell1 = new GameOfLifeCell(true);
        GameOfLifeCell cell2 = new GameOfLifeCell(false);
        GameOfLifeCell cell3 = new GameOfLifeCell(true);

        assertTrue(cell1.compareTo(cell2) > 0);
        assertTrue(cell2.compareTo(cell1) < 0);
        assertTrue(cell1.compareTo(cell3) == 0);
        assertTrue(cell2.compareTo(cell2) == 0);
    }

    @Test
    void testCompareToNull() {
        GameOfLifeCell cell = new GameOfLifeCell(true);
        assertThrows(NullPointerException.class, () -> {
            cell.compareTo(null);
        });
    }

}
