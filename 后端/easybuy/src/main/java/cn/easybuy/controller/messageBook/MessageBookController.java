package cn.easybuy.controller.messageBook;

import cn.easybuy.pojo.MessageBook;
import cn.easybuy.service.messageBook.MessageBookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
/**
 * 留言类
 * 实现用户留言的增删改查类
 * @author 杜煜兴
 * @time 2020年6月21
 */
@RestController
@RequestMapping("/messageBook")
public class MessageBookController {
    @Resource
    MessageBookService messageBookService;

    /**
     * 插入一条数据
     * @return
     */
    @RequestMapping("/insert")
    public String insert(@RequestParam("userId") Integer userId,
                         @RequestParam("title") String title,
                         @RequestParam("name") String name,
                         @RequestParam("phone") String phone,
                         @RequestParam("content") String content,
                         @RequestParam(value = "email", required = false) String email,
                         @RequestParam(value = "address", required = false) String address) {
        return messageBookService.insert(new MessageBook(userId, title, name, phone, email, address, content));
    }

    /**
     * 根据id查询留言信息
     * @param id id
     * @return
     */
    @RequestMapping("/queryMessageBookById")
    public String queryMessageBookById(@RequestParam(value = "id", required = false) Integer id) {
        return messageBookService.queryMessageBookById(id);
    }
}
