/**
 * 
 */
package br.com.ravelino.letty.poc.util;

import java.io.File;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author ravelino
 *
 */
public class PDFUtilTest {
	
	private static final File PDF1 = new File("src/test/resources/pdf/rogerio.pdf");
	
	private static final File PDF2 = new File("src/test/resources/pdf/letty.pdf");

	@Test
	public void mergeTest() {
		final File fileMerged = PDFUtil.createFile("mergeTeste").merge(PDF1, PDF2).getPdfMergedFile();
		assertNotNull(fileMerged);
		assertTrue(fileMerged.exists());
		assertTrue(fileMerged.length() > 0);
	}
}
