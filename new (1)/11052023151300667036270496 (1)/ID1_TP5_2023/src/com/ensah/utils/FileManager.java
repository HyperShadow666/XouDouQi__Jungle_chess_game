package com.ensah.utils;

import java.io.File;


public class FileManager {
	public static boolean fileExists(String filePathString) {
		File f = new File(filePathString);
		if (f.exists() && !f.isDirectory()) {
			return true;
		}
		return false;
	}

}
