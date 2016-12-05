package service;

import java.io.File;
import java.util.List;

import service.model.Hand;

public interface HandExtractorService {
	public List<String> getAllHandStringsFromFile(File file);
}
