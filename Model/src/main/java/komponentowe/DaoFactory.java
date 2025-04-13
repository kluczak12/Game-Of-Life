package komponentowe;

public interface DaoFactory<T> {
    Dao<T> getDao(String fileName);
}
