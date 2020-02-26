package r.a.h.grant.gbt33190.ocr;

import org.dom4j.Document;
import org.dom4j.Element;
import r.a.h.grant.gbt33190.ofdx.CustomTags;
import r.a.h.grant.gbt33190.ofdx.InvoiceInfo;
import r.a.h.grant.gbt33190.ofdx.InvoiceInfo.InvoiceInfoBuilder;
import r.a.h.grant.gbt33190.ofdx.Pages;
import r.a.h.grant.gbt33190.utils.BaseUtil;
import r.a.h.grant.gbt33190.utils.XmlUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * grant
 * 15/2/2020 11:22 AM
 * 描述：发票识别助手
 */
public class OcrInvoiceHelper implements OFDHelper {

    public InvoiceInfo ocr(String fileName){
        InvoiceInfoBuilder invoiceInfoBuilder = InvoiceInfo.builder();
        Document ofdDoc = getOFDRoot(fileName);
        Element docBody = getOFDBody(ofdDoc);
        Element docRoot = docBody.element("DocRoot");
        lkDoc(fileName, docRoot, invoiceInfoBuilder);
        return invoiceInfoBuilder.build();
    }

    protected void lkDoc(String fileName, Element docRoot, InvoiceInfoBuilder invoiceInfoBuilder) {
        String docPath = fileName + "/" + docRoot.getStringValue();
        Element root = XmlUtil.load(docPath).getRootElement();
        Element commonData = root.element("CommonData");
        Element templatePage = commonData.element("TemplatePage");
        invoiceInfoBuilder.bt(templatePage.attributeValue("Name"));
        //获取内容页
        Element pages = root.element("Pages");
        List<Element> lsPage = pages.elements("Page");
        List<Pages> lsPageObj = lsPage.stream().map(s->new Pages(s.attributeValue("ID"), s.attributeValue("BaseLoc"))).collect(Collectors.toList());
        //获取模板
        Element customTags = root.element("CustomTags");

        String tagPath = "";
        if (BaseUtil.isAbsolutePath(customTags.getStringValue())){
            tagPath = fileName + "/" + customTags.getStringValue();
        }else {
            tagPath = BaseUtil.getPath2Str(docPath) + "/" + customTags.getStringValue();
        }

        CustomTags tags = new TagsHelper().load(fileName, tagPath);
        //遍历页面
        lkPage(fileName, docPath, invoiceInfoBuilder, lsPageObj, tags);
    }

    protected void lkPage(String basePath, String docPath, InvoiceInfoBuilder invoiceInfoBuilder, List<Pages> lsPageObj, CustomTags tags) {
        String realPath = "";
        for (Pages page : lsPageObj){
            if (BaseUtil.isAbsolutePath(page.getLoc())){
                realPath = basePath + "/" + page.getLoc();
            }else {
                realPath = BaseUtil.getPath2Str(docPath) + "/" + page.getLoc();
            }

            new PagesHelper().load(realPath, invoiceInfoBuilder, tags);
        }

    }
}
