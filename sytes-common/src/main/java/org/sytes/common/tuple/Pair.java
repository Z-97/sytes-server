package org.sytes.common.tuple;

/**
 * 二元祖封装
 *@author wang
 *@
 */
public class Pair<A, B> {
    public final A v1;
    public final B v2;

    public Pair(A v1, B v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
}
