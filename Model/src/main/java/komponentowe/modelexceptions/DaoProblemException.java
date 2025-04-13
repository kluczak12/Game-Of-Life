package komponentowe.modelexceptions;

import java.io.IOException;

public class DaoProblemException extends IOException {
    public DaoProblemException(Throwable cause) {
        super(cause);
    }

    public DaoProblemException() {
    }
}
