package org.chaoyue.test.export.pdf.flyingsaucer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.chaoyue.test.export.pdf.util.FreeMarkerUtil;
import org.chaoyue.test.export.pdf.util.PathUtil;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaoyue on 2017/5/11.
 */
public class JavaToPdfHtmlFreeMarker {

  private static final String DEST = "target/HelloWorld_CN_HTML_FREEMARKER_FS.pdf";
  private static final String HTML = "pdf/template_freemarker_fs.html";
  private static final String FONT = "pdf/simhei.ttf";
  private static final String LOGO_PATH = "file://"+ PathUtil.getCurrentPath()+ "/pdf/logo.png";

  public static void main(String[] args) throws IOException, DocumentException, com.lowagie.text.DocumentException {
    Map<String,Object> data = new HashMap();
    data.put("name","樊超越");
    String content = FreeMarkerUtil.freeMarkerRender(data,HTML);
    JavaToPdfHtmlFreeMarker.createPdf(content,DEST);
  }

  public static void createPdf(String content,String dest) throws IOException, DocumentException, com.lowagie.text.DocumentException {
    ITextRenderer render = new ITextRenderer();
    ITextFontResolver fontResolver = render.getFontResolver();
    fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
    // 解析html生成pdf
    render.setDocumentFromString(content);
    //解决图片相对路径的问题
    render.getSharedContext().setBaseURL(LOGO_PATH);
    render.layout();
    render.createPDF(new FileOutputStream(dest));
  }
}
