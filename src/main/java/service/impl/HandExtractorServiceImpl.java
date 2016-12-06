package service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.output.StringBuilderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import builder.HandBuilder;
import builder.impl.CashGameHandBuilder;
import service.HandExtractorService;
import service.model.Hand;

public class HandExtractorServiceImpl implements HandExtractorService {

	private final static Logger logger = LoggerFactory.getLogger(HandExtractorServiceImpl.class);
	
	public List<String> getAllHandStringsFromFile(File file) {
		List<String> hands = new ArrayList<String>();
		int handCount = 0;
		StringBuilderWriter handString = new StringBuilderWriter();
		
		LineIterator iterator = null;
		try {
			iterator = FileUtils.lineIterator(file, "UTF-8");
			while (iterator.hasNext()) {
				String line = iterator.nextLine();
				if (! "".equals(line)) { // Line isn't empty
					if (handString == null) { // TODO handString 2nd line logic is weird, refactor
						handString = new StringBuilderWriter();
					}
					handString.append(line).append('\n');
				} else { // Line is empty
					if (handString != null) { // Line is empty for first time
						hands.add(handString.append('\n').toString());
						handString.close();
						handString = null;
						handCount++;
					} else { // Line is empty for the second time (each hand is separated by 2 blank lines)
						// Move on to the next line which should have stuff
					}
				}
				
			}
		} catch (IOException e) {
			logger.error("Caught IOException", e);
		} finally {
			LineIterator.closeQuietly(iterator);
		}
		logger.info("Found {} hands", handCount);
		return hands;
	}
	
	// TODO this method hold code refactored from getAllHandStringsFromFile which was doing too much
	private List<Hand> buildHands(List<String> handStrings) {
		List<Hand> hands = new ArrayList<Hand>();
		HandBuilder handBuilder = new CashGameHandBuilder(); // TODO dependency inject this??
		return handStrings.stream()
				.map( handString -> handBuilder.build(handString))
				.collect(Collectors.toList());
	}

}
