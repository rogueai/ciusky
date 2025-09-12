package com.rogueai.collection.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class DataSourceInitializer implements InitializingBean, DisposableBean {

    private static final Log logger = LogFactory.getLog(DataSourceInitializer.class);

    @Nullable
    private DataSource dataSource;

    @Nullable
    private DatabasePopulator databasePopulator;

    private boolean enabled = true;

    private String checkInitializationQuery;

    /**
     * The {@link DataSource} for the database to populate when this component is initialized and to clean up when this component is shut down.
     * <p>
     * This property is mandatory with no default provided.
     *
     * @param dataSource the DataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Set the {@link DatabasePopulator} to execute during the bean initialization phase.
     *
     * @param databasePopulator the {@code DatabasePopulator} to use during initialization
     * @see #setDatabaseCleaner
     */
    public void setDatabasePopulator(DatabasePopulator databasePopulator) {
        this.databasePopulator = databasePopulator;
    }

    /**
     * Flag to explicitly enable or disable the {@linkplain #setDatabasePopulator database populator} and {@linkplain #setDatabaseCleaner database cleaner}.
     *
     * @param enabled {@code true} if the database populator and database cleaner should be called on startup and shutdown, respectively
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * The setter method of checkInitializationQuery attribute.
     *
     * @param checkInitializationQuery the checkInitializationQuery to set
     */
    public void setCheckInitializationQuery(String checkInitializationQuery) {
        this.checkInitializationQuery = checkInitializationQuery;
    }

    /**
     * Use the {@linkplain #setDatabasePopulator database populator} to set up the database.
     */
    @Override
    public void afterPropertiesSet() {
        execute(databasePopulator);
    }

    /**
     * Use the {@linkplain #setDatabaseCleaner database cleaner} to clean up the database.
     */
    @Override
    public void destroy() {
        // do nothing
    }

    /**
     * Database creation if not initialized.
     */
    private void execute(@Nullable DatabasePopulator populator) {
        Assert.state(dataSource != null, "DataSource must be set");

        boolean initialized = false;
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(checkInitializationQuery);
                while (resultSet.next()) {
                    Timestamp initDate = resultSet.getTimestamp(1);
                    logger.info("Database inizialized date " + initDate);
                    initialized = true;
                }
            } finally {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        } catch (SQLException ex) {
            logger.warn("Database seems to be not initialized.");
        } catch (Exception ex) {
            throw new UncategorizedScriptException("Failed to execute database script", ex);
        }
        if ((enabled || !initialized) && populator != null) {
            logger.info("Inizializing database...");
            DatabasePopulatorUtils.execute(populator, dataSource);
            logger.info("Database initialized!");
        }
    }

}
