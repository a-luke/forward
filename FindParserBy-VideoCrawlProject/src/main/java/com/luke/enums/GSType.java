package com.luke.enums;

/**
 * Created by yangf on 2017/8/1/0001.
 */
public enum GSType {
    LX('('), RX(')'), LD('{'), RD('}'), LJK('<'), RJK('>'), DH(','), FH(';'), WD('a'), SY('\"'), LXG('/'), RXG('\n'), LGX('*'), DIAN('.');

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

    /**
     * 判断是否是单独的一行，花括号和注释都当作单独的行处理
     *
     * @param type
     * @return
     */
    public static boolean isAloneLine(GSType type) {
        return type == GSType.LD || type == GSType.RD || type == GSType.LXG;
    }

    public static boolean isChunkGist(GSType type) {
        return type == GSType.LD || type == GSType.RD;
    }

    /**
     * 是否是小括号
     *
     * @return
     */
    public static boolean isX(GSType type) {
        return type == GSType.LX || type == GSType.RX;
    }

    public String value() {
        return String.valueOf(this.value);
    }
}
