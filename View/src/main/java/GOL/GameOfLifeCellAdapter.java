package GOL;

import GOL.AppExceptions.RealTimeException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import komponentowe.GameOfLifeCell;

public class GameOfLifeCellAdapter {
    private final GameOfLifeCell gameCell;
    private final Rectangle cellRectangle;
    private final BooleanProperty property;

    public GameOfLifeCellAdapter(GameOfLifeCell gameCell, Rectangle cellRectangle) {
        this.gameCell = gameCell;
        this.cellRectangle = cellRectangle;

        try {
            this.property = JavaBeanBooleanPropertyBuilder.create()
                    .bean(gameCell)
                    .name("cellValue")
                    .getter("getCellValue")
                    .setter("updateState")
                    .build();
        } catch (NoSuchMethodException e) {
            throw new RealTimeException(e);
        }
    }

    public void makeBinding() {
        property.addListener((observable, oldValue, newValue) -> {
            cellRectangle.setFill(booleanToColor(newValue));
        });

        cellRectangle.setOnMouseClicked(event -> {
            Color currentColor = (Color) cellRectangle.getFill();

            boolean newValue = !colorToBoolean(currentColor);

            property.set(newValue);
        });
    }

    private Color booleanToColor(boolean value) {
        return value ? Color.MEDIUMTURQUOISE : Color.WHITE;
    }

    private boolean colorToBoolean(Color color) {
        return Color.MEDIUMTURQUOISE.equals(color);
    }
}
