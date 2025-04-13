package GOL.AppExceptions;


public class UnexpectedException extends GOLException {
    public UnexpectedException(Throwable cause) {
        super(cause);
    }

    @Override
    protected String getErrorCode() {
        return "unexpected.simulation.error";
    }
}
