package r.a.h.grant.gbt33190.ofdx;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * grant
 * 2/5/2020 4:07 下午
 * 描述：
 */
public class Boundary {
    int x;
    int y;
    int w;
    int h;
    private List<String> texts = new ArrayList<>();

    public Boundary(String boundary) {
        String[] bs = boundary.split(" ");
        this.x = Double.valueOf(bs[0]).intValue();
        this.y = Double.valueOf(bs[1]).intValue();
        this.w = Double.valueOf(bs[2]).intValue();
        this.h = Double.valueOf(bs[3]).intValue();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public void addText(String text){
        texts.add(text);
    }

    public List<String> getTexts() {
        return texts;
    }

    public String getAllText(){
        StringBuilder sb = new StringBuilder();
        for (String txt : texts){
            sb.append(txt);
        }
        return sb.toString();
    }

    public static Boundary build(String boundary){
        return new Boundary(boundary);
    }

    public static Boundary build(Element textObject){
        if ("TextObject".equals(textObject.getName())){
            Boundary boundary = new Boundary(textObject.attributeValue("Boundary"));
            List<Object> textObjEls = textObject.elements("TextCode");
            for (Object ob : textObjEls){
                Element el = (Element)ob;
                boundary.addText(el.getText());

            }
            return boundary;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Boundary{" +
                "x=" + x +
                ", y=" + y +
                ", texts=" + texts +
                '}';
    }
}
