package br.com.ravelino.letty.poc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.ravelino.letty.poc.util.PDFUtil;

/**
 * @author ravelino
 *
 */
@Controller
@RequestMapping("/pdf")
public class PDFController {
	
	private static final Logger LOG = LoggerFactory.getLogger(PDFController.class);
	
	private static final String MERGE_FILE_NAME = "merged";
	
	private static final String MERGE_FILE_ATTACHMENT = "attachment; filename=\"".concat(MERGE_FILE_NAME).concat(".pdf\"");

    @PostMapping("/merge")
    public ResponseEntity<InputStreamResource> merge(
    			@RequestParam("pdf1") MultipartFile pdf1, @RequestParam("pdf2") MultipartFile pdf2) throws FileNotFoundException {
    	
    		LOG.info("Method merge invoked in PDFController");
    		
    		final File fileMerged = PDFUtil.createFile(MERGE_FILE_NAME).merge(pdf1, pdf2).getPdfMergedFile();
    		
    		final InputStreamResource isr = new InputStreamResource(new FileInputStream(fileMerged));
    		
    		return ResponseEntity
    				.ok()
    				.contentType(MediaType.APPLICATION_PDF)
    				.header(HttpHeaders.CONTENT_DISPOSITION, MERGE_FILE_ATTACHMENT)
    				.contentLength(fileMerged.length())
    				.body(isr);
    	
    }
}