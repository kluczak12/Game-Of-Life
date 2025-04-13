package GOL;

import komponentowe.GameOfLifeBoard;

import java.util.ResourceBundle;

public enum Population {
    Low("population.low", 0.1),
    Medium("population.medium", 0.3),
    High("population.high", 0.5);

    private final String level;
    private final double levelNumber;
    private String levelLabel;

    Population(String level, double levelNumber) {
        this.level = level;
        this.levelNumber = levelNumber;
    }

    public String getLevel(ResourceBundle bundle) {
        return bundle.getString(level);
    }

    public double getLevelNumber() {
        return levelNumber;
    }

    @Override
    public String toString() {
        return levelLabel != null ? levelLabel : getLevel(ResourceBundle.getBundle("lang"));
    }

    public void fillBoard(GameOfLifeBoard boardP) {
        for (int i = 0; i < boardP.getSizeRows(); i++) {
            for (int j = 0; j < boardP.getSizeColumns(); j++) {
                boardP.setCell(i, j,Math.random() < getLevelNumber());
            }
        }
        for (int i = 0; i < boardP.getSizeRows(); i++) {
            for (int j = 0; j < boardP.getSizeColumns(); j++) {
                boardP.getCell(i,j).setNeighbours(boardP, i, j);
            }
        }
    }

    public void setLevelLabel(String levelLabel) {
        this.levelLabel = levelLabel;
    }
}
