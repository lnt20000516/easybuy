package cn.easybuy.dao.messageBook;

import cn.easybuy.pojo.MessageBook;
import cn.easybuy.provider.MessageBookProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 留言类
 * 实现用户留言的类
 * @author 杜煜兴
 * @time 2020年6月21
 */
@Mapper
@Component
public interface MessageBookMapper {

    /**
     * 插入一条数据
     * @param messageBook 留言中的详细信息
     * @return
     */
    @InsertProvider(value = MessageBookProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "messageBook.id")
    int insert(@Param("messageBook") MessageBook messageBook);

    /**
     * 根据id查询留言信息
     * @param id id
     * @return
     */
    @Select("select * from easybuy_message_book where id=#{id}")
    List<MessageBook> queryMessageBookById(@Param("id") Integer id);

    /**
     * 查询所有留言的信息
     * @return
     */
    @Select("select * from easybuy_message_book")
    List<MessageBook> queryMessageBookAll();
}
