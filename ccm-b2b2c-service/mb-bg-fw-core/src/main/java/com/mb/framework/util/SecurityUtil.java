package com.mb.framework.util;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.mb.framework.util.log.LogHelper;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class SecurityUtil {
	private static final LogHelper LOGGER = LogHelper
			.getInstance("SecurityUtil");

	private static final String AES_CBC_PKCS5PADDING_ALGO = "AES/CBC/PKCS5Padding";
	private static final String SECRET_KEY = "test";
	private static String salt;
	private static int pswdIterations = 65536;
	private static int keySize = 128; //Nirenj updated to 128
	private static byte[] ivBytes;

	private static final String AES_ALGO = "AES";

	// SecretKeySpec - AES allows 128, 192 or 256 bit key length. That is 16, 24
	// or 32 byte.( :- 16, 24 or 32 length of byte array )
	// Needs to keep SecretKey safe

	// 128 bit length of secret key(16 byte) private static final byte[]
	private static final byte[] KEY_VALUE_128_BIT = new byte[] { 'T', 'h', 'e',
			'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };

	// 192 bit length of secret key(24 byte) private static final byte[]
//	private static final byte[] KEY_VALUE_192_BIT = new byte[] { 'T', 'h', 'e',
//			'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y',
//			'T', 'h', 'e', 'B', 'e', 's', 't', 'S', };
//
//	// 256 bit length of secret key(32 byte)
//	private static final byte[] KEY_VALUE_256_BIT = new byte[] { 'T', 'h', 'e',
//			'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y',
//			'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't',
//			'K', 'e', 'y', };

	/********************** START - MD5 **********************/
	/**
	 * This method encrypts the password as per Md5 Algorithm.
	 * 
	 * @param String
	 * @return String
	 * 
	 */
	public static String encryptMd5(String plaintext) throws Exception {
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("SHA"); // step 2
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(e.getMessage());
		}

		try {
			md.update(plaintext.getBytes("UTF-8")); // step 3
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e.getMessage());
		}

		byte raw[] = md.digest(); // step 4
		return new BASE64Encoder().encode(raw); // step 5
	}

	/************************* END - MD5 ***********************/

	/********************** START - ASE_ALGO *******************/

	/**
	 * 
	 * This method is used for encrypt by using Algorithm - AES
	 * 
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public static String encryptAES(String data) throws Exception {

		Key key = generateKeyAES(); // step 1

		Cipher c = Cipher.getInstance(AES_ALGO); // step 2
		c.init(Cipher.ENCRYPT_MODE, key); // step 3

		byte[] encVal = c.doFinal(data.getBytes()); // step 4
		String encryptedValue = new BASE64Encoder().encode(encVal); // step 5

		return encryptedValue;
	}

	/**
	 * 
	 * This method is used for decrypt by using Algorithm - AES
	 * 
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public static String decryptAES(String encryptedData) throws Exception {

		Key key = generateKeyAES(); // step 1

		Cipher c = Cipher.getInstance(AES_ALGO); // step 2
		c.init(Cipher.DECRYPT_MODE, key); // step 3

		byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData); // step
																				// 4
		byte[] decValue = c.doFinal(decordedValue); // step 5
		return new String(decValue);
	}

	private static Key generateKeyAES() {
		return new SecretKeySpec(KEY_VALUE_128_BIT, AES_ALGO);
	}

	/******************** END - ASE_ALGO *****************************/

	/********** START - 256-bit AES/CBC/PKCS5Padding (based on PKCS#5s PBKDF2) ************/

	/**
	 * As per My Company SECURITY GUIDELINES How to fight against pre-computed look-up
	 * tables? Use a salt Iterate many times (e.g. 50,000 times) e.g.
	 * Hash(Hash(Hash(Hash(Hash(salt+credentials))))) Available solutions:
	 * PBKDF2 (Password-Based Key Derivation Function #2) 256-bit AES (based on
	 * PKCS#5s PBKDF2
	 * 
	 */

	/**
	 * 
	 * This method is used for encrypt by using Algorithm - AES/CBC/PKCS5Padding
	 * 
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public static String encryptAESPBKDF2(String plainText) throws Exception {

		// get salt
		salt = generateSaltAESPBKDF2();
		byte[] saltBytes = salt.getBytes("UTF-8");

		// Derive the key
		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), saltBytes,
				pswdIterations, keySize);

		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

		// encrypt the message
		Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING_ALGO);
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		return new Base64().encodeAsString(encryptedTextBytes);
	}

	/**
	 * 
	 * This method is used for decrypt by using Algorithm - AES/CBC/PKCS5Padding
	 * 
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static String decryptAESPBKDF2(String encryptedText)
			throws Exception {

		byte[] saltBytes = salt.getBytes("UTF-8");
		byte[] encryptedTextBytes = new Base64().decodeBase64(encryptedText);

		// Derive the key
		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), saltBytes,
				pswdIterations, keySize);

		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

		// Decrypt the message
		Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING_ALGO);
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));

		byte[] decryptedTextBytes = null;
		try {
			decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
		} catch (IllegalBlockSizeException e) {

			LOGGER.error("error " + e.getMessage());
		} catch (BadPaddingException e) {
			LOGGER.error("error " + e.getMessage());
		}

		return new String(decryptedTextBytes);
	}

	/**
	 * 
	 * This method is used for generate slat
	 * 
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public static String generateSaltAESPBKDF2() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		return new String(bytes);
	}

	/************ END - 256-bit AES/CBC/PKCS5Padding (based on PKCS#5s PBKDF2) **********/

	/**
	 * Thisis main method to run program
	 * 
	 * @param args
	 *            []
	 */
	public static void main(String args[]) {

		try {
			Security.addProvider(new com.sun.crypto.provider.SunJCE());// step 1
			// PasswordUtil passwordEncryptAgent = new PasswordUtil("ASCII");
			String password = "password";
			System.out.println("Plain Password : " + password + "\n");

			/******************************* START - MD5 *****************************************/
			System.out.println("MD5 - Encrypted Password:  "
					+ encryptMd5(password) + "\n");
			/******************************* END - MD5 *******************************************/

			/******************************* START - ASE_ALGO ************************************/
			String passwordEnc = encryptAES(password);
			String passwordDec = decryptAES(passwordEnc);

			System.out.println("ASE - Encrypted Password : " + passwordEnc);
			System.out.println("ASE - Decrypted Password : " + passwordDec
					+ "\n");
			/******************************* END - ASE_ALGO ************************************/

			/****** START - 256-bit AES/CBC/PKCS5Padding (based on PKCS#5s PBKDF2) ************/

			String encryptedText = encryptAESPBKDF2(password);
			System.out
					.println("256-bit AES/CBC/PKCS5Padding (based on PKCS#5s PBKDF2) - Encrypted string:"
							+ encryptedText);
			System.out
					.println("256-bit AES/CBC/PKCS5Padding (based on PKCS#5s PBKDF2) - Decrypted string:"
							+ decryptAESPBKDF2(encryptedText));

			/******** END - 256-bit AES/CBC/PKCS5Padding (based on PKCS#5s PBKDF2) ************/
		} catch (Exception ex) {
			LOGGER.error("error " + ex.getMessage());
		}

	}

}
