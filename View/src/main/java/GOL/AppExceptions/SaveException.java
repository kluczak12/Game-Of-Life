package GOL.AppExceptions;

public class SaveException extends GOLException {
    public SaveException(Throwable cause) {
        super(cause);
    }

    @Override
    protected String getErrorCode() {
        return "file.save.error";
    }
}