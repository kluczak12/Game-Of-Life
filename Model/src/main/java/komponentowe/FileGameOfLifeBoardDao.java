package komponentowe;

import komponentowe.modelexceptions.DaoReadException;
import komponentowe.modelexceptions.DaoWriteException;
import komponentowe.modelexceptions.IncorrectClassException;

import java.io.*;
import java.nio.file.Path;



public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {
    private final Path filePath;

    public FileGameOfLifeBoardDao(String fileName) {
        this.filePath = Path.of(fileName);
    }



    @Override
    public GameOfLifeBoard read() throws DaoReadException, IncorrectClassException {
        try (InputStream fis = new FileInputStream(filePath.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameOfLifeBoard) ois.readObject();
        } catch (IOException e) {
            throw new DaoReadException(e);
        } catch (ClassNotFoundException e) {
            throw new IncorrectClassException(e);
        }
    }

    @Override
    public void write(GameOfLifeBoard board) throws DaoWriteException {
        try (OutputStream fos = new FileOutputStream(filePath.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(board);
        } catch (IOException ioe) {
            throw new DaoWriteException(ioe);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
