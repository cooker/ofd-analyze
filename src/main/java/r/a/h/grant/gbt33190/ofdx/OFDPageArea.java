package r.a.h.grant.gbt33190.ofdx;

import lombok.Getter;
import lombok.Setter;

/**
 * grant
 * 30/4/2020 12:46 下午
 * 描述：
 */
@Getter
@Setter
public class OFDPageArea {
    byte[][] ares;
    int w;
    int h;

    public OFDPageArea(int w, int h) {
        this.ares = new byte[w][h];
        this.w = w;
        this.h = h;
    }

    @Override
    public String toString() {
        return "OFDPageArea{" +
                "w=" + w +
                ", h=" + h +
                '}';
    }
}
