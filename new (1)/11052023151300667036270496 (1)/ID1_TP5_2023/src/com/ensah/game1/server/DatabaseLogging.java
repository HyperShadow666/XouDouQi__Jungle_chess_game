package com.ensah.game1.server;

import com.ensah.data.DBManager;
import com.ensah.data.DataBaseException;
import com.ensah.data.DbPropertiesLoader;
import org.apache.log4j.Logger;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.sql.PreparedStatement;



public class DatabaseLogging {
    private Logger logger = Logger.getLogger(getClass());

    private static String dbUrl = " jdbc:h2:~/jeuChess\n";
    private static String login = "root";
    private static String password = "";
    private static String driver;

    /** pour y stocker l'objet de connexion */
    private static Connection connection;

    /**
     * Constructor
     *
     * @throws DataBaseException
     */

    public DatabaseLogging() throws DataBaseException {

        try {
            // Lire le fichier de configuration conf.propeties
            Properties dbProperties = DbPropertiesLoader.loadPoperties("conf.properties");
            dbUrl = dbProperties.getProperty("db.url");
            login = dbProperties.getProperty("db.login");
            password = dbProperties.getProperty("db.password");
            driver = dbProperties.getProperty("db.driver");

            // charger le pilote
            Class.forName(driver);

            // Créer une connexion à la base de données
            connection = DriverManager.getConnection(dbUrl, login, password);

        } catch (Exception ex) {
            // tracer cette erreur
            logger.error(ex);
            // raise the exception stack
            throw new DataBaseException(ex);
        }

    }


    public static Connection getInstance() throws DataBaseException {

        if (connection == null) {

            new DatabaseLogging();

        }
        return connection;
    }

    public static void insertUpdate(String query) throws DataBaseException {
        try {
            Connection connection = getInstance();
            String insertQuery = "INSERT INTO Events (message) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, query);
            statement.executeUpdate();
            statement.close();
            System.out.println("Message inserted successfully into the database.");
        } catch (SQLException ex) {
            throw new DataBaseException(ex);
        }
    }


    public static void main(String[] args) {

    }
}
