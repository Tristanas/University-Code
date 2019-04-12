/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Vilius
 */
public class GeneratePDF {
    
    /** Path to the resulting PDF file. */
    public static final String RESULT
        = "hello_letter.pdf";
    public static final String DEST = "simple_table.pdf";
 
    /**
     * Creates a PDF file: hello_letter.pdf.
     * @param    args    no arguments needed
     * @throws com.itextpdf.text.DocumentException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws DocumentException, IOException {
   
           
        File file = new File(DEST);
        new GeneratePDF().createPdf(DEST);
    }
    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfPTable table = new PdfPTable(8);
        for(int aw = 0; aw < 16; aw++){
            table.addCell("hi");
        }
        document.add(table);
        document.close();
    }
 
    public void oldMain() throws FileNotFoundException, DocumentException {
         // step 1
    	// Specifying the page size
        Document document = new Document(PageSize.LETTER);
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(RESULT));
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph("labas "));
        // step 5
        document.close();
    }
}

