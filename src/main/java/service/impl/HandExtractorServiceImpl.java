package service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<Hand> getAllHandsFromFile(File file) {
		List<Hand> hands = new ArrayList<Hand>();
		int handCount = 0;
		StringBuilderWriter handString = new StringBuilderWriter();
		HandBuilder handBuilder = new CashGameHandBuilder(); //TODO dependency inject this??
		
		LineIterator iterator = null;
		try {
			iterator = FileUtils.lineIterator(file, "UTF-8");
			while (iterator.hasNext()) {
				String line = iterator.nextLine();
				if (! "".equals(line)) { //line isn't empty
					if (handString == null) { //TODO handString 2nd line logic is weird, revise
						handString = new StringBuilderWriter();
					}
					handString.write(line);
				} else { //line is empty
					if (handString != null) { //line is empty for first time
						hands.add(handBuilder.build(handString.toString()));
						handString.close();
						handString = null;
						logger.info("Finished hand {}", ++handCount);
					} else { //line is empty for the second time (each hand is separated by 2 blank lines)
						//do nothing
						//move on to the next line which should have stuff
					}
				}
				
			}
		} catch (IOException e) {
			logger.error("Caught IOException", e);
		} finally {
			LineIterator.closeQuietly(iterator);
		}
		
		return hands;
	}

}
