package org.semlab.service.test;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * TODO
 * http://blog.csdn.net/loongshawn/article/details/51542309
 * http://blog.csdn.net/fighting_no1/article/details/51038966
 * @author houzhiwei
 * @date 2017/10/3 21:38
 */
public class PdfTest {

    @Test
    public void test() throws IOException {
        File f = new File("G:/教程和学习文档/水文模型/SWAT/ch01_overview_inputs.pdf");
//        PDDocument doc = PDDocument.load(new File("G:/DDL Driver/科研/开题/通知.pdf"));
        PDDocument doc = PDDocument.load(f);
        int pages = doc.getNumberOfPages();
        PDDocumentInformation docInfo = doc.getDocumentInformation();
        System.out.println(docInfo.getAuthor());
        System.out.println(docInfo.getKeywords());
        System.out.println(docInfo.getTitle());//Chapter 1: SWAT Input Data Overview
        System.out.println(docInfo.getSubject());
    }
}
