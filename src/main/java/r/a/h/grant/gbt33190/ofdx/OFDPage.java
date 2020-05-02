package r.a.h.grant.gbt33190.ofdx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import r.a.h.grant.gbt33190.utils.BaseUtil;

import java.io.File;

/**
 * grant
 * 30/4/2020 12:47 下午
 * 描述：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="ofdDocument")
public class OFDPage {
    //<ofd:Page ID="2" BaseLoc="Pages/Page_0/Content.xml"/>
    private String id;
    private String baseLoc;
    private OFDDocument ofdDocument;

    public String getRealPagePath(){
        if (BaseUtil.isAbsolutePath(baseLoc)){
            return ofdDocument.getDocumentPath() + baseLoc;
        }else {
            return ofdDocument.getDocumentPath() + File.separator + baseLoc;
        }
    }

}
