package com.xyu.mapper;
import com.xyu.po.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {
    public void creatMessage(@Param("id") int id, @Param("message") String message,@Param("answer") String answer);
    List<Message> queryData();
}
