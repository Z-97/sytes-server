package org.sytes.data.jr;

import java.util.concurrent.atomic.AtomicInteger;
public class IdGenerator {

	private static final AtomicInteger ID_SEED = new AtomicInteger(0);
	
	private IdGenerator() {
	}

	/**
	 * 获取id
	 * @return
	 */
	public static final int nextId() {
		return ID_SEED.incrementAndGet();
	}
}
