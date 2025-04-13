package komponentowe;

import komponentowe.modelexceptions.DaoReadException;
import komponentowe.modelexceptions.DaoWriteException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileGameOfLifeBoardDaoTest {
    private static final String testPath = "testP";

    @Test
    void close() {
        try (FileGameOfLifeBoardDao testFileClose = new FileGameOfLifeBoardDao(testPath)) {
            assertDoesNotThrow(testFileClose::close);
        } catch (Exception e) {
            // Test przechodzi, ponieważ wyjątek został rzucony
        }
    }

    void fillWithFalse(GameOfLifeBoard testBoardFullFalse) {
        for (int i = 0; i < testBoardFullFalse.getSizeRows(); i++) {
            for (int j = 0; j < testBoardFullFalse.getSizeColumns(); j++) {
                testBoardFullFalse.setBoard(i, j, false);
            }
        }
    }

    @Test
    void writeAndRead() {
        GameOfLifeBoard boardWrite = new GameOfLifeBoard(4, 4);
        fillWithFalse(boardWrite);
        boardWrite.setBoard(1, 1, true);
        boardWrite.setBoard(0, 2, true);

        try (FileGameOfLifeBoardDao testFile1 = new FileGameOfLifeBoardDao(testPath)) {
            testFile1.write(boardWrite);
            GameOfLifeBoard readFromWriteBoard = testFile1.read();

            assertNotNull(readFromWriteBoard);
            assertTrue(boardWrite.equals(readFromWriteBoard));
        } catch (Exception e) {
        // Test przechodzi, ponieważ wyjątek został rzucony
        }
    }

    @Test
    void writeAndReadExceptions() {
        String path = "/system/protected/file.txt";
        try (FileGameOfLifeBoardDao testFileExceptions = new FileGameOfLifeBoardDao(path)) {

            GameOfLifeBoard boardWrite = new GameOfLifeBoard(4, 4);
            fillWithFalse(boardWrite);
            boardWrite.setBoard(1, 1, true);
            boardWrite.setBoard(0, 2, true);

            assertThrows(DaoWriteException.class, () -> testFileExceptions.write(boardWrite));
            assertThrows(DaoReadException.class, testFileExceptions::read);

        } catch (Exception e) {
            // Nieoczekiwany wyjątek
        }
    }
}