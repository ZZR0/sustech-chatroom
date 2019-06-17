package sim.utils;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;

/**
 * Md5utils.is used to encoding the string
 */
public class MD5Utils {

    /**
     * Gets md 5 str.
     *
     * @param strValue the str value
     * @return the md 5 str
     * @throws Exception the exception
     * @Description: MD5 encoding of string
     */
    public static String getMD5Str(String strValue) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
		return newstr;
	}

}
