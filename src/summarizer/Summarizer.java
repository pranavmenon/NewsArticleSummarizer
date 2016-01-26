package summarizer;
import java.util.ArrayList;

import textfetcher.*;


public class Summarizer {

	//TODO
	public static SummaryInformation createSummary(ArrayList<Sentence> allSentencesList){
		ArrayList<Sentence> summaryList = new ArrayList<Sentence>();
		
		SummaryInformation summaryInfo = new SummaryInformation(0, null, summaryList);
		return summaryInfo;
	}
}
