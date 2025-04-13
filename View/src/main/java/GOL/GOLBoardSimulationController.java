package GOL;

import GOL.AppExceptions.*;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import komponentowe.*;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.control.*;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class GOLBoardSimulationController {
    private static final Logger logger = LogManager.getLogger(GOLApp.class);
    private GameOfLifeBoard GOLBoardC;
    private FileGameOfLifeBoardDaoFactory daoFile = new FileGameOfLifeBoardDaoFactory();
    private DbGameOfLifeBoardDaoFactory daoDatabase = new DbGameOfLifeBoardDaoFactory();
    private Population population;
    private int activeRow = 0;
    private int activeColumn = 0;
    private boolean highlightActiveCell = true;

    @FXML
    private TextField boardNameField;
    @FXML
    private ComboBox<String> boardNameComboBox;
    @FXML
    private GridPane gridBoard;


    @FXML
    public void initialize() {
        this.population = Population.Medium;
        this.GOLBoardC = new GameOfLifeBoard(10, 10);
        fillCells();
        initializeCellKeys();
        loadBoardNamesFromDb();
    }

    public void initializeCellKeys() {
        gridBoard.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    if (activeRow > 0) activeRow--;
                    break;
                case S:
                    if (activeRow < GOLBoardC.getSizeRows() - 1) activeRow++;
                    break;
                case A:
                    if (activeColumn > 0) activeColumn--;
                    break;
                case D:
                    if (activeColumn < GOLBoardC.getSizeColumns() - 1) activeColumn++;
                    break;
                case SPACE:
                    GOLBoardC.getCell(activeRow, activeColumn).updateState(
                            !GOLBoardC.getCell(activeRow, activeColumn).getCellValue()
                    );
                    break;
            }
            fillCells();
        });


        gridBoard.setFocusTraversable(true);
    }

    public void fillCells() {
        gridBoard.getChildren().clear();
        for (int i = 0; i < GOLBoardC.getSizeRows(); i++) {
            for (int j = 0; j < GOLBoardC.getSizeColumns(); j++) {
                Rectangle cell = new Rectangle(30, 30);
                cell.setFill(GOLBoardC.getCell(i, j).getCellValue() ? Color.MEDIUMTURQUOISE : Color.WHITE);

                if (highlightActiveCell && i == activeRow && j == activeColumn) {
                    cell.setFill(Color.MEDIUMVIOLETRED);
                } else {
                    cell.setFill(GOLBoardC.getCell(i, j).getCellValue() ? Color.MEDIUMTURQUOISE : Color.WHITE);
                }

                GameOfLifeCell gameCell = GOLBoardC.getCell(i, j);
                GameOfLifeCellAdapter adapter = new GameOfLifeCellAdapter(gameCell, cell);
                adapter.makeBinding();

                gridBoard.add(cell, j, i);
            }
        }
    }

    public void startSimulation(Population population, int rows, int columns) {
        this.population = population;
        this.GOLBoardC = new GameOfLifeBoard(rows, columns);
        this.population.fillBoard(this.GOLBoardC);
        fillCells();
    }

    @FXML
    public void saveBoard() throws SaveException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GOL Board", "*.bd"));

        File file = fileChooser.showSaveDialog(gridBoard.getScene().getWindow());
        if (file != null) {
            try (FileGameOfLifeBoardDao fileDao = daoFile.getDao(file.getAbsolutePath())) {
                fileDao.write(GOLBoardC);
            } catch (Exception e) {
                SaveException exception = new SaveException(e);
                logger.error(exception.getLocalizedMessage());
                throw exception;
            }
        }
    }

    @FXML
    public void loadBoard() throws UnexpectedException, LoadException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GOL Board", "*.bd"));

        File file = fileChooser.showOpenDialog(gridBoard.getScene().getWindow());
        if (file != null) {
            try {
                FileGameOfLifeBoardDao fileDao = daoFile.getDao(file.getAbsolutePath());
                GOLBoardC = fileDao.read();
                fillCells();
            } catch (Exception e) {
                LoadException exception = new LoadException(e);
                logger.error(exception.getLocalizedMessage());
                throw exception;
            }
        }
    }

    @FXML
    public void saveDb() {
        String boardName = boardNameField.getText();
        if (boardName == null || boardName.isBlank()) {
            return;
        }

        try {
            Dao<GameOfLifeBoard> dao = daoDatabase.getDao(boardName);
            if (dao instanceof DbGameOfLifeBoardDao dbDao) {
                dbDao.setSelectedBoardName(boardName);
                dbDao.write(GOLBoardC);
                loadBoardNamesFromDb();
            }
        } catch (Exception e) {
            throw new RealTimeException(e);
        }
    }


    @FXML
    public void loadDb() {
        String selectedBoard = boardNameComboBox.getValue();
        if (selectedBoard == null || selectedBoard.isBlank()) {
            return;
        }
        try {
            Dao<GameOfLifeBoard> dao = daoDatabase.getDao(selectedBoard);
            if (dao instanceof DbGameOfLifeBoardDao dbDao) {
                dbDao.setSelectedBoardName(selectedBoard);
                GOLBoardC = dbDao.read();

                fillCells();
            }
        } catch (Exception e) {
            throw new RealTimeException(e);
        }
    }


    @FXML
    public void doStep() {
        this.GOLBoardC.doSimulationStep();
        highlightActiveCell = false;
        fillCells();
    }

    private void loadBoardNamesFromDb() {
        try (Connection connection = DbGameOfLifeBoardDaoFactory.openConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT boardName FROM board");
            boardNameComboBox.getItems().clear();
            while (resultSet.next()) {
                String boardName = resultSet.getString("boardName");
                if (boardName != null && !boardName.isBlank()) {
                    boardNameComboBox.getItems().add(boardName);
                }
            }
        } catch (Exception e) {
            throw new RealTimeException(e);
        }
    }
}
