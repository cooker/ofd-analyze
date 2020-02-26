package r.a.h.grant.gbt33190.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * grant
 * 15/2/2020 11:09 AM
 * 描述：
 */
public class ProperteisUtil {

    public static String getValue(String cnf, String key){
        try {
            Properties p = getConf(cnf);
            return p.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Properties getConf(String cnf) throws IOException {
        InputStream in = Thread.currentThread().getClass().getClassLoader().getResourceAsStream(cnf);
        Properties p = new Properties();
        p.load(in);
        return p;
    }
}
