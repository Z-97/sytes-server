package org.sytes.data.server.fengyun.dom;

import java.util.Date;

/**
 * 支付记录
 * @author wang
 *
 */
public class MyPayLog {
  
    private Integer id;

    
    private Long userId;

   
    private Integer gold;

    
    private Integer actionType;

    
    private String actionDescribe;

    /**
     *支付时间
     */
    private Date payTime;
    /**
     * 场地
     */
    private String placeName;
    /**
     * 地址
     */
    private String placeAddress;
    public Integer getId() {
        return id;
    }

   
    public void setId(Integer id) {
        this.id = id;
    }

   
    public Long getUserId() {
        return userId;
    }

    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
    public Integer getGold() {
        return gold;
    }

   
    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getActionType() {
        return actionType;
    }

   
    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

   
    public String getActionDescribe() {
        return actionDescribe;
    }

    public void setActionDescribe(String actionDescribe) {
        this.actionDescribe = actionDescribe;
    }


	public Date getPayTime() {
		return payTime;
	}


	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}


	public String getPlaceName() {
		return placeName;
	}


	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}


	public String getPlaceAddress() {
		return placeAddress;
	}


	public void setPlaceAddress(String placeAddress) {
		this.placeAddress = placeAddress;
	}
}