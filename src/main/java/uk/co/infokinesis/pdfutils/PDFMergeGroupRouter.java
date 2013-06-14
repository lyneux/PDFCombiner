package uk.co.infokinesis.pdfutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import com.itextpdf.text.pdf.PdfReader;

public class PDFMergeGroupRouter {

	public HashMap<Integer, PDFMergeGroup> determineMergeGroups(File[] pdfs) { 
		 
		HashMap<Integer, PDFMergeGroup> mergeGroups = new HashMap<Integer, PDFMergeGroup>();
		
		for (File pdf : pdfs) {
			
			try {
				PdfReader reader = new PdfReader(new FileInputStream(pdf));
				int numberOfPages = reader.getNumberOfPages();
				
				PDFMergeGroup targetMergeGroup = null;
				if (!mergeGroups.containsKey(numberOfPages)) {
					targetMergeGroup = new PDFMergeGroup(numberOfPages);
					mergeGroups.put(numberOfPages, targetMergeGroup);
				} else {
					targetMergeGroup = mergeGroups.get(numberOfPages);
				}
				targetMergeGroup.addFileToBeMerged(pdf);
				
			} catch (FileNotFoundException fnfe) {
				
			} catch (IOException ioe) {
				
			}
	        
		}
		 
		return mergeGroups;
	}
	
	
	
}

