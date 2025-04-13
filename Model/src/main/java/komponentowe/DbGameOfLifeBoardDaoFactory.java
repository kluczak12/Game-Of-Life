package komponentowe;

import komponentowe.modelexceptions.DbSqlException;
import komponentowe.modelexceptions.IncorrectClassException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbGameOfLifeBoardDaoFactory implements DaoFactory<GameOfLifeBoard> {
    @Override
    public Dao<GameOfLifeBoard> getDao(String dataName) {
        Connection con = null;
        try {
            con = openConnection();
        } catch (IncorrectClassException e) {
            throw new DbSqlException(e);
        }
        dbTable(con);
        return new DbGameOfLifeBoardDao(con);
    }

    public static Connection openConnection() throws IncorrectClassException {
        String dbDriver = "org.postgresql.Driver";
        String dbUrl = "jdbc:postgresql://localhost:5432/BoardBase";
        final String user = "z6";
        final String password = "21";
        try {
            Class.forName(dbDriver);
            return DriverManager.getConnection(dbUrl, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new IncorrectClassException(e);
        }
    }

    public static void dbTable(Connection connection) {
        String boardTable = "CREATE TABLE IF NOT EXISTS board (\n"
                + " boardId SERIAL PRIMARY KEY,\n"
                + " boardName VARCHAR(20),\n"
                + " rows INT NOT NULL,\n"
                + " columns INT NOT NULL\n"
                + ");";

        String cellTable = "CREATE TABLE IF NOT EXISTS cell (\n"
                + " boardId INT NOT NULL,\n"
                + " rowId INT NOT NULL,\n"
                + " columnId INT NOT NULL,\n"
                + " value BOOLEAN NOT NULL,\n"
                + " PRIMARY KEY (boardId, rowId, columnId),\n"
                + " FOREIGN KEY (boardId) REFERENCES board(boardId)\n"
                + ");";
        try {
            Statement statement = connection.createStatement();
            statement.execute(boardTable);
            statement.execute(cellTable);
        } catch (SQLException e) {
            throw new DbSqlException(e);
        }
    }
}