package utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Security {

    public Security(){

    }

    public String md5(String value) {
        String result = "";
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes("UTF-8"));
            BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);
            if ((result.length() % 2) != 0) {
                result = "0" + result;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            return result;
        }
    }
}
