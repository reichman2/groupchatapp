package me.brianr.groupchat.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Decrypt encrypted messages.
 * <strong>This class is not currently implemented for any use.</strong>
 * @author Brian Reich
 * @version 1.0
 */
public class Decryptor {
	public static boolean validatePBKDF2(String origStr, String hashedPass) throws InvalidKeySpecException, NoSuchAlgorithmException {
		String[] sections = hashedPass.split(":");
		int it = Integer.parseInt(sections[0]);
		byte[] salt = fromHex(sections[1]);
		byte[] hash = fromHex(sections[2]);
		
		PBEKeySpec spec = new PBEKeySpec(origStr.toCharArray(), salt, it, hash.length * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] test = skf.generateSecret(spec).getEncoded();
		
		int lengthDifference = hash.length ^ test.length;
		for (int i = 0; i < hash.length && i < test.length; i++) {
			lengthDifference |= hash[i] ^ test[i];
		}
		
		return lengthDifference == 0;
	}
	
	public static byte[] fromHex(String hex) {
		// Create an array of bytes from the hex string.
		byte[] bytes = new byte[hex.length() / 2];
		
		// Add the
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring((2 * i), (2 * i + 2)), 16);
		}
		
		return bytes;
	}
}
