package org.chaoyue.test.export.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by chaoyue on 2017/5/11.
 */
public class JavaToPdfCN {
  private static final String DEST = "target/HelloWorld_CN.pdf";
  private static final String FONT = "pdf/simhei.ttf";

  public static void main(String[] args) throws FileNotFoundException, DocumentException {
    Document document = new Document();
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
    document.open();
    Font f1 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
    document.add(new Paragraph("hello world,我是樊超越", f1));
    document.close();
    writer.close();
  }
}
