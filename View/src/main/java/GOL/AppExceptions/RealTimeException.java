package GOL.AppExceptions;

import GOL.GOLApp;

import java.util.ResourceBundle;

public class RealTimeException extends RuntimeException {
    private final ResourceBundle bundle = ResourceBundle.getBundle("lang", GOLApp.bundleLanguage.getLocale());


    public RealTimeException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getLocalizedMessage() {
        return bundle.getString("real.time.error");
    }
}
