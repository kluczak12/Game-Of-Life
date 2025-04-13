package GOL.AppExceptions;

import GOL.GOLApp;

import java.util.ResourceBundle;

public abstract class GOLException extends Exception {
    private final ResourceBundle bundle = ResourceBundle.getBundle("lang", GOLApp.bundleLanguage.getLocale());

    public GOLException() {super();}

    public GOLException(Throwable cause) {super(cause);}

    protected abstract String getErrorCode();

    @Override
    public String getLocalizedMessage() {return bundle.getString(getErrorCode());}
}
