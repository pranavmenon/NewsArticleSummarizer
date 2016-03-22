package summarizer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class RankedSentence {
   @Getter private final String sentence;
   
   //chronology = location where this sentence appears in the article. 0 = beginning of article
   @Getter private final int chronology;
   @Getter @Setter private int rank;
   @Getter private Map<String, Integer> wordFrequencyMap;
  
   
  public RankedSentence(String sentence, int chronology){
    this.sentence = sentence;
    this.chronology = chronology;
    this.rank = 0;
    this.wordFrequencyMap = new HashMap<String, Integer>();
  }
  
  
  
  public void calculateRank(Map<String, Integer> globalWordFrequencyMap){
    for(String word : wordFrequencyMap.keySet()){
      int localFrequency = wordFrequencyMap.get(word);
      int globalFrequency = globalWordFrequencyMap.get(word);
      rank += (localFrequency*globalFrequency);
    }
  }
  
  
  
  public static final Comparator<RankedSentence> RANK_DESCENDING_COMPARATOR = new Comparator<RankedSentence>() {
    @Override
    public int compare(RankedSentence r1, RankedSentence r2) {
      if(r1==null || r2==null){
        return 0;
      }
      return Integer.compare(r2.rank, r1.rank);
    }
  };
  
  
  
  public static final Comparator<RankedSentence> CHRONOLOGY_ASCENDING_COMPARATOR = new Comparator<RankedSentence>() {
    @Override
    public int compare(RankedSentence r1, RankedSentence r2) {
      if(r1==null || r2==null){
        return 0;
      }
      return Integer.compare(r1.chronology, r2.chronology);
    }
  };
  
}
