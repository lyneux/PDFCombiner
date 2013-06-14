package uk.co.infokinesis.pdfutils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.ResourceBundle;


/**
 * 
 */
// TODO: Add log4j in place of System.out
// TODO: Parameterise with properties file
public class PDFCombiner {
	
	private static File sourceDirectory;
	private static File targetDirectory;
	private static String targetFilename;

	public static void main( String[] args ) {
     
		ResourceBundle configuration = ResourceBundle.getBundle("PDFCombiner");
		
        // Source pdfs
    	sourceDirectory = new File(configuration.getString("sourceDirectory"));
    	targetDirectory = new File(configuration.getString("targetDirectory"));
    	targetFilename = configuration.getString("targetFilename");
    	
    	// Validate configuration
    	if (!sourceDirectory.exists()) {
    		System.out.println("The source directory: " + sourceDirectory + " does not exist");
    		System.exit(0);
    	} else {
    		// Exists
        	if (!sourceDirectory.isDirectory()) {
        		System.out.println("The source directory: " + sourceDirectory + " is not a directory");
        		System.exit(0);
        	}
    	}
    	
    	if (!targetDirectory.exists()) {
    		System.out.println("The target directory: " + targetDirectory + " does not exist");
    		System.out.println("Attempting to create the target directory: " + targetDirectory);
    		boolean success = targetDirectory.mkdirs();
    		if (!success) {
        		System.out.println("The target directory: " + targetDirectory + " could not be created");
        		System.exit(0);
    		}
    	} else {
    		if (!targetDirectory.isDirectory()) {
        		System.out.println("The target directory: " + targetDirectory + " is not a directory");
        		System.exit(0);
        	}
    	}
    	
    	
    	// Get all PDFs that are in the directory:
		System.out.println("==============Searching for PDFs=========");
		File[] pdfs = findAllPDFs(sourceDirectory);
		if (pdfs.length == 0) {
    		System.out.println("No PDFs found in the source directory: " + sourceDirectory);
    		System.exit(0);
		}
		
		// Determine the groupings of PDFs to be merged:
		System.out.println("==============Grouping PDFs==============");
		PDFMergeGroupRouter mergeGroupRouter = new PDFMergeGroupRouter();
		HashMap<Integer, PDFMergeGroup> mergeGroups = mergeGroupRouter.determineMergeGroups(pdfs);
		System.out.println(mergeGroups);
		
		// Merge the PDFs!
		System.out.println("==============Merging PDFs===============");
		PDFMerger pdfMerger = new PDFMerger();
		for (PDFMergeGroup pdfMergeGroup : mergeGroups.values()) {
			pdfMerger.mergePDFs(pdfMergeGroup, targetDirectory, targetFilename);
		}
        
		System.out.println("=========================================");
		System.out.println(" COMPLETE");
		System.out.println("=========================================");
		
    }
    
    
    /**
     * Returns all PDF Files that exist in the directory
     * @param sourceDirectory
     * @return
     */
    private static File[] findAllPDFs(File sourceDirectory) {
    	
    	// create new filename filter for PDFs
        FilenameFilter fileNameFilter = new FilenameFilter() {
  
           @Override
           public boolean accept(File dir, String name) {
              if(name.lastIndexOf('.')>0) {
            	  
                 // get last index for '.' char
                 int lastIndex = name.lastIndexOf('.');
                 
                 // get extension
                 String str = name.substring(lastIndex);
                 
                 // match path name extension
                 if(str.equals(".pdf")) {
                    return true;
                 }
              }
              return false;
           }
        };
		
        File[] files = sourceDirectory.listFiles(fileNameFilter);
		
        System.out.println("Found " + files.length + " files:");
        for (File file : files) {
			System.out.println("   Found file: " + file);
		}
        
        return files;
        
    }
    
}
