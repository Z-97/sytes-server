package org.sytes.data.server.fengyun.dom; 
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * 场地预约订单
 * @author wang
 *
 */
public class SportPlaceOrederState {

	private Integer id;

	private Integer placeId;

	private String placeName;

	/**
	 * 申请状态0未处理1已经批准2完成
	 */
	private Integer state;

	private Date startTime;

	private Date endTime;

	private Integer sportNum;

	private Long promoterId;

	private String promoterName;

	private String placeCity;
	private String placeAdress;
	/**
	 * 报名成员
	 */
	private CopyOnWriteArrayList<SportMan> sportMember = new CopyOnWriteArrayList<>();
	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getSportNum() {
		return sportNum;
	}

	public void setSportNum(Integer sportNum) {
		this.sportNum = sportNum;
	}

	public Long getPromoterId() {
		return promoterId;
	}

	public void setPromoterId(Long promoterId) {
		this.promoterId = promoterId;
	}

	public String getPromoterName() {
		return promoterName;
	}

	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}

	public String getPlaceCity() {
		return placeCity;
	}

	public void setPlaceCity(String placeCity) {
		this.placeCity = placeCity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}

	public String getPlaceAdress() {
		return placeAdress;
	}

	public void setPlaceAdress(String placeAdress) {
		this.placeAdress = placeAdress;
	}

	public CopyOnWriteArrayList<SportMan> getSportMember() {
		return sportMember;
	}

	public void setSportMember(CopyOnWriteArrayList<SportMan> sportMember) {
		this.sportMember = sportMember;
	}

	

}