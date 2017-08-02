package com.luke.enums;

/**
 * Created by yangf on 2017/8/1/0001.
 */
public enum GSType {

    LX('('), RX(')'), LD('{'), RD('}'), FH(';'), WD('a'), SY('\"'), LXG('/'), RXG('\n'), LGX('*');

    private Character value;

    GSType(Character value) {
        this.value = value;
    }

    public static GSType has(Character value) {
        GSType[] gSTypes = GSType.values();
        for (GSType gSType : gSTypes) {
            if (gSType.value.equals(value)) {
                return gSType;
            }
        }
        return GSType.WD;
    }

    public static boolean isLineEnd(GSType type) {
        return type == GSType.LD || type == GSType.RD || type == GSType.LXG;
    }

}
