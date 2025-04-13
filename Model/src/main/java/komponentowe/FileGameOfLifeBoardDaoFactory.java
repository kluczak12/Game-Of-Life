package komponentowe;

public class FileGameOfLifeBoardDaoFactory implements DaoFactory<GameOfLifeBoard> {
    @Override
    public FileGameOfLifeBoardDao getDao(String fileName) {
        return new FileGameOfLifeBoardDao(fileName);
    }
}