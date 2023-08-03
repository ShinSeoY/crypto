package crypto.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class CryptoDbUtil {
    // zmap처럼 hex를 직접 만들지 말고
    // https://veneas.tistory.com/entry/JAVA-Hex-%EC%9D%B8%EC%BD%94%EB%94%A9-%EB%94%94%EC%BD%94%EB%94%A9-%EB%B0%A9%EB%B2%95-16-%EC%A7%84%EC%88%98
    // 여기서 처럼 내장함수 사용해서 해보기
    final private String cryptoDbIv = "나의 IV";

    final private String cryptoDbKey = "나의 key";

    public String encrypt(String msg) throws Exception {
        try {
            return encryptAes(msg, cryptoDbKey, cryptoDbIv);
        } catch (Exception e) {
            log.error("CRYPTO_ENCRYPT_ERROR");
            throw new Exception(e);
        }
    }

    public String decrypt(String msg) throws Exception {
        try {
            return decryptAes(msg, cryptoDbKey, cryptoDbIv);
        } catch (Exception e) {
            log.error("CRYPTO_DECRYPT_ERROR");
            throw new Exception(e);
        }
    }

    private String encryptAes(String plainText, String inputKey, String inputIV) throws Exception {
        byte[] IV = DatatypeConverter.parseHexBinary(inputIV);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(inputKey.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));

        byte[] result = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printHexBinary(result);
    }

    private String decryptAes(String text, String inputKey, String inputIV) throws Exception {
        byte[] cipherText = DatatypeConverter.parseHexBinary(text);
        byte[] IV = DatatypeConverter.parseHexBinary(inputIV);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(inputKey.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV));

        return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
    }

    // HEX
//    private String byteToHex(byte num) {
//        char[] hexDigits = new char[2];
//        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
//        hexDigits[1] = Character.forDigit((num & 0xF), 16);
//        return new String(hexDigits);
//    }
//
//    private byte hexToByte(String hexString) {
//        int firstDigit = toDigit(hexString.charAt(0));
//        int secondDigit = toDigit(hexString.charAt(1));
//        return (byte) ((firstDigit << 4) + secondDigit);
//    }
//
//    private int toDigit(char hexChar) {
//        int digit = Character.digit(hexChar, 16);
//        if (digit == -1) {
//            throw new IllegalArgumentException(
//                    "Invalid Hexadecimal Character: " + hexChar);
//        }
//        return digit;
//    }
//
//    private String encodeHexString(byte[] byteArray) {
//        StringBuilder hexStringBuffer = new StringBuilder();
//        for (byte b : byteArray) {
//            hexStringBuffer.append(byteToHex(b));
//        }
//        return hexStringBuffer.toString();
//    }
//
//    private byte[] decodeHexString(String hexString) {
//        if (hexString.length() % 2 == 1) {
//            throw new IllegalArgumentException(
//                    "Invalid hexadecimal String supplied.");
//        }
//
//        byte[] bytes = new byte[hexString.length() / 2];
//        for (int i = 0; i < hexString.length(); i += 2) {
//            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
//        }
//        return bytes;
//    }


}
