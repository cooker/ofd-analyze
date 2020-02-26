package r.a.h.grant.gbt33190.ocr;

import org.dom4j.Document;
import org.dom4j.Element;
import r.a.h.grant.gbt33190.utils.XmlUtil;

public interface OFDHelper {
    default Document getOFDRoot(String fileName){
        Document ofdDoc = XmlUtil.load(fileName + "/OFD.xml");
        return ofdDoc;
    }

    default Element getOFDBody(Document ofdDoc){
        Element docBody = ofdDoc.getRootElement().element("DocBody");
        return docBody;
    }
}
