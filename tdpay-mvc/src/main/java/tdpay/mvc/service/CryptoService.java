package tdpay.mvc.service;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.isols.common.utils.StringUtils;
import tdpay.mvc.service.shared.SystemPropertyService;

/**
 *
 */
@Service
public class CryptoService {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CryptoService.class);

    @Autowired
    protected SystemPropertyService systemPropertyService;

    //暗号鍵
    protected byte[] encryptKey = {
        (byte)0x83, (byte)0x5C, (byte)0xB2, (byte)0xE3, (byte)0x8E, (byte)0x41, (byte)0xFB, (byte)0xB9,
        (byte)0x93, (byte)0x49, (byte)0x46, (byte)0x4C, (byte)0x21, (byte)0x31, (byte)0x20, (byte)0x6A,
        (byte)0x53, (byte)0x70, (byte)0x6A, (byte)0xD3, (byte)0x75, (byte)0x5F, (byte)0x79, (byte)0x55,
        (byte)0x50, (byte)0xB0, (byte)0x3D, (byte)0x24, (byte)0xA2, (byte)0x3C, (byte)0x37, (byte)0xD4,
    };

    //初期化ベクトル（ブロック長と同じ）
    protected byte[] encryptIv = {
        (byte)0x80, (byte)0xF4, (byte)0x54, (byte)0x6F, (byte)0x62, (byte)0xF4, (byte)0xED, (byte)0x48,
        (byte)0xF2, (byte)0xDC, (byte)0x09, (byte)0x27, (byte)0x02, (byte)0xF7, (byte)0x55, (byte)0x70,
    };

    public String sha256Encode(String target, String shopKey) {
        byte[] targetByte = target.getBytes(StringUtils.UTF_8);
        byte[] salt = getSalt(shopKey.getBytes(StringUtils.UTF_8));
        byte[] bin = new byte[targetByte.length + salt.length];
        System.arraycopy(targetByte, 0, bin, 0, targetByte.length);
        System.arraycopy(salt, 0, bin, salt.length, salt.length);

        byte[] sha256 = DigestUtils.sha256(bin);
        char[] hexChars = Hex.encodeHex(sha256);
        String hex = new String(hexChars);
        
        String hash = systemPropertyService.getSixHash(hex);

        return hash;
    }

    public static byte[] getSalt(byte[] salt) {
        Random random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    public String encrypt(String target) {
        return StringUtils.encrypt(target, encryptKey, encryptIv);
    }

    public String decrypt(String target) {
        return StringUtils.decrypt(target, encryptKey, encryptIv);
    }

    public String getKey() {
        return new String(encryptKey);
    }

    public String getIv() {
        return new String(encryptIv);
    }
}
