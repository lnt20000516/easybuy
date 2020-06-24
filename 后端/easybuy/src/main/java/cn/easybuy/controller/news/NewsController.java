package cn.easybuy.controller.news;

import cn.easybuy.pojo.News;
import cn.easybuy.service.news.NewsService;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Resource
    private NewsService newsService;

    @RequestMapping("/add")
    public String add(@RequestParam("title") String title,
                      @RequestParam("content") String content,
                      @RequestParam("createTime") String createTime) {
        return newsService.add(new News(title, content, createTime));
    }

    @RequestMapping("/update")
    public String update(@RequestParam("id") Integer id,
                      @RequestParam(value = "title", required = false) String title,
                      @RequestParam(value = "content", required = false) String content,
                      @RequestParam(value = "createTime", required = false) String createTime) {
        return newsService.update(new News(id, title, content, createTime));
    }

    @RequestMapping("/deleteById")
    public String deleteById(@RequestParam("id") Integer id) {
        return newsService.deleteById(id);
    }

    @RequestMapping("/queryNewsList")
    public String queryProductCategoryList(@RequestParam("from") Integer from,
                                           @RequestParam("pageSize") Integer pageSize) {
        return newsService.queryNewsList(from, pageSize);
    }

    @RequestMapping("/count")
    public String count() {
        return newsService.count();
    }
}
