package org.sytes.data.server.fengyun.mapper;

import org.sytes.data.server.fengyun.dom.PlatformInfo;

public interface PlatformInfoMapper {
 
    int insert(PlatformInfo record);
    int updateById(PlatformInfo record);
    PlatformInfo selectPlatformInfo();
}