package crypto.controller;

import crypto.util.MyCryptoUtil;
import crypto.util.SeedUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class MainController {

  private final MyCryptoUtil myCryptoUtil;

  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public String index() {
    log.info("--------come");
    boolean isShow = true;
    if(isShow){
      return "success";
    }else {
      throw new RuntimeException("exception...");
    }
  }

  @GetMapping("/encrypt-phone")
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Object> encryptPhone(@RequestParam String phoneNum) throws Exception {
    return myCryptoUtil.cryptoPhone(phoneNum);
  }

  @GetMapping("/encrypt-text")
  @ResponseStatus(HttpStatus.OK)
  public String encryptText(@RequestParam String plainText) throws Exception {
    return myCryptoUtil.toSEEDCBCEncrypt(plainText);
  }

  @GetMapping("/to-external")
  @ResponseStatus(HttpStatus.OK)
  public String toExternal(@RequestParam String encText) throws Exception {
    return myCryptoUtil.toExternal(encText);
  }

  @GetMapping("/to-DB")
  @ResponseStatus(HttpStatus.OK)
  public String toDb(@RequestParam String encText) throws Exception {
    return myCryptoUtil.toDb(encText);
  }


}
