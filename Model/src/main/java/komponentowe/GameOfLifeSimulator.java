package komponentowe;

import java.io.Serializable;

public interface GameOfLifeSimulator extends Serializable {
    void doStep(GameOfLifeBoard board);
}
