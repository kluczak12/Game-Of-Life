package komponentowe;

import komponentowe.modelexceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DbGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {
    private Connection connection;
    private PreparedStatement addBoard;
    private PreparedStatement removeBoard;
    private PreparedStatement getBoard;
    private PreparedStatement addCell;
    private PreparedStatement getCellsByBoard;
    private PreparedStatement updateBoard;
    private PreparedStatement removeCellsByBoard;
    private String selectedBoardName;
    private static final Logger logger = LogManager.getLogger(DbGameOfLifeBoardDao.class);


    public DbGameOfLifeBoardDao(Connection con) {
        this.connection = con;
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            addBoard = connection.prepareStatement("INSERT INTO board (boardName, rows, columns) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            removeBoard = connection.prepareStatement("DELETE FROM board WHERE boardName = ?");
            removeCellsByBoard = connection.prepareStatement("DELETE FROM cell WHERE boardId = ?");
            getBoard = connection.prepareStatement("SELECT boardId, rows, columns FROM board WHERE boardName = ?");
            addCell = connection.prepareStatement(
                    "INSERT INTO cell (boardId, rowId, columnId, value) VALUES (?, ?, ?, ?)");
            getCellsByBoard = connection.prepareStatement(
                    "SELECT rowId, columnId, value FROM cell WHERE boardId = ?");
            updateBoard = connection.prepareStatement("UPDATE board SET rows = ?, columns = ? WHERE boardId = ?");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DbSqlException(e);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if (addBoard != null) {
                addBoard.close();
            }
            if (removeBoard != null) {
                removeBoard.close();
            }
            if (getBoard != null) {
                getBoard.close();
            }
            if (addCell != null) {
                addCell.close();
            }
            if (getCellsByBoard != null) {
                getCellsByBoard.close();
            }
            if (updateBoard != null) {
                updateBoard.close();
            }

            if (removeCellsByBoard != null) {
                removeCellsByBoard.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DbSqlException(e);
        }
    }

    @Override
    public GameOfLifeBoard read() throws DaoReadException {
        try {
            getBoard.setString(1, selectedBoardName);
            ResultSet result = getBoard.executeQuery();
            if (!result.next()) {
                throw new DaoReadException();
            }
            int boardId = result.getInt("boardId");
            final int rows = result.getInt("rows");
            final int columns = result.getInt("columns");

            getCellsByBoard.setInt(1, boardId);

            GameOfLifeBoard board = new GameOfLifeBoard(rows, columns);

            try (ResultSet resultCell = getCellsByBoard.executeQuery()) {
                while (resultCell.next()) {
                    final int rowId = resultCell.getInt("rowId");
                    final int columnId = resultCell.getInt("columnId");
                    boolean value = resultCell.getBoolean("value");
                    board.setBoard(rowId, columnId, value);
                }
            }
            connection.commit();
            return board;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                throw new DaoReadException(rollbackEx);
            }
            throw new DbSqlException(e);
        }
    }

    @Override
    public void write(GameOfLifeBoard obj) throws DaoWriteException {
        try {
            int boardId = -1;
            getBoard.setString(1, selectedBoardName);
            try (ResultSet result = getBoard.executeQuery()) {
                if (result.next()) {
                    boardId = result.getInt("boardId");
                    removeCellsByBoard.setInt(1, boardId);
                    removeCellsByBoard.executeUpdate();
                    removeBoard.setString(1, selectedBoardName);
                    removeBoard.executeUpdate();
                }
            }

            addBoard.setString(1, selectedBoardName);
            addBoard.setInt(2, obj.getSizeRows());
            addBoard.setInt(3, obj.getSizeColumns());
            addBoard.executeUpdate();

            try (ResultSet result = getBoard.executeQuery()) {
                if (result.next()) {
                    boardId = result.getInt("boardId");
                }
            }

            for (int row = 0; row < obj.getSizeRows(); row++) {
                for (int col = 0; col < obj.getSizeColumns(); col++) {
                    boolean value = obj.getCell(row, col).getCellValue();
                    addCell.setInt(1, boardId);
                    addCell.setInt(2, row);
                    addCell.setInt(3, col);
                    addCell.setBoolean(4, value);
                    addCell.executeUpdate();
                }
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                throw new DaoWriteException(rollbackEx);
            }
            throw new DbSqlException(e);
        }
    }


    public void deleteTable(String delTable) {
        try {
            int boardId = -24;
            getBoard.setString(1, delTable);
            try (ResultSet result = getBoard.executeQuery()) {
                if (result.next()) {
                    boardId = result.getInt("boardId");
                } else {
                    throw new DbIllegalException();
                }
            }

            removeCellsByBoard.setInt(1, boardId);
            removeCellsByBoard.executeUpdate();

            removeBoard.setString(1, delTable);
            removeBoard.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                throw new DbSqlException(rollbackEx);
            }
            throw new DbSqlException(e);
        }
    }

    public void setSelectedBoardName(String selectedBoardName) {
        this.selectedBoardName = selectedBoardName;
    }
}