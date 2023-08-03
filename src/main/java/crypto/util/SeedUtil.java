package crypto.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@Component
public class SeedUtil {
    public String encrypt(String masterKey, String msg) throws Exception {
        try {
            byte[] sha256 = getSHA256(masterKey);
            byte[] key = Arrays.copyOfRange(sha256, 0, 16);
            byte[] iv = Arrays.copyOfRange(sha256, 16, 32);

            byte[] data = KISA_SEED_CBC.SEED_CBC_Encrypt(key, iv, msg.getBytes(), 0, msg.getBytes().length);
            return Base64.getEncoder().encodeToString(data);
        } catch (Exception e) {
            log.error("SEED_ENCRYPT_ERROR");
            throw new Exception(e);
        }
    }

    public String decrypt(String masterKey, String base64Str) throws Exception {
        try {
            byte[] sha256 = getSHA256(masterKey);
            byte[] key = Arrays.copyOfRange(sha256, 0, 16);
            byte[] iv = Arrays.copyOfRange(sha256, 16, 32);

            byte[] decode = Base64.getDecoder().decode(base64Str);
            byte[] dec = KISA_SEED_CBC.SEED_CBC_Decrypt(key, iv, decode, 0, decode.length);
            return new String(dec);
        } catch (Exception e) {
            log.error("SEED_DECRYPT_ERROR");
            throw new Exception(e);
        }
    }

    private byte[] getSHA256(String msg) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.reset();
        return md.digest(msg.getBytes());
    }
}
