package hw5;

import org.jetbrains.annotations.NotNull;
import initialize.JDBCCredentials;
import initialize.FlywayInit;

public final class Main {
    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    public static void main(@NotNull String[] args) {
        FlywayInit.initDb();
    }
}
