package komponentowe;

public class PlainGameOfLifeSimulation implements GameOfLifeSimulator {
    @Override
    public void doStep(GameOfLifeBoard board) {
        GameOfLifeBoard nextBoard = new GameOfLifeBoard(board.getSizeRows(), board.getSizeColumns());
        for (int i = 0; i < nextBoard.getSizeRows(); i++) {
            for (int j = 0; j < nextBoard.getSizeColumns(); j++) {
                nextBoard.setBoard(i, j, board.getBoard()[i][j].nextState());
            }
        }
        for (int i = 0; i < board.getSizeRows(); i++) {
            for (int j = 0; j < board.getSizeColumns(); j++) {
                board.setBoard(i, j, nextBoard.getBoard()[i][j].getCellValue());
            }
        }
    }
}
