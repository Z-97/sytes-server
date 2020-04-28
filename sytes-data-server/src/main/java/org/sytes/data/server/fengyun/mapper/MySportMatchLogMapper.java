package org.sytes.data.server.fengyun.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.sytes.data.server.fengyun.dom.MySportMatchLog;

public interface MySportMatchLogMapper {
   
	/**
	 * 插入一条记录
	 * @param record
	 * @return
	 */
    int insert(MySportMatchLog record);

   
    /**
     * 根据id查找
     * @param id
     * @return
     */
    MySportMatchLog selectById(Integer id);
    

    /**
     * 查找用户当前赛事
     * @param userId
     * @return
     */
    List<MySportMatchLog> selectByUser(long userId);
    /**
     * 查找用户发起的赛事
     * @param userId
     * @return
     */
    List<MySportMatchLog> selectPromoterByUser(@Param("userId")long userId,@Param("isPromoter")boolean isPromoter);
    List<MySportMatchLog> selectAll();

    /**
     * 更新状态
     * @param record
     * @return
     */
    int updateById(MySportMatchLog record);
}