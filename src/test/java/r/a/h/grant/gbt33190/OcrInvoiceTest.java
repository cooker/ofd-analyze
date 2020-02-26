package r.a.h.grant.gbt33190;

import org.junit.Test;
import r.a.h.grant.gbt33190.ocr.OcrInvoiceHelper;
import r.a.h.grant.gbt33190.ofdx.InvoiceInfo;

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
}
