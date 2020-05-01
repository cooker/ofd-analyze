package r.a.h.grant.gbt33190.ofdx;

import com.rits.cloning.Cloner;
import lombok.*;
import org.dom4j.Document;
import org.dom4j.Element;
import r.a.h.grant.gbt33190.utils.BaseUtil;
import r.a.h.grant.gbt33190.utils.XmlUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * grant
 * 30/4/2020 12:45 下午
 * 描述：
 */
@Getter
@Setter
public class OFDDocument {
    private String documentPath;
    private OFDPageArea pageArea;
    private List<OFDPage> pages;

    public OFDDocument() {
    }

    public OFDDocument(String documentPath, OFDPageArea pageArea, List<OFDPage> pages) {
        this.documentPath = documentPath;
        this.pageArea = pageArea;
        this.pages = pages;
    }

    public OFDSinglePage getIndexPage(int index) {
        OFDSinglePage ofdSinglePage = new OFDSinglePage();
        Cloner cloner = Cloner.standard();
        ofdSinglePage.setPageArea(cloner.deepClone(pageArea));
        ofdSinglePage.setPage(cloner.deepClone(pages.get(index)));
        return ofdSinglePage;
    }

    public static OFDDocument xml(String path){
        OFDDocument document = new OFDDocument();
        document.setDocumentPath(BaseUtil.getParent(path));
        Document doc = XmlUtil.load(path);
        Element element = doc.getRootElement();
        String physicalBox = element.element("CommonData").element("PageArea").element("PhysicalBox").getText();
        String[] pb = physicalBox.split(" ");
        OFDPageArea pageArea = new OFDPageArea(Integer.valueOf(pb[2]), Integer.valueOf(pb[3]));
        document.setPageArea(pageArea);
        Element pages = element.element("Pages");
        List<Object> datas = pages.elements();
        List<OFDPage> ofdPages = new ArrayList<>(datas.size());
        String val = null, val2 = null;
        for (Object da : datas){
            Element el = (Element) da;
            val = el.attributeValue("ID");
            val2 = el.attributeValue("BaseLoc");
            ofdPages.add(new OFDPage(val, val2, document));
        }
        document.setPages(ofdPages);
        return document;
    }


}
