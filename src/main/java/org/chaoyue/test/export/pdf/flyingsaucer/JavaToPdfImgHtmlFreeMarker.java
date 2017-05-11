package org.chaoyue.test.export.pdf.flyingsaucer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.chaoyue.test.export.pdf.util.FreeMarkerUtil;
import org.chaoyue.test.export.pdf.util.PathUtil;
import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.FontMappings;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by chaoyue on 2017/5/11.
 */
public class JavaToPdfImgHtmlFreeMarker {
  private static final String DEST = "target/HelloWorld_CN_HTML_FREEMARKER_FS_IMG.png";
  private static final String HTML = "pdf/template_freemarker_fs.html";
  private static final String FONT = "pdf/simhei.ttf";
  private static final String LOGO_PATH = "file://"+PathUtil.getCurrentPath()+ "/pdf/logo.png";
  private static final String IMG_EXT = "png";

  public static void main(String[] args) throws IOException, DocumentException, com.lowagie.text.DocumentException {
    Map<String,Object> data = new HashMap();
    data.put("name","樊超越");

    String content = FreeMarkerUtil.freeMarkerRender(data,HTML);
    ByteArrayOutputStream pdfStream = JavaToPdfImgHtmlFreeMarker.createPdf(content);
    ByteArrayOutputStream imgSteam = JavaToPdfImgHtmlFreeMarker.pdfToImg(pdfStream.toByteArray(),2,1,IMG_EXT);

    FileOutputStream fileStream = new FileOutputStream(new File(DEST));
    fileStream.write(imgSteam.toByteArray());
    fileStream.close();

  }

  /**
   * 根据模板生成pdf文件流
   */
  public static ByteArrayOutputStream createPdf(String content) {
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    ITextRenderer render = new ITextRenderer();
    ITextFontResolver fontResolver = render.getFontResolver();
    try {
      fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
    } catch (com.lowagie.text.DocumentException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // 解析html生成pdf
    render.setDocumentFromString(content);
    //解决图片相对路径的问题
    render.getSharedContext().setBaseURL(LOGO_PATH);
    render.layout();
    try {
      render.createPDF(outStream);
      return outStream;
    } catch (com.lowagie.text.DocumentException e) {
      e.printStackTrace();
    } finally {
      try {
        outStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * 根据pdf二进制文件 生成图片文件
   *
   * @param bytes   pdf二进制
   * @param scaling 清晰度
   * @param pageNum 页数
   */
  public static ByteArrayOutputStream pdfToImg(byte[] bytes, float scaling, int pageNum,String formatName) {
    //推荐的方法打开PdfDecoder
    PdfDecoder pdfDecoder = new PdfDecoder(true);
    FontMappings.setFontReplacements();
    //修改图片的清晰度
    pdfDecoder.scaling = scaling;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      //打开pdf文件，生成PdfDecoder对象
      pdfDecoder.openPdfArray(bytes); //bytes is byte[] array with PDF
      //获取第pageNum页的pdf
      BufferedImage img = pdfDecoder.getPageAsImage(pageNum);

      ImageIO.write(img, formatName, out);
    } catch (PdfException e) {
      e.printStackTrace();
    } catch (IOException e){
      e.printStackTrace();
    }

    return out;
  }
}
