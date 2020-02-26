package r.a.h.grant.gbt33190.utils;

import java.io.File;

/**
 * grant
 * 16/2/2020 10:06 AM
 * 描述：
 */
public class BaseUtil {
    public static boolean isAbsolutePath(String path) {
        if (path.startsWith("/") || path.indexOf(":") > 0) {
            return true;
        }
        return false;
    }

    public static String getPath2Str(String fileName) {
        File file = new File(fileName);
        return file.getParent();
    }
}
