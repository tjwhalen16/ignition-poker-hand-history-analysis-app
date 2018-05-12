package util;

import java.util.HashSet;
import java.util.Set;

public final class Constants {	
	private Constants() {}
	
	public final static Set<String> linesToSkip = new HashSet<String>();
	static {
		linesToSkip.add("Table enter user");
		linesToSkip.add("Table leave user");
		linesToSkip.add("Seat sit out");
		linesToSkip.add("Seat re-join");
	}

	public final static String boardPatternString = "\\*\\*\\* .*]$";
	public final static String allinPatternString = "(^.+) : [Aa]ll-in \\$([\\.\\d]+)\\s*";
	public final static String cardPatternString = "(([2-9jJQKA]|10)[cCdDsShH])";
}
