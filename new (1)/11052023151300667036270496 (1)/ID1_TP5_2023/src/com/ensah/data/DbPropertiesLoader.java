package com.ensah.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Permet de lire le fichier de configuration de type properties
 */
public class DbPropertiesLoader {

	/**
	 * load the property configuration file
	 *
	 * @return file name
	 * @throws IOException
	 */
	public static Properties loadPoperties(String pName) throws IOException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream propInputStream = loader.getResourceAsStream(pName);
		Properties properties = new Properties();
		properties.load(propInputStream);
		return properties;
	}



}
