package r.a.h.grant.gbt33190.ofdx;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * grant
 * 16/2/2020 9:52 AM
 * 描述：关键字标记
 */
@Data
@Builder
public class CustomTags {


    private Tag fphm;
    //发票代码
    private Tag fpdm;
    //校验码
    private Tag jym;
    //密文
    private List<Tag> mw;
    private Tag kprq;

    //购销信息
    private Tag xfsh;
    private Tag xfmc;
    private Tag xfdzdh;
    private Tag xfyhzh;

    private Tag gfsh;
    private Tag gfmc;
    private Tag gfdzdh;
    private Tag gfyhdh;

    //备注
    private Tag bz;

    //税额
    private Tag hjse;
    //价税合计
    private Tag jshj;
    //合计金额
    private Tag hjje;



    @Data
    @AllArgsConstructor
    public static class Tag{
        String id;
        String PageRef;
    }

}
