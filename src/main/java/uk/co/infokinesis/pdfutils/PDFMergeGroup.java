package uk.co.infokinesis.pdfutils;

import java.io.File;
import java.util.ArrayList;

/**
 * Represents a group of PDFs that are to be merged
 * @author guy
 *
 */
public class PDFMergeGroup {
	
	private ArrayList<File> filesToBeMerged;
	private int numberOfPages;
	
	public PDFMergeGroup(int numberOfPages) {
		this.filesToBeMerged = new ArrayList<File>();
		this.numberOfPages = numberOfPages;
	}
	
	public int getNumberOfPages() {
		return this.numberOfPages;
	}
	
	public void addFileToBeMerged(File file) {
		this.filesToBeMerged.add(file);
	}
	
	public ArrayList<File> getFilesToBeMerged() {
		return this.filesToBeMerged;
	}
	
	public String toString() {
		String result = "PDFs with " + this.numberOfPages + " page\n";
		for (File file : this.filesToBeMerged) {
			result += "File: " + file + "\n";
		}
		return result;
	}

}
