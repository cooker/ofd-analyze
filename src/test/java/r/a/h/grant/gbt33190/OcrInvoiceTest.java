package r.a.h.grant.gbt33190;

import com.google.gson.Gson;
import com.rits.cloning.Cloner;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import r.a.h.grant.gbt33190.ocr.OcrInvoiceHelper;
import r.a.h.grant.gbt33190.ofdx.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        InvoiceInfo invoiceInfo = new OcrInvoiceHelper().ocrZip("/Users/grant/Documents/个人/031001800311_70831393.ofd");
        Gson gson = new Gson();
        System.out.println(gson.toJson(invoiceInfo));
    }

    @Test
    public void ofd(){
        String str = "/Users/grant/Documents/个人/fp";
        OFD ofd = OFD.xml(str);
        System.out.println(ofd.getRealDocPath());
    }


    @Test
    public void clear() throws IOException {
       FileUtils.forceDeleteOnExit(Paths.get("/Users/grant/Documents/个人/Code/GSysStation/ofd-analyze/tmp/a99a42e2-cf50-4531-8434-cb842d17949f").toFile());
//       System.out.println(is);
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

    @Test
    public void toJpg() throws IOException {
        System.out.println(new OcrInvoiceHelper().ofd2jpg("/Users/grant/Documents/工作资料/票易通/项目/阿里巴巴/OFD 样票/百望云增值税发票.ofd"));

    }
}
