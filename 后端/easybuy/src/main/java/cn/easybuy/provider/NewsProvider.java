package cn.easybuy.provider;

import cn.easybuy.pojo.News;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class NewsProvider {
    public String update(News news) {
        SQL sql = new SQL().UPDATE("easybuy_news").WHERE("id=#{news.id}");

        // 标题
        if(StringUtils.hasText(news.getTitle())) {
            sql.SET("title=#{news.title}");
        }
        // 内容
        if(StringUtils.hasText(news.getContent())) {
            sql.SET("content=#{news.content}");
        }
        // 创建时间
        if(StringUtils.hasText(news.getCreateTime())) {
            sql.SET("createTime=#{news.createTime}");
        }

        return sql.toString();
    }
}
