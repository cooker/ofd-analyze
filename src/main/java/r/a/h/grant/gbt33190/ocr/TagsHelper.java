package r.a.h.grant.gbt33190.ocr;

import org.dom4j.Element;
import r.a.h.grant.gbt33190.ofdx.CustomTags;
import r.a.h.grant.gbt33190.ofdx.CustomTags.CustomTagsBuilder;
import r.a.h.grant.gbt33190.ofdx.CustomTags.Tag;
import r.a.h.grant.gbt33190.utils.BaseUtil;
import r.a.h.grant.gbt33190.utils.XmlUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * grant
 * 16/2/2020 9:30 AM
 * 描述：
 */
public class TagsHelper {
    public CustomTags load(String basePath, String fileName) {
        Element root = XmlUtil.load(fileName).getRootElement();
        Element customTag = root.element("CustomTag");
        Element fileLoc = customTag.element("FileLoc");
        String tag = fileLoc.getStringValue();
        String tagPath = "";
        if (BaseUtil.isAbsolutePath(tag)) {
            tagPath = basePath + tag;
        }else {
            tagPath = BaseUtil.getPath2Str(fileName) + "/" + tag;
        }
        return loadTagXml(tagPath);
    }

    protected CustomTags loadTagXml(String tagPath){
        CustomTagsBuilder customTagsBuilder = CustomTags.builder();
        Element root = XmlUtil.load(tagPath).getRootElement();
        Element common = root.element("COMMON_ZZS");
        List<Element> zzs = common.elements();
        for (Element e : zzs) {
            switch (e.getName()){
                case "HEAD":
                    ckHEAD(customTagsBuilder, e);
                    break;
                case "BUYER":
                    ckBUYER(customTagsBuilder, e);
                    break;
                case "MXHJ":
                    ckMXHJ(customTagsBuilder, e);
                    break;
                case "JSHJ":
                    ckJSHJ(customTagsBuilder, e);
                    break;
                case "SALE":
                    ckSALE(customTagsBuilder, e);
                    break;
            }
        }
        return customTagsBuilder.build();
    }

    private void ckMXHJ(CustomTagsBuilder customTagsBuilder, Element e) {
        Element tp = e.element("HJJE").element("ObjectRef");
        customTagsBuilder.hjje(objectRef(tp));
        tp = e.element("HJSE").element("ObjectRef");
        customTagsBuilder.hjse(objectRef(tp));
    }

    private void ckJSHJ(CustomTagsBuilder customTagsBuilder, Element e) {
        Element tp = e.element("JSHJ_XX").element("ObjectRef");
        customTagsBuilder.jshj(objectRef(tp));
    }

    private void ckSALE(CustomTagsBuilder customTagsBuilder, Element e) {
        e = e.element("XSF");
        Element tp = e.element("XSF_MC").element("ObjectRef");
        customTagsBuilder.xfmc(objectRef(tp));
        tp = e.element("XSF_NSRSBH").element("ObjectRef");
        customTagsBuilder.xfsh(objectRef(tp));
        tp = e.element("XSF_DZDH").element("ObjectRef");
        customTagsBuilder.xfdzdh(objectRef(tp));
        tp = e.element("XSF_YHZH").element("ObjectRef");
        customTagsBuilder.xfyhzh(objectRef(tp));

    }

    private void ckHEAD(CustomTagsBuilder customTagsBuilder, Element e){
        Element tp = e.element("FP_BT").element("ObjectRef");
        customTagsBuilder.bz(objectRef(tp));
        tp = e.element("FP_DM").element("ObjectRef");
        customTagsBuilder.fpdm(objectRef(tp));
        tp = e.element("FP_HM").element("ObjectRef");
        customTagsBuilder.fphm(objectRef(tp));
        tp = e.element("KPRQ").element("ObjectRef");
        customTagsBuilder.kprq(objectRef(tp));
        tp = e.element("JYM").element("ObjectRef");
        customTagsBuilder.jym(objectRef(tp));
    }
    
    private void ckBUYER(CustomTagsBuilder customTagsBuilder, Element e) {
        Element root = e.element("GMF");
        Element tp = root.element("MC").element("ObjectRef");
        customTagsBuilder.gfmc(objectRef(tp));
        tp = root.element("NSRSBH").element("ObjectRef");
        if (tp != null) customTagsBuilder.gfsh(objectRef(tp));
        tp = root.element("DZDH").element("ObjectRef");
        if (tp != null) customTagsBuilder.gfdzdh(objectRef(tp));
        tp = root.element("YHZH").element("ObjectRef");
        if (tp != null) customTagsBuilder.gfyhdh(objectRef(tp));
        List<Element> tps = root.element("MW").elements("ObjectRef");
        List<Tag> tags = tps.stream().map(s->objectRef(s))
                .sorted((a, b) -> Integer.valueOf(a.getId()) - Integer.valueOf(b.getId()))
                .collect(Collectors.toList());
        customTagsBuilder.mw(
            tags
        );
    }


    protected Tag objectRef(Element tp){
        return new Tag(tp.getStringValue(), tp.attributeValue("PageRef"));
    }
}
