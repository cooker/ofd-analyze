package r.a.h.grant.gbt33190.ocr;

import r.a.h.grant.gbt33190.ofdx.OFDSinglePage;

/**
 * grant
 * 4/6/2020 3:43 下午
 * 描述：坐标帮助
 */
public class OcrPosFlag {
    OFDSinglePage singlePage;
    //210 140
    int w = 210;
    int h = 140;
    public OcrPosFlag(OFDSinglePage singlePage) {
        this.singlePage = singlePage;
    }


    public int centerPosX(){
        return (int)(w * 0.5);
    }

    public int purchasePosY(){
        return (int)(h * 0.20510);
    }

    //密码区
    public int mwPosY(){
        return (int)(h * 0.214555);
    }

    public int mwPosFootY(){
        return (int)(h * 0.3695652);
    }

    public int mwPosX(){
        return (int)(w * 0.59357);
    }

    //销方信息 + 备注

    public int bzPosY(){
        return (int)(h * 0.74291);
    }

    public int bzPosX(){
        return (int)(w * 0.568998);
    }

    // 开票人，收款人，复核人

    public int personalPosY(){
        return (int)(h * 0.888);
    }

    public int kprPosX(){
        return (int) (w * 0.504);
    }

    public int fhrPosX(){
        return (int)(w * 0.302);
    }

    public int skrPosX(){
        return (int)(w * 0.035);
    }
}
