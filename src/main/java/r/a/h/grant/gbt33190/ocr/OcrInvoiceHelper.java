package r.a.h.grant.gbt33190.ocr;

import r.a.h.grant.gbt33190.ofdx.InvoiceInfo;
import r.a.h.grant.gbt33190.ofdx.InvoiceInfo.InvoiceInfoBuilder;
import r.a.h.grant.gbt33190.ofdx.OFD;
import r.a.h.grant.gbt33190.ofdx.OFDDocument;

/**
 * grant
 * 15/2/2020 11:22 AM
 * 描述：发票识别助手
 */
public class OcrInvoiceHelper implements OFDHelper {

    public InvoiceInfo ocr(String path){
        InvoiceInfoBuilder invoiceInfoBuilder = InvoiceInfo.builder();
        pageParsing(invoiceInfoBuilder, path);
        return invoiceInfoBuilder.build();
    }

    private void pageParsing(InvoiceInfoBuilder builder, String path){
        OFD ofd = OFD.xml(path);
        OFDDocument ofdDocument = OFDDocument.xml(ofd.getRealDocPath());
    }
}
