package komponentowe.modelexceptions;

public class DaoReadException extends DaoProblemException {
    public DaoReadException(Throwable cause) {
        super(cause);
    }

    public DaoReadException() {
        super();
    }
}
