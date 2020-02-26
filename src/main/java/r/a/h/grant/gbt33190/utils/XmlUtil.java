package r.a.h.grant.gbt33190.utils;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * grant
 * 16/2/2020 8:49 AM
 * 描述：
 */
public class XmlUtil {

    public static Document load(String filename) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(filename)); // 读取XML文件,获得document对象
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }
}
