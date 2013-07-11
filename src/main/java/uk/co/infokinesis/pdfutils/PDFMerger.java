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
import com.itextpdf.text.pdf.PdfCopy;
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
     * 
     * @param list
     *            of pdf input stream
     * @param outputStream
     *            output file output stream
     * @throws DocumentException
     * @throws IOException
     */
    public void doMerge(List<InputStream> list, OutputStream outputStream)
            throws DocumentException, IOException {
    	
        Document document = new Document();
        
        PdfCopy cp = new PdfCopy(document, outputStream);
        
        document.open();
        
        for (InputStream in : list) {
        	
            PdfReader r = new PdfReader(in);
            for (int k = 1; k <= r.getNumberOfPages(); ++k) {
                cp.addPage(cp.getImportedPage(r, k));
            }
            cp.freeReader(r);

        }
        
        cp.close();
        document.close();
        
    }
    
    
    
}
