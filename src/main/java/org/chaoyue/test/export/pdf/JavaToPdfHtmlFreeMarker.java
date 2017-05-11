package org.chaoyue.test.export.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.chaoyue.test.export.pdf.util.FreeMarkerUtil;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaoyue on 2017/5/11.
 */
public class JavaToPdfHtmlFreeMarker {
  private static final String DEST = "target/HelloWorld_CN_HTML_FREEMARKER.pdf";
  private static final String HTML = "pdf/template_freemarker.html";
  private static final String FONT = "pdf/simhei.ttf";

  public static void main(String[] args) throws IOException, DocumentException {
    Map<String, Object> data = new HashMap();
    data.put("name", "樊超越");
    String content = FreeMarkerUtil.freeMarkerRender(data, HTML);
    JavaToPdfHtmlFreeMarker.createPdf(content, DEST);
  }

  public static void createPdf(String content, String dest) throws IOException, DocumentException {
    // step 1
    Document document = new Document();
    // step 2
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
    // step 3
    document.open();
    // step 4
    XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
    fontImp.register(FONT);
    XMLWorkerHelper.getInstance().parseXHtml(writer, document,
      new ByteArrayInputStream(content.getBytes()), null, Charset.forName("UTF-8"), fontImp);
    // step 5
    document.close();

  }
}
