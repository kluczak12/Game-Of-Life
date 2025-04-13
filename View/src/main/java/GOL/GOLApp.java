package GOL;

import GOL.AppExceptions.SceneException;
import GOL.AppExceptions.UnexpectedException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class GOLApp extends Application {
    private Stage stage;
    private MenuController menuController;
    public static ResourceBundle bundleLanguage;
    private ResourceBundle bundleAuthors;
    private GOLBoardSimulationController simulationController;
    private static final Logger logger = LogManager.getLogger(GOLApp.class);


    @Override
    public void start(Stage stage) throws SceneException, UnexpectedException {
        try {
            this.stage = stage;
            Locale locale = Locale.getDefault();
            bundleLanguage = ResourceBundle.getBundle("lang", locale);
            bundleAuthors = ResourceBundle.getBundle(
                    locale.getLanguage().equals("pl") ? "GOL.AuthorsListsPL" : "GOL.AuthorsListsEN", locale);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            fxmlLoader.setResources(bundleLanguage);
            Scene menuScene = new Scene(fxmlLoader.load());
            menuController = fxmlLoader.getController();
            menuController.setApp(this);
            menuController.updateAuthorsLabel(bundleAuthors);
            stage.setTitle(bundleLanguage.getString("app.title"));
            stage.setScene(menuScene);
            stage.show();
            logger.info(bundleLanguage.getString("menu.load.success"));
        } catch (IOException e) {
            SceneException exception = new SceneException(e);
            logger.error(exception.getLocalizedMessage());
            throw exception;
        } catch (Exception e) {
            UnexpectedException exception = new UnexpectedException(e);
            logger.error(exception.getLocalizedMessage());
            throw exception;
        }
    }

    public void startSimulation() throws SceneException, UnexpectedException {
        try {
            int rowS = menuController.getRows();
            int columnS = menuController.getColumns();
            Population populationS = menuController.getPopulation();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("GOLBoardSimulation.fxml"));
            loader.setResources(bundleLanguage);
            Scene simulationScene = new Scene(loader.load());

            simulationController = loader.getController();
            simulationController.startSimulation(populationS, rowS, columnS);

            stage.setScene(simulationScene);
            stage.show();
            logger.info(bundleLanguage.getString("simulation.load.success"));
        } catch (IOException e) {
            SceneException exception = new SceneException(e);
            logger.error(exception.getLocalizedMessage());
            throw exception;
        } catch (Exception e) {
            UnexpectedException exception = new UnexpectedException(e);
            logger.error(exception.getLocalizedMessage());
            throw exception;
        }
    }

    public Stage getStage() {
        return stage;
    }

    public ResourceBundle getBundleLanguage() {
        return bundleLanguage;
    }

    public void setBundleLanguage(ResourceBundle bundleLanguage) {
        this.bundleLanguage = bundleLanguage;
    }

    public ResourceBundle getBundleAuthors() {
        return bundleAuthors;
    }

    public GOLBoardSimulationController getSimulationController() {
        return simulationController;
    }

    public void setBundleAuthors(ResourceBundle bundleAuthors) {
        this.bundleAuthors = bundleAuthors;
    }
}