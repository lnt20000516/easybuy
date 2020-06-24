package cn.easybuy.controller.news;

import cn.easybuy.pojo.News;
import cn.easybuy.service.news.NewsService;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
/**
 * 新闻类
 * 实现新闻的增删改查的类
 * @author 高骏
 * @time 2020年6月21
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Resource
    private NewsService newsService;

    /**
     * 添加新闻
     */
    @RequestMapping("/add")
    public String add(@RequestParam("title") String title,
                      @RequestParam("content") String content,
                      @RequestParam("createTime") String createTime) {
        return newsService.add(new News(title, content, createTime));
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update")
    public String update(@RequestParam("id") Integer id,
                      @RequestParam(value = "title", required = false) String title,
                      @RequestParam(value = "content", required = false) String content,
                      @RequestParam(value = "createTime", required = false) String createTime) {
        return newsService.update(new News(id, title, content, createTime));
    }

    /**
     * 根据id删除新闻
     */
    @RequestMapping("/deleteById")
    public String deleteById(@RequestParam("id") Integer id) {
        return newsService.deleteById(id);
    }

    /**
     * 查询新闻列表
     */
    @RequestMapping("/queryNewsList")
    public String queryProductCategoryList(@RequestParam("from") Integer from,
                                           @RequestParam("pageSize") Integer pageSize) {
        return newsService.queryNewsList(from, pageSize);
    }

    /**
     * 查询新闻的数目
     */
    @RequestMapping("/count")
    public String count() {
        return newsService.count();
    }
}
