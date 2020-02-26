package r.a.h.grant.gbt33190.ocr;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import r.a.h.grant.gbt33190.ofdx.CustomTags;
import r.a.h.grant.gbt33190.ofdx.InvoiceInfo.InvoiceInfoBuilder;
import r.a.h.grant.gbt33190.utils.XmlUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * grant
 * 16/2/2020 9:29 AM
 * 描述：
 */
public class PagesHelper {
    public void load(String realPath, InvoiceInfoBuilder invoiceInfoBuilder, CustomTags tags) {
        Element root = XmlUtil.load(realPath).getRootElement();
        Element layer = root.element("Content").element("Layer");

        CustomTags.Tag tag = tags.getFphm();
        invoiceInfoBuilder.fphm(getText(getById(layer, tag)));

        tag = tags.getFpdm();
        invoiceInfoBuilder.fpdm(getText(getById(layer, tag)));

        tag = tags.getJym();
        invoiceInfoBuilder.jym(getText(getById(layer, tag)));

        List<CustomTags.Tag> tagss = tags.getMw();
        List<String> mw = new ArrayList<>(tagss.size());
        for (int i=0;i<tagss.size();i++){
            tag = tagss.get(i);
            mw.add(getText(getById(layer, tag)));
        }
        invoiceInfoBuilder.mw(mw);

        tag = tags.getKprq();
        invoiceInfoBuilder.kprq(getText(getById(layer, tag)));
        //购方
        tag = tags.getGfmc();
        invoiceInfoBuilder.gfmc(getText(getById(layer, tag)));
        tag = tags.getGfdzdh();
        invoiceInfoBuilder.gfdzdh(getText(getById(layer, tag)));
        tag = tags.getGfsh();
        invoiceInfoBuilder.gfsh(getText(getById(layer, tag)));
        tag = tags.getGfyhdh();
        invoiceInfoBuilder.gfyhdh(getText(getById(layer, tag)));
        //销方
        tag = tags.getXfmc();
        invoiceInfoBuilder.xfmc(getText(getById(layer, tag)));
        tag = tags.getXfsh();
        invoiceInfoBuilder.xfsh(getText(getById(layer, tag)));
        tag = tags.getXfdzdh();
        invoiceInfoBuilder.xfdzdh(getText(getById(layer, tag)));
        tag = tags.getXfyhzh();
        invoiceInfoBuilder.xfyhzh(getText(getById(layer, tag)));

        //备注
        tag = tags.getBz();
        invoiceInfoBuilder.bz(getText(getById(layer, tag)));

        tag = tags.getHjje();
        invoiceInfoBuilder.hjje(getText(getById(layer, tag)));
        tag = tags.getHjse();
        invoiceInfoBuilder.hjse(getText(getById(layer, tag)));
        tag = tags.getJshj();
        invoiceInfoBuilder.jshj(getText(getById(layer, tag)));
    }

    protected String getText(Element element){
        if (element == null || element.element("TextCode") == null) return null;
        return element.element("TextCode").getStringValue();
    }

    protected Element getById(Element layer, CustomTags.Tag tag){
        if (tag == null) return new DOMElement("EMP");
        Element nl = layer.elementByID(tag.getId());
        return nl == null ? new DOMElement("EMP") : nl;
    }
}
