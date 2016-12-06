package service.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

public class HandExtractorServiceImplIT {

	@Test
	public void testReadingHand() {
		File file;
		try {
			file = ResourceUtils.getFile(this.getClass().getResource("/HH20160930-224100 - 4754389 - RING - $0.02-$0.05 - HOLDEM - NL - TBL No.11049214.txt"));
			HandExtractorServiceImpl handExtractor = new HandExtractorServiceImpl();
			List<String> handStrings = handExtractor.getAllHandStringsFromFile(file);
			assertEquals(33, handStrings.size()); // 33 because I manually counted in this particular file
			long validHandCount = handStrings.stream()
				.filter( str -> str.contains("Ignition Hand #") )
				.count();
			assertEquals(33, validHandCount);
		} catch (IOException e) {
			System.out.println("Can't find file in resource folder\n");
		}
	}
}
