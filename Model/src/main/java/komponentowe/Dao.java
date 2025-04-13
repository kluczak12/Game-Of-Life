package komponentowe;

import komponentowe.modelexceptions.DaoReadException;
import komponentowe.modelexceptions.DaoWriteException;
import komponentowe.modelexceptions.IncorrectClassException;

public interface Dao<T> {
    T read() throws DaoReadException, IncorrectClassException;

    void write(T obj) throws DaoWriteException;
}
