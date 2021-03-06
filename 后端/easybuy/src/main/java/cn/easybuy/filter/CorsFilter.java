package cn.easybuy.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 过滤器
 * @author 刘楠婷
 * @time 2020年6月23
 */
@Component
public class CorsFilter implements Filter {

    /**
     * 运行跨域访问
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        //设置允许跨域访问
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        System.out.println("*********************************过滤器被使用=>" + getTime(System.currentTimeMillis()) + "**************************");
        chain.doFilter(req, res);
    }

    /**
     * 获得当前时间
     * @param time 时间戳
     * @return
     */
    public String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return format.format(new Date(time));
    }
}
