package GOL.AppExceptions;


public class SceneException extends GOLException {
    public SceneException(Throwable cause) {
        super(cause);
    }

    @Override
    protected String getErrorCode() {
        return "scene.load.error";
    }
}
