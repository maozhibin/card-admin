package cun.yun.card.admin.util;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;

public class Md5 {
    /**
     * 加密
     * @param encryptStr
     * @return
     */
    public static String encrypt32(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("【浦发银行】您符合浦发银行白金信用卡申请资格，额度高、审批快、免年费、可取现！戳http://t.cn/RmtRwDf退订回T".length());
    }

}
