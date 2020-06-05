package r.a.h.grant.gbt33190;

import com.google.gson.Gson;
import com.rits.cloning.Cloner;
import org.junit.Test;
import r.a.h.grant.gbt33190.ocr.OcrInvoiceHelper;
import r.a.h.grant.gbt33190.ofdx.*;

import java.util.stream.Collectors;

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
//        /Users/grant/Downloads/pdf/百望云增值税发票
//        /Users/grant/Downloads/comm-2020
        InvoiceInfo invoiceInfo = new OcrInvoiceHelper().ocr("/Users/grant/Downloads/pdf/百望云增值税发票");
        Gson gson = new Gson();
        System.out.println(gson.toJson(invoiceInfo));
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
        OFDSinglePage sp = ofdDocument.getIndexPage(0);
        System.out.println(sp.getPage().getRealPagePath());

        sp.ready();
        System.out.println(sp.getPageArea());
        System.out.println(sp.getPoints());

        System.out.println(sp.getPoints().keySet().stream().limit(5).collect(Collectors.toList()));
    }

    @Test
    public void xx(){
        OFDPageArea ofdPageArea = new OFDPageArea(2,2);

        ofdPageArea.print();

        Cloner cloner = Cloner.standard();
        OFDPageArea of = cloner.deepClone(ofdPageArea);
        ofdPageArea.addPoint(1,1);

        of.print();

        ofdPageArea.print();

    }
}
