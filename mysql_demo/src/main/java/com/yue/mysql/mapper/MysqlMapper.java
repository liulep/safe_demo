package com.yue.mysql.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MysqlMapper {

    @Select("${sql}")
    int isLogin(@Param("sql")String sql);
}
