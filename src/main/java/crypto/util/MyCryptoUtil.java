package crypto.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyCryptoUtil {
    private final String masterkey = "DC2D84F72A7DED45";

    private final SeedUtil seedUtil;
    private final CryptoDbUtil cryptoDbUtil;

    public Map<String, Object> cryptoPhone(String phoneNum) throws Exception {
        String encPhone = seedUtil.encrypt(masterkey, phoneNum);
        String decPhone = seedUtil.decrypt(masterkey, encPhone);

        Map<String, Object> res = new HashMap<>();
        res.put("enc_phone", encPhone);
        res.put("dec_phone", decPhone);

        return res;
    }

    public String toSEEDCBCEncrypt(String plainText) throws Exception{
        return seedUtil.encrypt(masterkey, plainText);
    }

    public String toExternal(String input) throws Exception {
        String plainText = cryptoDbUtil.decrypt(input);
        log.info("toExternal plain text : {}", plainText);
        return seedUtil.encrypt(masterkey, plainText);
    }

    public String toDb(String input) throws Exception {
        String plainText = seedUtil.decrypt(masterkey, input);
        log.info("toDb plain text : {}", plainText);
        return cryptoDbUtil.encrypt(plainText);
    }
}
