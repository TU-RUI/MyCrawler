package com.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigUtils {

	public String readString(String key) {
		Properties prop = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("src/main/resources/conf.properties");
			prop.load(fis);
			return prop.getProperty(key);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	// 会覆盖
	public static boolean writeString(String key, String value) {
		Properties prop = new Properties();
		OutputStream fos;
		try {
			fos = new FileOutputStream("src/main/resources/conf.properties");
			prop.setProperty(key, value);
			prop.store(fos, "Update '" + key + "' value");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		ConfigUtils cu = new ConfigUtils();
		String s = cu.readString("id");
		System.out.println(s);
		writeString("a", "21");
	}
}
