package summarizer;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

public class SummaryInformation {
	@Getter private final double percentageOfSizeReduced;
	@Getter private final List<String> topKeyWords;
	@Getter private final List<String> sentenceList;
	
	public SummaryInformation(double percentageOfSizeReduced, List<String> topKeyWords, List<String> sentenceList){
		this.percentageOfSizeReduced = percentageOfSizeReduced;
		this.topKeyWords = Collections.unmodifiableList(topKeyWords);
		this.sentenceList = Collections.unmodifiableList(sentenceList);
	}
	
	@Override
	public String toString(){
	  return String.format("Article reduced by %f %\n\nTop key words: %s\n\nSummary:\n%s\n",
	      percentageOfSizeReduced, topKeyWords, sentenceList);
	}
}
