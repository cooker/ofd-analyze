package r.a.h.grant.gbt33190.ofdx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dom4j.Document;
import org.dom4j.Element;
import r.a.h.grant.gbt33190.utils.XmlUtil;

import java.util.*;

/**
 * grant
 * 30/4/2020 1:54 下午
 * 描述：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OFDSinglePage {
    OFDPageArea pageArea;
    OFDPage page;
    //y 轴排序
    Map<Integer, List<Boundary>> points = new TreeMap<>();

    public void addPoint(int x, int y){
        this.addPoint(Integer.valueOf(x), Integer.valueOf(y));
    }

    public void addPoint(int x, int y, Boundary boundary){
        addPoint(x, y);
        if (points.containsKey(Integer.valueOf(y))){
            points.get(Integer.valueOf(y)).add(boundary);
        }else {
            List<Boundary> boundaries =  new ArrayList<>();
            boundaries.add(boundary);
            points.put(Integer.valueOf(y), boundaries);
        }
    }

    public void addPoint(Integer x, Integer y){
        pageArea.getAres()[x-1][y-1] = 1;
    }

    public void ready(){
        String real = page.getRealPagePath();
        Document doc = XmlUtil.load(real);
        Element rootEl = doc.getRootElement();
        Element layerEl = rootEl.element("Content").element("Layer");
        List<Object> textObjectEls = layerEl.elements("TextObject");
        for (Object ob : textObjectEls){
            Element el = (Element) ob;
            Boundary boundary = Boundary.build(el);
            addPoint(boundary.getX(), boundary.getY(), boundary);
        }
    }
}
