package org.sytes.common.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 单服id生成，如果多服需要增加server bits
 * 41 bits: Timestamp (毫秒级) 
 * 22 bits: sequence number		每毫秒:4194304
 * @author wang
 */
public class IdGenerator {

	private static final AtomicLong ID_SEED = new AtomicLong(System.currentTimeMillis() << 22);
	
	private IdGenerator() {
	}

	/**
	 * 获取id
	 * @return
	 */
	public static final long nextId() {
		return ID_SEED.incrementAndGet();
	}
}
