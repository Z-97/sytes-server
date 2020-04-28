package org.sytes.data.server.fengyun.mapper;

import java.util.List;

import org.sytes.data.server.fengyun.dom.MyPayLog;

public interface MyPayLogMapper {
    
  
    int insert(MyPayLog record);

  
    MyPayLog selectById(Integer id);
    List<MyPayLog> selectByUserId(long userId);
   
   
}