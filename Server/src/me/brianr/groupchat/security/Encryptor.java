package me.brianr.groupchat.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * A class to encrypt a string.
 * <strong>This class is not currently used for anything and is unimplemented.</strong>
 * <p>This class was not implemented because it was an optional feature in the project</p>
 * @author Brian Reich
 * @version 1.0
 */
public class Encryptor {
	
	/**
	 * Hash a string with MD5 hash.  <strong>MD5 HASHES ARE NOT SAFE.  USE RESPONSIBLY</strong>
	 * @param str the string to hash.
	 * @return the hashed string.
	 */
	public static String hashMD5(String str) {
		try {
			// Create message digest instance for hashing in MD5.
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			// A variable that will store the hashed string.
			String hashedString;
			
			// Add the bytes of the string to hash to the message digest.
			md.update(str.getBytes());
			
			// Get the hash's bytes.
			byte[] bytes = md.digest();
			
			
			// Because the bytes array is in decimal format (base 10), it must be changed to hexidecimal format (base-16).
			StringBuilder hexBuilder = new StringBuilder();
			for (byte b : bytes) {
				hexBuilder.append(Integer.toString((b & 0xFF) + 0x100, 16)).substring(1);
			}
			
			hashedString = hexBuilder.toString();
			
			return hashedString;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Hash a string with MD5 hash and a salt.  <strong>MD5 HASHES ARE NOT SAFE.  USE RESPONSIBLY</strong>
	 * @param str the string to hash.
	 * @param salt the salt to use in the hash.
	 * @return the hashed string.
	 */
	public static String hashMD5(String str, byte[] salt) {
		// A little salt and pepper never hurt anybody :)
		
		try {
			// Create message digest instance for hashing in MD5.
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			// A variable that will store the hashed string.
			String hashedString;
			
			// Add the bytes of the string to hash to the message digest.
			md.update(salt);
			
			// Get the hash's bytes.
			byte[] bytes = md.digest(str.getBytes());
			
			
			// Because the bytes array is in decimal format (base 10), it must be changed to hexidecimal format (base-16).
			StringBuilder hexBuilder = new StringBuilder();
			for (byte b : bytes) {
				hexBuilder.append(Integer.toString((b & 0xFF) + 0x100, 16)).substring(1);
			}
			
			hashedString = hexBuilder.toString();
			
			return hashedString;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Hash a string to PBKDF2.
	 * @param str the string to hash.
	 * @return the hashed string.
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static String hashPBKDF2(String str) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		// Because the PBKDF2 hash is based off of iterations, we need to have one.
		int it = 1000;
		
		// Get a salt.
		byte[] salt = getSalt();
		
		// Put the string into an array of chars. 
		char[] strChars = str.toCharArray();
		
		
		PBEKeySpec spec = new PBEKeySpec(strChars, salt, it, (64 * 8));
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = skf.generateSecret(spec).getEncoded(); // Encode the byte array.
		
		return it + ":" + toHex(salt) + ":" + toHex(hash);
	}
	
	/**
	 * Get a random salt.
	 * @return a random salt.
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
		
		// Create an array to hold the salt and add a random salt.
		byte[] salt = new byte[16];
		rand.nextBytes(salt);
		
		return salt;
	}
	
	
	/**
	 * Convert to a hex.
	 * @param arr the array of bytes to convert to hex.
	 * @return the string of hex characters.
	 */
	public static String toHex(byte[] arr) {
		BigInteger bigInt = new BigInteger(1, arr);
		String hex = bigInt.toString(16);
		int paddingLength = (arr.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0);
		}
		
		return hex;
	}
	
	
	// TESTER
	public static void main(String[] args) {
		String hashedBanana;
		try {
			hashedBanana = hashPBKDF2("Banana");
			System.out.println(hashedBanana);
			System.out.println(Decryptor.validatePBKDF2("Banana", hashedBanana));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
