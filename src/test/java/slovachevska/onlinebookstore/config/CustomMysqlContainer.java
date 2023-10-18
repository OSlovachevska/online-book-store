package slovachevska.onlinebookstore.config;

import org.testcontainers.containers.MySQLContainer;

public class CustomMysqlContainer extends MySQLContainer<CustomMysqlContainer> {

    private static final String DB_IMAGE = "mysql:8";

    private static CustomMysqlContainer customMysqlContainer;

    private CustomMysqlContainer() {
        super(DB_IMAGE);
    }

    public static synchronized CustomMysqlContainer getInstance() {
        if (customMysqlContainer == null) {
            customMysqlContainer = new CustomMysqlContainer();
        }

        return customMysqlContainer;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("TEST_DB_URL", customMysqlContainer.getJdbcUrl());
        System.setProperty("TEST_DB_USERNAME", customMysqlContainer.getUsername());
        System.setProperty("TEST_DB_PASSWORD", customMysqlContainer.getPassword());
    }

    @Override
    public void stop() {
    }
}
