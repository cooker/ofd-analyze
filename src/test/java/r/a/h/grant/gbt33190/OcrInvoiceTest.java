package r.a.h.grant.gbt33190;

import org.junit.Test;
import r.a.h.grant.gbt33190.ocr.OcrInvoiceHelper;
import r.a.h.grant.gbt33190.ofdx.InvoiceInfo;
import r.a.h.grant.gbt33190.ofdx.OFD;
import r.a.h.grant.gbt33190.ofdx.OFDDocument;

/**
 * Unit test for simple App.
 */
public class OcrInvoiceTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void ocr()
    {
        InvoiceInfo invoiceInfo = new OcrInvoiceHelper().ocr("/Users/grant/Documents/fp");
        System.out.println(invoiceInfo);
    }

    @Test
    public void ofd(){
        String str = "/Users/grant/Pictures/fp";
        OFD ofd = OFD.xml(str);
        System.out.println(ofd.getRealDocPath());
    }


    @Test
    public void ofdDoc(){
        OFDDocument ofdDocument = OFDDocument.xml("/Users/grant/Pictures/fp/Doc_0/Document.xml");
        System.out.println(ofdDocument);
    }

    @Test
    public void xx(){
        byte[][] xx = new byte[5][5];
        xx[1][1] = 1;
        byte[][] aa = new byte[5][5];
        System.arraycopy(xx, 0, aa, 0, 5);
        System.out.println(aa[1][1]);
//        System.

    }
}
