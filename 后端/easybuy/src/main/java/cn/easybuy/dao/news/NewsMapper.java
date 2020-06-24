package cn.easybuy.dao.news;

import cn.easybuy.pojo.News;
import cn.easybuy.provider.NewsProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * 新闻类
 * 实现新闻的增删改查的类
 * @author 高骏
 * @time 2020年6月21
 */
@Mapper
@Component
public interface NewsMapper {
    /**
     * 添加新闻
     */
    @Insert("insert into easybuy_news(title, content, createTime) values(#{news.title}, #{news.content}, #{news.createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "news.id")
    int add(@Param("news") News news);

    /**
     * 根据标题查看该标题是否已经被使用
     * @param title 标题
     * @return
     */
    @Select("select count(1) from easybuy_news where title=#{title}")
    int checkTitle(@Param("title") String title);

    /**
     * 修改信息
     */
    @UpdateProvider(value = NewsProvider.class, method = "update")
    int update(@Param("news") News news);

    /**
     * 根据id删除新闻
     */
    @Delete("delete from easybuy_news where id=#{id}")
    int deleteById(Integer id);

    /**
     * 查询新闻列表
     */
    @Select("select * from easybuy_news limit #{from}, #{pageSize}")
    List<News> queryNewsList(@Param("from") Integer from,
                             @Param("pageSize") Integer pageSize);

    /**
     * 根据id查询新闻
     * @param id id
     * @return
     */
    @Select("select * from easybuy_news where id=#{id}")
    News queryNewsById(@Param("id") Integer id);

    /**
     * 查询新闻列表
     */
    @Select("select * from easybuy_news")
    List<News> queryAll();

    /**
     * 查询新闻的数目
     */
    @Select("select count(1) from easybuy_news")
    Integer count();
}
