package org.chaoyue.test.export.excl;


import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

public class ExportExcelUtil {
  private final static String  TEMPLATE_PATH = "/templates/exportTemplates/"; //模板路径
 // protected static Logger logger = LoggerFactory.getLogger(ExportExcelUtil.class);
  /**
   * 导出Excel
   * @param data 数据 （建议小于2000条数据）
   * @param fileName 输出文件名
   * @param templateName 模板名称
   * @param response
   */
  public static void exportExcel(Map data, String fileName, String templateName, HttpServletRequest request, HttpServletResponse response) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append(TEMPLATE_PATH).append(templateName).append(".xlsx");
    ServletOutputStream outputStream = response.getOutputStream();
    XLSTransformer transformer = new XLSTransformer();
    Resource resource = new ClassPathResource(sb.toString());
    String filename = fileName+"-.xlsx";
    try {
      Workbook workbook = transformer.transformXLS(resource.getInputStream(), data);
      setXlsHeader(response, request,filename);
      outputStream = response.getOutputStream();
      workbook.write(outputStream);
      outputStream.flush();
      outputStream.close();
    }catch (InvalidFormatException e) {
    //  logger.error("exportExcel InvalidFormatException:[{}]",e);
      e.printStackTrace();
    }finally{
      try{
        outputStream.close();
      }catch (IOException e) {
     //   logger.error("IOException:[{}]",e);
        e.printStackTrace();
      }
    }
  }
  public static void setXlsHeader(HttpServletResponse response,HttpServletRequest request,String fileName){
    response.reset();
    response.setHeader("Expires", "0");
    response.setHeader("Pragma", "public");
    response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
    response.setHeader("Cache-Control", "public");
    response.setContentType("application/vnd.ms-excel;charset=ISO-8859-1");
    try {
      String userAgent = request.getHeader("USER-AGENT");
      String name = encodeFilename(userAgent,fileName);
      response.addHeader("Content-Disposition", "attachment; filename=" + name);
    } catch (Exception e) {
   //   logger.error("",e);
    }
  }
  public static String encodeFilename(String userAgent, String fileName)  {
    if (userAgent == null) {
      return fileName;
    }
    try {
      // Firefox
      if (-1 != userAgent.toLowerCase().indexOf("firefox")) {
        fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
      }
      // IE
      else /*if (-1 != userAgent.indexOf("MSIE"))*/ {
        fileName = URLEncoder.encode(fileName, "UTF-8");
      }
    } catch (UnsupportedEncodingException e) {
    //  logger.error("",e);
    }
    return fileName;
  }
}
