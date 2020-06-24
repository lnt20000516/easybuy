package cn.easybuy.dao.messageBook;

import cn.easybuy.pojo.MessageBook;
import cn.easybuy.provider.MessageBookProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MessageBookMapper {

    @InsertProvider(value = MessageBookProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "messageBook.id")
    int insert(@Param("messageBook") MessageBook messageBook);

    @Select("select * from easybuy_message_book where id=#{id}")
    List<MessageBook> queryMessageBookById(@Param("id") Integer id);

    @Select("select * from easybuy_message_book")
    List<MessageBook> queryMessageBookAll();
}
