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
	  String sentencesAsString = sentenceList.get(0);
	  for(int i=1; i<sentenceList.size(); i++){
	    sentencesAsString += ("\n"+sentenceList.get(i));
	  }
	  return String.format("(Reduced by %.2f%%)\n\nTop key words: %s\n\nSummary:\n%s\n",
	      percentageOfSizeReduced*100, topKeyWords, sentencesAsString);
	}
}
