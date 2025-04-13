package komponentowe.modelexceptions;

public class DbSqlException extends RuntimeException {
    public DbSqlException(Throwable cause) {
        super(cause);
    }

    public DbSqlException() {
    }
}
