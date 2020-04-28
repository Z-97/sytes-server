/*
 * Copyright (c) 2016, Alex. All Rights Reserved.
 */

package org.sytes.common.tuple;

/**
 * 3元祖封装
 *@author wang
 *
 */
public class Triplet<A, B, C> extends Pair<A, B> {
    public final C v3;

    public Triplet(A v1, B v2, C v3) {
        super(v1, v2);
        this.v3 = v3;
    }
}
