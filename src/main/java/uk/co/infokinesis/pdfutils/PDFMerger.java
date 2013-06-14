package uk.co.infokinesis.pdfutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFMerger {

	public void mergePDFs(PDFMergeGroup mergeGroup, File targetDirectory, String fileName) {
		
	    try {
        
	    	List<InputStream> list = new ArrayList<InputStream>();

	    	for (File file : mergeGroup.getFilesToBeMerged()) {
				list.add(new FileInputStream(file));
			}

	    	// Resulting pdf
	    	String targetFilename = targetDirectory.getPath() + "/" + fileName + mergeGroup.getNumberOfPages() + "Pages.pdf";
	    	OutputStream out = new FileOutputStream(new File(targetFilename));

	    	System.out.println("Merging " + mergeGroup.getNumberOfPages() + " page PDFs:");
	    	doMerge(list, out);
	    	System.out.println("Merge complete, file created: " + targetFilename);

	    } catch (DocumentException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    
	}
	
	
	/**
     * Merge multiple pdf into one pdf
     * 
     * @param list
     *            of pdf input stream
     * @param outputStream
     *            output file output stream
     * @throws DocumentException
     * @throws IOException
     */
    public static void doMerge(List<InputStream> list, OutputStream outputStream)
            throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        
        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //import the page from source pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                //add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }
        }
        
        outputStream.flush();
        document.close();
        outputStream.close();
    }
}
