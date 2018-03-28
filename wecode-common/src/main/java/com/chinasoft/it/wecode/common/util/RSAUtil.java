package com.chinasoft.it.wecode.common.util;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.springframework.util.Base64Utils;

/**
 * RSA 非对称加密
 * 
 * 外部通过公钥对数据进行加密，系统内部是用私钥对数据进行解密
 * 
 * 问题：这种加密算法只能防止数据被阅读，无法防止伪造数据
 * 
 * 详情请参考：https://blog.csdn.net/qq_32523587/article/details/79092364
 * 
 * @author Administrator
 *
 */
public class RSAUtil {
	// 生成秘钥对
	public static KeyPair getKeyPair() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		return keyPair;
	}

	// 获取公钥(Base64编码)
	public static String getPublicKey(KeyPair keyPair) {
		PublicKey publicKey = keyPair.getPublic();
		byte[] bytes = publicKey.getEncoded();
		return byte2Base64(bytes);
	}

	// 获取私钥(Base64编码)
	public static String getPrivateKey(KeyPair keyPair) {
		PrivateKey privateKey = keyPair.getPrivate();
		byte[] bytes = privateKey.getEncoded();
		return byte2Base64(bytes);
	}

	// 将Base64编码后的公钥转换成PublicKey对象
	public static PublicKey string2PublicKey(String pubStr) throws Exception {
		byte[] keyBytes = base642Byte(pubStr);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	// 将Base64编码后的私钥转换成PrivateKey对象
	public static PrivateKey string2PrivateKey(String priStr) throws Exception {
		byte[] keyBytes = base642Byte(priStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	// 公钥加密
	public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}

	// 私钥解密
	public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}

	// 字节数组转Base64编码
	public static String byte2Base64(byte[] bytes) {
		return Base64Utils.encodeToString(bytes);
	}

	// Base64编码转字节数组
	public static byte[] base642Byte(String base64Key) throws IOException {
		return Base64Utils.decodeFromString(base64Key);
	}

	public static void main(String[] args) throws Exception {
		KeyPair keyPair = getKeyPair();
		String content = "这是一段密码";
		String privateKey = getPrivateKey(keyPair);
		String publicKey = getPublicKey(keyPair);
		System.out.println("待加密内容：" + content + "\r\n\r\n");

		System.out.println("自动生成的公钥（用于加密）");
		System.out.println(publicKey);

		System.out.println("\r\n自动生成的私钥（用于解密）");
		System.out.println(privateKey);

		byte[] publicEncrypt = publicEncrypt(content.getBytes("utf8"), string2PublicKey(publicKey));
		String base64EncodeString = Base64Utils.encodeToString(publicEncrypt);
		System.out.println("\r\n加密后：");
		System.out.println(base64EncodeString);

		byte[] decodeBytes = Base64Utils.decodeFromString(base64EncodeString);
		byte[] privateDecrypt = privateDecrypt(decodeBytes, string2PrivateKey(privateKey));
		System.out.println("\r\n解密后：");
		System.out.println(new String(privateDecrypt, "utf8"));
	}
}