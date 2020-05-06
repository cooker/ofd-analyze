package r.a.h.grant.gbt33190.ocr;

import r.a.h.grant.gbt33190.ofdx.*;
import r.a.h.grant.gbt33190.ofdx.InvoiceInfo.InvoiceInfoBuilder;
import r.a.h.grant.gbt33190.utils.BaseUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        OFDSinglePage singlePage = ofdDocument.getIndexPage(0);
        singlePage.ready();
        Map<Integer, List<Boundary>> treeMap = singlePage.getPoints();
        List<Integer> dataYs = treeMap.keySet().stream().collect(Collectors.toList());
        List<Integer> y5 = dataYs.stream().limit(5).collect(Collectors.toList());
        //判断前5位
        builder.fpdm(treeMap.get(y5.get(0)).get(0).getTexts().get(0))
                .fphm(treeMap.get(y5.get(1)).get(0).getTexts().get(0))
                .kprq(treeMap.get(y5.get(2)).get(0).getTexts().get(0));
        List<Boundary> ty4 = treeMap.get(y5.get(3));
        List<Boundary> ty5 = treeMap.get(y5.get(4));
        //校验码
        if (ty4.size() > 1) {
            builder.jym(ty4.get(1).getAllText());
        }else {
            Boundary b4 = ty4.get(0);
            Boundary b5 = ty5.get(0);
            if (b4.getX() > b5.getX()){
                builder.jym(b4.getAllText());
            }else {
                builder.jym(b5.getAllText());
            }
        }
        List<Integer> temp = null;
        Boundary op = null;

        //购方 + 密码区
        Boundary b5 = ty5.get(0);
        if (!BaseUtil.isChinese(b5.getAllText().charAt(0))){
            //5 6 7 8
            Integer index = dataYs.get(5);
            ty5 = treeMap.get(index);
            builder.gfmc(ty5.get(0).getAllText());
            if (ty5.size() > 1){
                builder.mw(BaseUtil.newList(ty5.get(1).getAllText()));
            }
            index = dataYs.get(6);
            builder.gfsh(treeMap.get(index).get(0).getAllText());
            index = dataYs.get(7);
            builder.gfdzdh(treeMap.get(index).get(0).getAllText());
            index = dataYs.get(8);
            builder.gfyhdh(treeMap.get(index).get(0).getAllText());
        }
        //金额
        temp = dataYs.stream().skip(treeMap.size() - 7).limit(2).collect(Collectors.toList());
        ty4 = treeMap.get(temp.get(0)).stream().filter(
                b->!b.getAllText().contains("*") && !b.getAllText().contains("¥")
        )
        .sorted((a, b)-> (int) (Double.valueOf(a.getAllText())- Double.valueOf(b.getAllText())))
        .collect(Collectors.toList());
        builder.hjje(ty4.get(0).getAllText())
                .hjse(ty4.get(1).getAllText())
                .jshj(
                        treeMap.get(temp.get(1)).size() > 2 ?
                        treeMap.get(temp.get(1)).get(2).getAllText()
                        : treeMap.get(temp.get(1)).get(1).getAllText().replaceAll("¥", "")
                        );

        //销方 + 备注
        temp = dataYs.stream().skip(treeMap.size() - 5).limit(4).collect(Collectors.toList());
        ty4 = treeMap.get(temp.get(0)).stream().sorted(
                (a,b)->a.getX()-b.getX()
        ).collect(Collectors.toList());
        if (ty4.size() > 1){
            builder.bz(ty4.get(1).getAllText());
        }
        op = ty4.get(0);
        builder.xfmc(op.getAllText());

        ty4 = treeMap.get(temp.get(1)).stream().sorted(
                (a,b)->a.getX()-b.getX()
        ).collect(Collectors.toList());;
        op = ty4.get(0);
        builder.xfsh(op.getAllText());

        ty4 = treeMap.get(temp.get(2)).stream().sorted(
                (a,b)->a.getX()-b.getX()
        ).collect(Collectors.toList());
        op = ty4.get(0);
        builder.xfdzdh(op.getAllText());

        ty4 = treeMap.get(temp.get(3)).stream().sorted(
                (a,b)->a.getX()-b.getX()
        ).collect(Collectors.toList());
        op = ty4.get(0);
        builder.xfyhzh(op.getAllText());

        //开票人 对账人 收款人
        Integer lastY = dataYs.get(dataYs.size() - 1);
        List<Boundary> lastBoundary = treeMap.get(lastY);
        Collections.sort(lastBoundary, (a, b)-> a.getX() - b.getX());
        builder.skr(lastBoundary.get(0).getAllText())
               .fhr(lastBoundary.get(1).getAllText())
               .kpr(lastBoundary.get(2).getAllText());

    }
}
