package cn.easybuy.service.messageBook;

import cn.easybuy.dao.messageBook.MessageBookMapper;
import cn.easybuy.pojo.MessageBook;
import cn.easybuy.utils.MyJsonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageBookService {
    @Resource
    MessageBookMapper messageBookMapper;

    public String insert(MessageBook messageBook) {
        int i = messageBookMapper.insert(messageBook);
        List<MessageBook> list = new ArrayList<>();
        list.add(messageBook);

        return new MyJsonUtils<MessageBook>()
                .put("code", 200)
                .put("message", "受影响条数" + i)
                .addArray("data", list)
                .toString();
    }

    public String queryMessageBookById(Integer id) {
        List<MessageBook> list;

        if(id == null) {
            list = messageBookMapper.queryMessageBookAll();
        } else {
            list = messageBookMapper.queryMessageBookById(id);
        }

        return new MyJsonUtils<MessageBook>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("length", list.size())
                .addArray("data", list)
                .toString();
    }
}
