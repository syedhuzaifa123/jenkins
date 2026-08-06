package com.tech.pulse.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Helper {

	public static boolean deleteFile(String path) {

		boolean flag = false;

		try {
			File file = new File(path);
			flag = file.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	public static boolean updateFileOld(InputStream is, String path) {

		boolean flag = false;

		try {

			byte b[] = new byte[is.available()];
			is.read(b);

			FileOutputStream fos = new FileOutputStream(path);
			fos.write(b);
			fos.flush();
			fos.close();
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	// Helper.java
	public static boolean updateFile(InputStream is, String path) {
		boolean success = false;
		try (InputStream inputStream = is; FileOutputStream fos = new FileOutputStream(path)) {

			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	// Register a file to be deleted on JVM exit
	public static void DeletionOnExit(String filePath) {
		File tempFile = new File(filePath);
		if (tempFile.exists()) {
			tempFile.deleteOnExit();
		}
	}

}
