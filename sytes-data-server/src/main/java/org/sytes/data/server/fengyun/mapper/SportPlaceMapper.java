package org.sytes.data.server.fengyun.mapper;

import java.util.List;

import org.sytes.data.server.fengyun.dom.SportPlace;
public interface SportPlaceMapper {
   
	/**
	 * 删除球场
	 * @param placeId
	 * @return
	 */
    int deleteById(Integer placeId);

    /**
     * 新增加球场
     * @param record
     * @return
     */
    int insert(SportPlace record);

    /**
     *查询球场
     */
    SportPlace selectById(Integer placeId);

    /**
     * 全部球场
     */
    List<SportPlace> selectAll();
    
    /**
     * 根据场地名称查找
     */
    SportPlace selectByplaceName(String placeName);
    /**
     * 根据城市名字查找
     */
    List<SportPlace> selectByCity(String cityName);
    /**
     *更新球场信息
     */
    int updateById(SportPlace record);
}