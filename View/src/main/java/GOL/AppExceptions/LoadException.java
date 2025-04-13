package GOL.AppExceptions;

public class LoadException extends GOLException {
    @Override
    protected String getErrorCode() {
        return "board.load.error";
    }

    public LoadException(Throwable cause) {
        super(cause);
    }
}
