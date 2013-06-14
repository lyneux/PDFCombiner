PDFCombiner
===========

A small java utility designed to be run from the command line that combines PDFs in a source directory together based on the number of pages within the PDF.

For example, if the source directory contains 4 PDFs, two with 1 page and two with 2 pages, the output will be two PDFs, one containing the documents with 1 page and the other containing the documents with two pages.

Build using: mvn package appassembler:assemble

This creates a standalone java application in the target directory called "PDFCombiner" which can be deployed to any machine with Java installed on it.

Shell scripts to execute the application are contained within the bin directory.

Configure your source and target directories in the PDFCombiner.properties file in the conf subdirectory. 
