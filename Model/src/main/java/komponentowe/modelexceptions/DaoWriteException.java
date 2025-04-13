package komponentowe.modelexceptions;

public class DaoWriteException extends DaoProblemException {
    public DaoWriteException(Throwable cause) {
        super(cause);
    }

    public DaoWriteException() {
    }
}
