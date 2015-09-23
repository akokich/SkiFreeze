package com.akx2.skifreeze;

public enum SlopeSpeed {
    STOP (0),
    SLOWEST(250),
    SLOW(325),
    FAST(500),
    FASTEST(700)
    ;

    public final int code;

    SlopeSpeed (int slopeCode)
    {
        code = slopeCode;
    }
}
