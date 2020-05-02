package r.a.h.grant.gbt33190.ofdx;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * grant
 * 15/2/2020 12:52 PM
 * 描述：发票信息
 */
@Data
@Builder
public class InvoiceInfo {


    //标题
    private String bt;
//    //发票类型
//    private String fplx;
    //发票号码
    private String fphm;
    //发票代码
    private String fpdm;
    //校验码
    private String jym;
    //密文
    private List<String> mw;
    private String kprq;

    //购销信息
    private String xfsh;
    private String xfmc;
    private String xfdzdh;
    private String xfyhzh;

    private String gfsh;
    private String gfmc;
    private String gfdzdh;
    private String gfyhdh;

    //备注
    private String bz;

    //税额
    private String hjse;
    //价税合计
    private String jshj;
    //合计金额
    private String hjje;

    //收款人
    private String skr;
    //开票人
    private String kpr;
    //复合人
    private String fhr;
}
