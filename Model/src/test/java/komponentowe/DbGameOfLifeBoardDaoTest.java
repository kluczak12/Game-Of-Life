package komponentowe;

import komponentowe.modelexceptions.DaoReadException;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;
class DbGameOfLifeBoardDaoTest {
    String dbUrl = "jdbc:postgresql://localhost:5432/BoardBase";
    final String user = "z6";
    final String password = "21";

    @Test
    public void testWriteAndReadBoard() throws Exception {
        try (Connection connection = DriverManager.getConnection(dbUrl, user, password);
             DbGameOfLifeBoardDao dao = new DbGameOfLifeBoardDao(connection)) {

            connection.setAutoCommit(false);

            GameOfLifeBoard originalBoard = new GameOfLifeBoard(5, 5);
            originalBoard.setBoard(0, 0, true);
            originalBoard.setBoard(1, 1, true);
            originalBoard.setBoard(2, 2, true);
            dao.setSelectedBoardName("TestBoard");

            dao.write(originalBoard);

            GameOfLifeBoard pomBoard = dao.read();

            assertNotSame(originalBoard, pomBoard);
            assertEquals(originalBoard, pomBoard);

            dao.deleteTable("TestBoard");

            connection.commit();
        }
    }
    @Test
    public void testResourceManagement() throws Exception {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, user, password);
            try (DbGameOfLifeBoardDao dao = new DbGameOfLifeBoardDao(connection)) {

                connection.setAutoCommit(false);

                GameOfLifeBoard board = new GameOfLifeBoard(3, 3);
                board.setBoard(0, 0, true);

                dao.setSelectedBoardName("ResourceTestBoard");

                dao.write(board);

                dao.deleteTable("ResourceTestBoard");

                connection.commit();

                assertFalse(connection.isClosed());
            }
        } catch (SQLException e) {
            throw new DaoReadException(e);
        } finally {
            if (connection != null) {
                assertTrue(connection.isClosed());
            }
        }
    }
    @Test
    public void testSuccessfulOperation() throws Exception {
        try (Connection connection = DriverManager.getConnection(dbUrl, user, password);
             DbGameOfLifeBoardDao dao = new DbGameOfLifeBoardDao(connection)) {

            connection.setAutoCommit(false);

            GameOfLifeBoard board = new GameOfLifeBoard(4, 4);
            board.setBoard(1, 1, true);

            dao.setSelectedBoardName("Nowa");

            dao.write(board);
            GameOfLifeBoard pomBoard = dao.read();

            assertEquals(board, pomBoard);
            dao.deleteTable("Nowa");
            connection.commit();
        }
    }
    @Test
    public void testErrorPositive() throws Exception {
        try (Connection connection = DriverManager.getConnection(dbUrl, user, password);
             DbGameOfLifeBoardDao dao = new DbGameOfLifeBoardDao(connection)) {

            connection.setAutoCommit(false);

            dao.setSelectedBoardName("zefgiygcuxcviog");

            assertThrows(DaoReadException.class, dao::read);
        }
    }
}