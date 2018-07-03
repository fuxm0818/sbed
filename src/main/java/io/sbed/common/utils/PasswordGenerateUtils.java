package io.sbed.common.utils;

import org.apache.shiro.crypto.hash.Sha256Hash;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class PasswordGenerateUtils {

	private static final String digital = "0123456789";
	private static final String letter = "abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
	private static final String character = "~!@#$%^&*";
	private static final String dlc = digital + letter + character;

	public static String generatePassword() {
		StringBuffer password = new StringBuffer();
		Map<String, String> charMap = new HashMap<String, String>();
		/* 1.生成一个随机数字 */
		String digitalString = genRandomString(digital, 1);
		charMap.put(digitalString, digitalString);
		/* 2.生成一个随机字母 */
		String letterString = genRandomString(letter, 1);
		charMap.put(letterString, letterString);
		/* 3.生成一个随机特殊字符 */
		String characterString = genRandomString(character, 1);
		charMap.put(characterString, characterString);
		/* 4.生成剩余的3到5位任意字符（数字、字母、特殊字符均可） */
		String dlcString = genRandomString(dlc, ((int) Math.round(Math.random() * 10)) % 3 + 3);
		charMap.put(dlcString, dlcString);
		/* 5.将前4步骤中生成的四组密码段按任意顺序排列:利用Map取数据的特性达到随机排序的目的 */
		for (Map.Entry<String, String> entry : charMap.entrySet()) {
			password.append(entry.getValue());
		}
		return password.toString();
	}

	private static String genRandomString(String sourceString, int length) {
		StringBuffer retStr = new StringBuffer();
		for (int index = 0; index < length; index++) {
			retStr.append(sourceString.charAt(Math.min(sourceString.length() - 1,
					(int) Math.round(Math.random() * sourceString.length()))));
		}
		return retStr.toString();
	}

	public static String MD5password(String password) {
		String encryptedPassword = HashEncryptor
				.encrypt(password, HashEncryptor.HASH_ALGORITHM_MD5);
		return encryptedPassword;
	}

	public static void main(String[] args) {
//		for (int i = 0; i < 5; i++) {
//			System.out.println(generatePassword());
//		}
		String str = "admin";
		MessageDigest md5= null;
		try {
			String password = MD5password(str);
			System.out.println(password);
			password = new Sha256Hash(password, "1234567890").toHex();
			System.out.println(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
