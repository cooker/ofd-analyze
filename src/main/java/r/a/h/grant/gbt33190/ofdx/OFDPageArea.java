package r.a.h.grant.gbt33190.ofdx;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * grant
 * 30/4/2020 12:46 下午
 * 描述：
 */
@Getter
@Setter
@ToString(exclude = "ares")
public class OFDPageArea {
    byte[][] ares;
    int w;
    int h;

    public OFDPageArea(int w, int h) {
        this.ares = new byte[w][h];
        this.w = w;
        this.h = h;
    }

    public void print(){
        StringBuilder sb = new StringBuilder();
        for (byte[] ar : ares){
            for (byte a : ar) {
                if(0x0 == a) {
                    sb.append("  O");
                }else {
                    sb.append("  X");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    public void addPoint(int x, int y){
        ares[x-1][y-1] = 0x1;
    }
}
