package cn.easybuy.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 图片工具类
 * 实现对图片的一些操作的类
 * @author 杜煜兴
 * @time 2020年6月23
 */
public class ImageUtils {
    private static final String ORIGINAL_PICTURE_PATH = "C:\\Users\\Administrator\\Desktop\\easybuy\\images";
    private static final String DEFAULT_IMAGE = "未设置图片.jpg";

    /**
     * 保存文件
     * @param in 输入流
     * @param userName
     * @param fileName 文件名
     */
    public static void saveImage(InputStream in, String userName, String fileName) {
        BufferedInputStream input = new BufferedInputStream(in);
        String p = ORIGINAL_PICTURE_PATH;
        //如果该文件夹不存在就创建
        createMkdirs(p);

        File file = new File(p + "\\" + fileName);
        try {
            FileOutputStream out = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int n;
            while ((n = input.read(b)) > 0) {
                out.write(b, 0, n);
            }
            out.flush();
            out.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件夹
     * @param p 路径
     */
    private static void createMkdirs(String p) {
        File f = new File(p);
        // 如果文件夹不存在则创建文件夹
        if(!f.exists()) {
            f.mkdir();
        }
    }

    /**
     * 将图片输出流输出
     * @param name
     * @param response
     * @throws Exception
     */
    public static void image(String name, HttpServletResponse response) throws Exception{
        // 图片
        File file = new File(ORIGINAL_PICTURE_PATH + "\\" + (!StringUtils.hasText(name) || "null".equals(name) ? DEFAULT_IMAGE : name));
        if(!file.exists()) {
            file = new File(ORIGINAL_PICTURE_PATH + "\\" + DEFAULT_IMAGE);
        }
        // 穿件输入对象
        FileInputStream fis = new FileInputStream(file);
        // 设置相关格式
        response.setContentType("image/jpeg");
        // 创建输出对象
        OutputStream os = response.getOutputStream();
        // 常规操作
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();
    }
}
