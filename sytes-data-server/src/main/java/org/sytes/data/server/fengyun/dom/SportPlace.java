package org.sytes.data.server.fengyun.dom;
/**
 * 场地
 * @author wang
 *
 */
public class SportPlace {
    
    private Integer placeId;

    /**
     *所有者
     */
    private Long placeOwner;

    /**
     *场地名称
     *
     */
    private String placeName;

    /**
     *
     *场地地址
     */
    private String placeAdress;

    /**
     *场地类型1足球2篮球3其他
     */
    private Integer placeType;

    /**
     *联系人
     */
    private String linkman;

    /**
     *联系人电话
     */
    private String linkmanPhone;

    /**
     *场地最大容纳人数
     */
    private Integer placeMax;

    /**
     *场地最小预约成功人数
     */
    private Integer placeMin;

    /**
     *包场价格
     */
    private Integer wholePrice;

    /**
     *单人价格
     */
    private Integer singlePrice;

    /**
     *预约发起人价格
     */
    private Integer promoterPrice;

    /**
     *提前取消时间（小时）
     */
    private Integer cancelHours;

    /**
     *平台抽成比例百分比
     */
    private Integer platformRate;

    /**
     *开放预约开始时间
     */
    private String startTime;

    /**
     *开放预约结束时间 (15:34:32):SimpleDateFormat stringToDateFormat = new SimpleDateFormat("HH:mm:ss");
     */
    private String endTime;

    /**
     *当前订单与预订
     */
    private String curOreder;

    /**
     * 是否开放
     */
    private boolean placeState;
    /**
     * 所在城市
     */
    private String placeCity;

    /**
     * 所在省份
     */
	private String placeProvince;
	
    public Integer getPlaceId() {
        return placeId;
    }

   
    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

   
    public Long getPlaceOwner() {
        return placeOwner;
    }

    public void setPlaceOwner(Long placeOwner) {
        this.placeOwner = placeOwner;
    }

    
    public String getPlaceName() {
        return placeName;
    }

   
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

   
    public String getPlaceAdress() {
        return placeAdress;
    }

   
    public void setPlaceAdress(String placeAdress) {
        this.placeAdress = placeAdress;
    }

    public Integer getPlaceType() {
        return placeType;
    }

   
    public void setPlaceType(Integer placeType) {
        this.placeType = placeType;
    }

   
    public String getLinkman() {
        return linkman;
    }

    
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkmanPhone() {
        return linkmanPhone;
    }

   
    public void setLinkmanPhone(String linkmanPhone) {
        this.linkmanPhone = linkmanPhone;
    }

   
    public Integer getPlaceMax() {
        return placeMax;
    }

    
    public void setPlaceMax(Integer placeMax) {
        this.placeMax = placeMax;
    }

   
    public Integer getPlaceMin() {
        return placeMin;
    }

   
    public void setPlaceMin(Integer placeMin) {
        this.placeMin = placeMin;
    }

    
    public Integer getWholePrice() {
        return wholePrice;
    }

    public void setWholePrice(Integer wholePrice) {
        this.wholePrice = wholePrice;
    }

    
    public Integer getSinglePrice() {
        return singlePrice;
    }

   
    public void setSinglePrice(Integer singlePrice) {
        this.singlePrice = singlePrice;
    }

   
    public Integer getPromoterPrice() {
        return promoterPrice;
    }

    
    public void setPromoterPrice(Integer promoterPrice) {
        this.promoterPrice = promoterPrice;
    }

   
    public Integer getCancelHours() {
        return cancelHours;
    }

   
    public void setCancelHours(Integer cancelHours) {
        this.cancelHours = cancelHours;
    }

    
    public Integer getPlatformRate() {
        return platformRate;
    }

   
    public void setPlatformRate(Integer platformRate) {
        this.platformRate = platformRate;
    }

   
    public String getStartTime() {
        return startTime;
    }

   
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    
    public String getCurOreder() {
        return curOreder;
    }

    public void setCurOreder(String curOreder) {
        this.curOreder = curOreder;
    }


	public boolean isPlaceState() {
		return placeState;
	}


	public void setPlaceState(boolean placeState) {
		this.placeState = placeState;
	}


	public String getPlaceCity() {
		return placeCity;
	}


	public void setPlaceCity(String placeCity) {
		this.placeCity = placeCity;
	}


	public String getPlaceProvince() {
		return placeProvince;
	}


	public void setPlaceProvince(String placeProvince) {
		this.placeProvince = placeProvince;
	}
}