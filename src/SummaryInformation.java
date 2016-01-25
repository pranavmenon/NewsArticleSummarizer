import java.util.ArrayList;


public class SummaryInformation {
	double percentageOfSizeReduced;
	ArrayList<String> topKeyWords;
	ArrayList<Sentence> sentenceList;
	
	public SummaryInformation(double percentageOfSizeReduced, ArrayList<String> topKeyWords, ArrayList<Sentence> sentenceList){
		this.percentageOfSizeReduced = percentageOfSizeReduced;
		this.topKeyWords = topKeyWords;
		this.sentenceList = sentenceList;
	}
}
