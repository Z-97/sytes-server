package org.sytes.data.server.fengyun.dom;

import java.util.concurrent.atomic.AtomicInteger;

public class PlatformInfo {

	private Integer platformId;

    /**
     *收入
     */
    private AtomicInteger income;

    /**
     * 提现
     */
    private AtomicInteger cashOut;
    public int cashOut() {
    	return cashOut.get();
    }
    public int income() {
    	return income.get();
    }
    public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public AtomicInteger getIncome() {
		return income;
	}

	public void setIncome(AtomicInteger income) {
		this.income = income;
	}

	public AtomicInteger getCashOut() {
		return cashOut;
	}

	public void setCashOut(AtomicInteger cashOut) {
		this.cashOut = cashOut;
	}


}