package r.a.h.grant.gbt33190.ofdx;

import lombok.Getter;
import lombok.Setter;
import r.a.h.grant.gbt33190.utils.BaseUtil;

import java.io.File;

/**
 * grant
 * 30/4/2020 12:47 下午
 * 描述：
 */
@Getter
@Setter
public class OFDPage {
    //<ofd:Page ID="2" BaseLoc="Pages/Page_0/Content.xml"/>
    private String id;
    private String baseLoc;
    private OFDDocument ofdDocument;

    public OFDPage(String id, String baseLoc, OFDDocument ofdDocument) {
        this.id = id;
        this.baseLoc = baseLoc;
        this.ofdDocument = ofdDocument;
    }

    public OFDPage() {
    }

    public String getRealPagePath(){
        if (BaseUtil.isAbsolutePath(baseLoc)){
            return ofdDocument.getDocumentPath() + baseLoc;
        }else {
            return ofdDocument.getDocumentPath() + File.separator + baseLoc;
        }
    }

    @Override
    public String toString() {
        return "OFDPage{" +
                "id='" + id + '\'' +
                ", baseLoc='" + baseLoc + '\'' +
                ", ofdDocument=" + ofdDocument.toString() +
                '}';
    }
}
