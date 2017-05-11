package org.chaoyue.test.export.pdf.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by chaoyue on 2017/5/11.
 */
public class FreeMarkerUtil {

  private static Configuration freemarkerCfg = null;

  static {
    freemarkerCfg =new Configuration();
    //freemarker的模板目录
    try {
      freemarkerCfg.setDirectoryForTemplateLoading(new File(PathUtil.getCurrentPath()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  /**
   * freemarker渲染html
   */
  public static String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
    Writer out = new StringWriter();
    try {
      // 获取模板,并设置编码方式
      Template template = freemarkerCfg.getTemplate(htmlTmp);
      template.setOutputEncoding("UTF-8");
      // 合并数据模型与模板
      template.process(data, out); //将合并后的数据和模板写入到流中，这里使用的字符流
      out.flush();
      return out.toString();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        out.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return null;
  }
}
