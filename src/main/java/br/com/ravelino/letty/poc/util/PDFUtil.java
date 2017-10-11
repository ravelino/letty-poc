package br.com.ravelino.letty.poc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ravelino
 *
 */
public class PDFUtil {

	private static final Logger LOG = LoggerFactory.getLogger(PDFUtil.class);

	private File pdfMergedFile;
	
	private PDFUtil(String fileName) {
		pdfMergedFile = createTempFile(fileName);
	}

	public static PDFUtil createFile(String filename) {
		return new PDFUtil(filename);
	}

	public PDFUtil merge(File pdf1, File pdf2) {

		final PDFMergerUtility pdfMergeUtility = new PDFMergerUtility();

		try {
			LOG.info("Starting merge...");

			final FileOutputStream outputStream = new FileOutputStream(pdfMergedFile);

			pdfMergeUtility.addSource(pdf1);
			pdfMergeUtility.addSource(pdf2);

			pdfMergeUtility.setDestinationStream(outputStream);

			pdfMergeUtility.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());

			LOG.info("Files merged...");
			LOG.info("Temp file was in: {}", pdfMergedFile.getAbsolutePath());

		} catch (FileNotFoundException e) {
			LOG.error("File not found: " + e.getMessage(), e);
		} catch (IOException e) {
			LOG.error("Error merging: " + e.getMessage(), e);
		}
		
		return this;
	}
	
	public PDFUtil merge(MultipartFile pdf1, MultipartFile pdf2) {
		merge(multipart2File(pdf1), multipart2File(pdf2));
		return this;
	}

	public File getPdfMergedFile() {
		return pdfMergedFile;
	}

	private File multipart2File(MultipartFile multipart) {
		final File fileTemp = createTempFile(multipart.getOriginalFilename());

		try {
			final byte[] bytes = multipart.getBytes();
			final Path path = Paths.get(fileTemp.getAbsolutePath());
			Files.write(path, bytes);
		} catch (IOException e) {
			LOG.error("Error parsing MultipartFile to File: "+ e.getMessage(), e);
		}

		return fileTemp;
	}
	
	private File createTempFile(String filename) {
		File tempFile = null;
		try {
			tempFile = File.createTempFile(filename, ".pdf");
		} catch (IOException e) {
			LOG.error("Error creating file: " + e.getMessage(), e);
		}
		return tempFile;
	}

}
