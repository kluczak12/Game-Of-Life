package GOL;

import GOL.AppExceptions.RealTimeException;
import GOL.AppExceptions.SceneException;
import GOL.AppExceptions.UnexpectedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class MenuController {
    @FXML
    public Label authorsLabel;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Spinner<Integer> rows;
    @FXML
    private Spinner<Integer> columns;
    @FXML
    private ChoiceBox<Population> population;
    private int currentRows;
    private int currentColumns;


    private GOLApp app;

    private static final Logger logger = LogManager.getLogger(MenuController.class);

    public int getRows() {
        return rows.getValue();
    }

    public int getColumns() {
        return columns.getValue();
    }

    public Population getPopulation() {
        return population.getValue();
    }

    @FXML
    private void initialize() {
        rows.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 30, 8));
        columns.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 30, 8));
        languageComboBox.getItems().addAll("Polski", "English");
        languageComboBox.setValue("Polski");
        languageComboBox.setOnAction(event -> {
            try {
                changeLanguage(languageComboBox.getValue());
            } catch (SceneException e) {
                RealTimeException exception = new RealTimeException(e);
                logger.error(exception.getLocalizedMessage());
                throw exception;
            }
        });
        population.getItems().addAll(Population.values());
        population.setValue(Population.Medium);
    }

    public void setApp(GOLApp app) {
        this.app = app;
    }

    public void startProgram(ActionEvent actionEvent) throws SceneException, UnexpectedException {
        app.startSimulation();
    }

    private void changeLanguage(String selectedLanguage) throws SceneException {
        currentRows = rows.getValue();
        currentColumns = columns.getValue();
        Locale locale = selectedLanguage.equals("Polski") ? new Locale("pl") : new Locale("en");
        ResourceBundle bundle = ResourceBundle.getBundle("lang", locale);
        app.setBundleLanguage(bundle);
        ResourceBundle authorsBundle;

        if (locale.getLanguage().equals("pl")) {
            authorsBundle = ResourceBundle.getBundle("GOL.AuthorsListsPL", locale);
        } else {
            authorsBundle = ResourceBundle.getBundle("GOL.AuthorsListsEN", locale);
        }
        app.setBundleAuthors(authorsBundle);

        reloadView();
        logger.info(bundle.getString("language.change.succes"));
    }


    private void reloadView() throws SceneException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            loader.setResources(app.getBundleLanguage());
            Pane newRoot = loader.load();

            MenuController controll = loader.getController();
            controll.setApp(app);

            controll.languageComboBox.setValue(app.getBundleLanguage().getLocale().getLanguage().equals("pl")
                    ? "Polski" : "English");
            controll.updatePopulationItems(app.getBundleLanguage());
            controll.updateAuthorsLabel(app.getBundleAuthors());
            controll.rows.getValueFactory().setValue(currentRows);
            controll.columns.getValueFactory().setValue(currentColumns);

            app.getStage().getScene().setRoot(newRoot);
        } catch (IOException e) {
            SceneException exception = new SceneException(e);
            logger.error(exception.getLocalizedMessage());
            throw exception;
        }
    }

    private void updatePopulationItems(ResourceBundle bundle) {
        for (Population p : Population.values()) {
            p.setLevelLabel(p.getLevel(bundle));
        }
        population.getItems().setAll(Population.values());
        population.setValue(Population.Medium);
    }

    void updateAuthorsLabel(ResourceBundle authorsBundle) {
        String author1 = authorsBundle.getString("author1");
        String author2 = authorsBundle.getString("author2");
        String authors = authorsBundle.getString("authors");
        authorsLabel.setText(authors + ":  \n" + author1 + ", " + author2);
    }

}