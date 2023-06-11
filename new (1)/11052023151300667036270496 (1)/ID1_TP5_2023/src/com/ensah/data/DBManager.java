package com.ensah.data;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBManager {

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

    private DBManager() throws DataBaseException {

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

    /**
     * returns the unique instance of connection
     *
     * @return connection
     * @throws DataBaseException
     */
    public static Connection getInstance() throws DataBaseException {

        if (connection == null) {

            new DBManager();

        }

        return connection;
    }

    public static void createTables() throws SQLException, DataBaseException {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DBManager.getInstance();
            statement = connection.createStatement();



            // Création de la table Events
            String createEventsTableSQL = "CREATE TABLE IF NOT EXISTS Events ("
                    + "Id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                    + "message TEXT"
                    + ")";


            statement.executeUpdate(createEventsTableSQL);



            System.out.println("Table created successfully.");
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


    public static void main(String[] args) {
        try {
            Connection connection = DBManager.getInstance();
            // Utilisez la connexion pour interagir avec la base de données
            createTables();
            // Fermez la connexion lorsque vous avez terminé
            connection.close();
        } catch (DataBaseException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
