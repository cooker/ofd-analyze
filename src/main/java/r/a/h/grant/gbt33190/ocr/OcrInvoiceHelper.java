package r.a.h.grant.gbt33190.ocr;

import r.a.h.grant.gbt33190.ofdx.*;
import r.a.h.grant.gbt33190.ofdx.InvoiceInfo.InvoiceInfoBuilder;

import java.util.ArrayList;
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
    OcrPosFlag posFlag = null;
    OFDSinglePage singlePage = null;
    OFDDocument ofdDocument = null;

    private boolean isUsed = false;
    public InvoiceInfo ocr(String path){
        if (isUsed) throw new RuntimeException("Can't use please re-instantiate");
        InvoiceInfoBuilder invoiceInfoBuilder = InvoiceInfo.builder();
        isUsed = true;
        pageParsing(invoiceInfoBuilder, path);
        return invoiceInfoBuilder.build();
    }

    private void pageParsing(InvoiceInfoBuilder builder, String path){
        OFD ofd = OFD.xml(path);
        ofdDocument = OFDDocument.xml(ofd.getRealDocPath());
        singlePage = ofdDocument.getIndexPage(0);
        posFlag = new OcrPosFlag(singlePage);
        final OcrPosFlag fposFlag = posFlag;
        singlePage.ready();
        Map<Integer, List<Boundary>> treeMap = singlePage.getPoints();
        List<Integer> dataYs = treeMap.keySet().stream().collect(Collectors.toList());


        List<Integer> y5 = dataYs.stream().filter((a)->posFlag.purchasePosY() > a).collect(Collectors.toList());
        List<Boundary> tDatas = new ArrayList<>();
        List<Boundary> bDatas = new ArrayList<>();
        for (Integer y : y5){
            treeMap.get(y).stream().filter(x->fposFlag.centerPosX() < x.getX()).forEach(
                (x)->{
                    tDatas.add(x);
                }
            );
        }
        bDatas = tDatas.stream().sorted(
                (a,b)->a.getY() - b.getY()
        ).collect(Collectors.toList());
        //判断前5位
        builder.fpdm(bDatas.get(0).getAllText())
                .fphm(bDatas.get(1).getAllText())
                .kprq(bDatas.get(2).getAllText())
                .jym(bDatas.get(3).getAllText());
        //二维码扫描
        //TODO
        //购方
        this.ocrPurchase(builder, posFlag, treeMap);
        //密码区
        this.ocrMw(builder, posFlag, treeMap);
        //销方
        this.ocrSeller(builder, posFlag, treeMap);
        //备注
        this.ocrRemark(builder, posFlag, treeMap);
        //开票人 对账人 收款人
        this.ocrPersonal(builder, posFlag, treeMap);

    }

    protected void ocrPurchase(InvoiceInfoBuilder builder, OcrPosFlag posFlag, Map<Integer, List<Boundary>> treeMap){
        List<Integer> datasY = treeMap.keySet().stream().filter(
                (a)-> posFlag.purchasePosY() <= a && posFlag.mwPosFootY() > a

        ).sorted().collect(Collectors.toList());

        List<Boundary> purchasers = new ArrayList<>();

        for (Integer y:datasY){
            for (Boundary b : treeMap.get(y)){
                if (posFlag.mwPosX() > b.getX()){
                    purchasers.add(b);
                    break;
                }
            }
        }

        int size = purchasers.size();
        builder.gfmc(purchasers.get(0).getAllText());
        if (size > 1){
            builder.gfsh(purchasers.get(1).getAllText());
        }

        if (size > 2){
            builder.gfdzdh(purchasers.get(2).getAllText());
        }

        if (size > 3){
            builder.gfyhdh(purchasers.get(3).getAllText());
        }

    }

    protected void ocrMw(InvoiceInfoBuilder builder, OcrPosFlag posFlag, Map<Integer, List<Boundary>> treeMap){
        List<Integer> datasY = treeMap.keySet().stream().filter(
                (a)-> posFlag.mwPosY() <= a

        ).sorted(
                (a,b)->b-a
        ).collect(Collectors.toList());
        for (Integer y:datasY){
            for (Boundary b : treeMap.get(y)){
                if (posFlag.mwPosX() <= b.getX()){
                    builder.mw(b.getTexts());
                    break;
                }
            }
        }
    }

    protected void ocrSeller(InvoiceInfoBuilder builder, OcrPosFlag posFlag, Map<Integer, List<Boundary>> treeMap){
        List<Boundary> xfBoundary = findSellerAera(posFlag, treeMap);
        xfBoundary =  xfBoundary.stream().filter((a)->posFlag.bzPosX() > a.getX()).collect(Collectors.toList());

        xfBoundary = xfBoundary.stream().sorted((a,b)->{
            if (a.getY() != b.getY()) return a.getY() - b.getY();
            return a.getX() - b.getX();
        }).collect(Collectors.toList());

        int i=1;
        int y = 0;
        StringBuilder sb = new StringBuilder();
        int size = xfBoundary.size();
        Boundary indexB = null;
        for (int index = 0; index < size; index++){
            indexB = xfBoundary.get(index);
            if (y == 0){
                sb.append(indexB.getAllText());
                y = indexB.getY();
            }else if (y == indexB.getY()){
                sb.append(indexB.getAllText());
            }else {
                fillSellerByIndex(i, y, sb, builder);
                sb = new StringBuilder(indexB.getAllText());
                i++;
                y = indexB.getY();
            }
        }
        fillSellerByIndex(i, y, sb, builder);
    }

    private void fillSellerByIndex(int index, int y, StringBuilder sb, InvoiceInfoBuilder builder) {
        switch (index){
            case 1:
                builder.xfmc(sb.toString());
                break;
            case 2:
                builder.xfsh(sb.toString());
                break;
            case 3:
                builder.xfdzdh(sb.toString());
                break;
            case 4:
                builder.xfyhzh(sb.toString());
                break;
            default: break;
        }
    }

    protected void ocrRemark(InvoiceInfoBuilder builder, OcrPosFlag posFlag, Map<Integer, List<Boundary>> treeMap){
        List<Boundary> bzBoundary = findSellerAera(posFlag, treeMap);
        bzBoundary =  bzBoundary.stream().filter((a)->posFlag.bzPosX() <= a.getX()).collect(Collectors.toList());
        if (!bzBoundary.isEmpty()){
            builder.bz(bzBoundary.get(0).getAllText());
        }

    }

    private List<Boundary> findSellerAera(OcrPosFlag posFlag, Map<Integer, List<Boundary>> treeMap) {
        List<Integer> datasY = treeMap.keySet().stream().filter(
                (a)-> posFlag.personalPosY() > a &&
                        posFlag.bzPosY() <= a

        ).collect(Collectors.toList());

        List<Boundary> bzBoundary = new ArrayList<>();
        for (Integer y : datasY) {
            bzBoundary.addAll(treeMap.get(y));
        }
        return bzBoundary;
    }

    protected void ocrPersonal(InvoiceInfoBuilder builder, OcrPosFlag posFlag, Map<Integer, List<Boundary>> treeMap) {
        List<Integer> datasY = treeMap.keySet().stream().filter(
                (a)-> posFlag.personalPosY() <= a

        ).collect(Collectors.toList());
        List<Boundary> lastBoundary = new ArrayList<>();
        for (Integer y : datasY) {
            lastBoundary.addAll(treeMap.get(y));
        }
        Collections.sort(lastBoundary, (a, b)-> a.getX() - b.getX());
        personal(builder, lastBoundary, posFlag);
    }

    protected void personal(InvoiceInfoBuilder builder, List<Boundary> lastBoundary, OcrPosFlag posFlag){
        for (Boundary boundary : lastBoundary) {
            if (boundary.getX() >= posFlag.kprPosX()) {
                builder.kpr(boundary.getAllText());
            }else if (boundary.getX() >= posFlag.fhrPosX()){
                builder.fhr(boundary.getAllText());
            }else {
                builder.skr(boundary.getAllText());
            }

        }
    }
}
