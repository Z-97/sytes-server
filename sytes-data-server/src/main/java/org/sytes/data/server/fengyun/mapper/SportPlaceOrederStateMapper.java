package org.sytes.data.server.fengyun.mapper;

import java.util.List;
import org.sytes.data.server.fengyun.dom.SportPlaceOrederState;

public interface SportPlaceOrederStateMapper {
    /**
     * 新增加场地预约订单
     * @param record
     * @return
     */
	int insert(SportPlaceOrederState record);
	/**
	 * 根据id查找订单
	 * @param id
	 * @return
	 */
	SportPlaceOrederState selectById( Integer id);
	/**
	 * 根据场地id查找
	 * @param placeId
	 * @return
	 */
	List<SportPlaceOrederState> selectByPlaceId(Integer placeId);
	/**
	 * 根据所在城市查找
	 * @param placeCity
	 * @return
	 */
	List<SportPlaceOrederState> selectByPlaceCity( String placeCity);
	/**
	 * 查找全部
	 * @return
	 */
	List<SportPlaceOrederState> selectAll();
	/**
	 * 查找正在报名的赛事
	 * @return
	 */
	List<SportPlaceOrederState> selectOpenSportPlaceOreder();
	/**
	 * 更新
	 * @param record
	 * @return
	 */
	int updateById(SportPlaceOrederState record);
}