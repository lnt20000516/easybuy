package cn.easybuy.controller.messageBook;

import cn.easybuy.pojo.MessageBook;
import cn.easybuy.service.messageBook.MessageBookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/messageBook")
public class MessageBookController {
    @Resource
    MessageBookService messageBookService;
    /**
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

    @RequestMapping("/queryMessageBookById")
    public String queryMessageBookById(@RequestParam(value = "id", required = false) Integer id) {
        return messageBookService.queryMessageBookById(id);
    }
}
