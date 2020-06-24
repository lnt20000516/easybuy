package cn.easybuy.provider;

import cn.easybuy.pojo.MessageBook;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class MessageBookProvider {
    public String insert(MessageBook messageBook) {
        SQL sql = new SQL().INSERT_INTO("easybuy_message_book");

        // 必传参数
        sql.VALUES("userId", "#{messageBook.userId}");
        sql.VALUES("title", "#{messageBook.title}");
        sql.VALUES("name", "#{messageBook.name}");
        sql.VALUES("phone", "#{messageBook.phone}");
        sql.VALUES("content", "#{messageBook.content}");

        // 可选参数
        if(StringUtils.hasText(messageBook.getEmail())) {
            sql.VALUES("email", "#{messageBook.email}");
        }
        if(StringUtils.hasText(messageBook.getAddress())) {
            sql.VALUES("address", "#{messageBook.address}");
        }
        return sql.toString();
    }
}
