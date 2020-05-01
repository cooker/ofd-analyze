package r.a.h.grant.gbt33190.ofdx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dom4j.Document;
import org.dom4j.Element;
import r.a.h.grant.gbt33190.utils.BaseUtil;
import r.a.h.grant.gbt33190.utils.XmlUtil;

import java.io.File;

/**
 * grant
 * 30/4/2020 10:48 上午
 * 描述：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OFD {
    /**
     * 最上层 doc
     */
    private String docRoot;

    private String ofdPath;

    public String getRealDocPath(){
        if (BaseUtil.isAbsolutePath(docRoot)){
            return ofdPath + docRoot;
        }else {
            return ofdPath + File.separator + docRoot;
        }
    }

    public static OFD xml(String ofdPath){
        Document doc = XmlUtil.load(ofdPath + File.separator + "OFD.xml");
        Element docBody = doc.getRootElement();
        String val = docBody
                .element("DocBody")
                .element("DocRoot").getText();
        return new OFD(val, ofdPath);
    }

    @Override
    public String toString() {
        return "OFD{" +
                "docRoot='" + docRoot + '\'' +
                ", ofdPath='" + ofdPath + '\'' +
                '}';
    }
}
