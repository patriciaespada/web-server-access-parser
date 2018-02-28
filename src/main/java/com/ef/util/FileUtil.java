package com.ef.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility methods to file manipulation.
 * @author patriciaespada
 *
 */
public class FileUtil {
	
	private FileUtil() { }
	
	/**
	 * Get file md5 check sum.
	 * @param file
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String md5CheckSum(String file) throws NoSuchAlgorithmException, IOException {
		String md5CheckSum = "";

		MessageDigest md = MessageDigest.getInstance("MD5");
		try (FileInputStream fis = new FileInputStream(file)) {
			byte[] dataBytes = new byte[1024];

			int nread = 0;
			while ((nread = fis.read(dataBytes)) != -1) {
				md.update(dataBytes, 0, nread);
			}

			byte[] mdbytes = md.digest();

			//convert the byte to hex format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			md5CheckSum = sb.toString();
		}

		return md5CheckSum;
	}
	
}
