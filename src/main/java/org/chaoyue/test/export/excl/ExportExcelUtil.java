package org.chaoyue.test.export.excl;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * Created by chaoyue on 2017/4/12.
 */
public class ExportExcelUtil {

    private static final String TEMPLATE_PATH = "excl/"; //模板路径
    /**
     * 导出Excel
     * @param data 数据 （建议小于2000条数据）
     * @param filem 输出文件名
     * @param templateName 模板名称
     * @param response
     */
    public void exportExcel(Map data, String filem, String templateName, HttpServletResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(TEMPLATE_PATH).append(templateName);
        ServletOutputStream outputStream = response.getOutputStream();
        XLSTransformer transformer = new XLSTransformer();
        Resource resource = new ClassPathResource(sb.toString());
        String filename = filem+"_"+new Date()+"_"+new Random().nextInt()+".xlsx";
        try {
            Workbook workbook = transformer.transformXLS(resource.getInputStream(), data);
            setXlsHeader(response, filename);
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (InvalidFormatException e) {
            e.printStackTrace();
        }finally{
            try{
                outputStream.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void setXlsHeader(HttpServletResponse response,String fileName){
        response.reset();
        response.setHeader("Expires", "0");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Cache-Control", "public");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try {
            String name = new String((fileName).getBytes(), "utf-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + name);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
