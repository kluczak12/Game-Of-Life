package komponentowe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileGameOfLifeBoardDaoFactoryTest {

    @Test
    void getDao() {
        String testPath = "testPa";
        FileGameOfLifeBoardDaoFactory factoryTest = new FileGameOfLifeBoardDaoFactory();

        Dao<GameOfLifeBoard> dao = factoryTest.getDao(testPath);
        assertNotNull(dao);
    }
}