package cn.easybuy.service.news;

import cn.easybuy.dao.news.NewsMapper;
import cn.easybuy.pojo.News;
import cn.easybuy.pojo.Product;
import cn.easybuy.pojo.ProductCategory;
import cn.easybuy.pojo.User;
import cn.easybuy.utils.MyJsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NewsService {
    @Resource
    NewsMapper newsMapper;

    public String add(News news) {
        if(!StringUtils.hasText(news.getTitle()) || !StringUtils.hasText(news.getContent()) || !StringUtils.hasText(news.getCreateTime())) {
            return new MyJsonUtils<News>().put("code", 500).put("message", "有空参数").toString();
        }
        if(checkTitle(news.getTitle())) {
            return new MyJsonUtils<News>().put("code", 201).put("message", "标题已被使用").toString();
        }
        String regex = "^(((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))|"
                + "((\\d{2}(([02468][048])|([13579][26]))[\\/]((((0?[13578])|(1[02]))[\\/]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\/]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\/]((((0?[13578])|(1[02]))[\\/]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\/]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/]((0?[1-9])|(1[0-9])|(2[0-8])))))))";
        if(!news.getCreateTime().matches(regex)) {
            return new MyJsonUtils<News>().put("code", 202).put("message", "请输入正确的时间格式(yyyy-MM-dd)").toString();
        }
        int i = newsMapper.add(news);
        if(i > 0) {
            List<News> list = new ArrayList<>();
            list.add(news);
            return new MyJsonUtils<News>().put("code", 200).put("message", "SUCCESS").addArray("data", list).toString();
        }
        return new MyJsonUtils<News>().put("code", 500).put("message", "未知错误").toString();
    }

    public boolean checkTitle(String title) {
        return newsMapper.checkTitle(title) > 0;
    }

    public String update(News news) {
        News t = newsMapper.queryNewsById(news.getId());
        //如果传了title
        if(t != null && StringUtils.hasText(news.getTitle())) {
            // 并且title不为未发生改变，且它被使用过了
            if(!news.getTitle().equals(t.getTitle()) && checkTitle(news.getTitle())) {
                return new MyJsonUtils<News>()
                        .put("code", 500)
                        .put("message", "标题已被占用")
                        .toString();
            }
        }

        // 如果输入了时间，检测是否合法
        if(StringUtils.hasText(news.getCreateTime())) {
            String regex = "^(((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))|"
                    + "((\\d{2}(([02468][048])|([13579][26]))[\\/]((((0?[13578])|(1[02]))[\\/]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\/]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\/]((((0?[13578])|(1[02]))[\\/]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\/]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/]((0?[1-9])|(1[0-9])|(2[0-8])))))))";
            if(!news.getCreateTime().matches(regex)) {
                return new MyJsonUtils<News>().put("code", 202).put("message", "请输入正确的时间格式(yyyy-MM-dd)").toString();
            }
        }


        return new MyJsonUtils<News>()
                .put("code", 200)
                .put("message", "受影响条数" + newsMapper.update(news))
                .toString();
    }

    public String deleteById(Integer id) {
        int i = newsMapper.deleteById(id);
        MyJsonUtils<News> json = new MyJsonUtils<>();
        String message;
        int code;

        if(i > 0) {
            code = 200;
            message = "删除成功，受影响条数为" + i;
        } else if(i == 0){
            code = 201;
            message = "id不存在";
        } else {
            code = 500;
            message = "未知错误";
        }
        json.put("code", code);
        json.put("message", message);
        return json.toString();
    }

    public String queryNewsList(Integer from, Integer pageSize) {
        List<News> list;
        if(from != null || pageSize != null) {
            from = from == null ? 0 : from;
            pageSize = pageSize == null ? 0 : pageSize;
            list = newsMapper.queryNewsList(from, pageSize);
        } else {
            list = newsMapper.queryAll();
        }
        return new MyJsonUtils<News>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("length", list.size())
                .put("sum", newsMapper.count())
                .addArray("data", list)
                .toString();
    }

    public String count() {
        return new MyJsonUtils<Product>()
                .put("code", 200)
                .put("message", "SUCCESS")
                .put("length", newsMapper.count())
                .toString();
    }
}
