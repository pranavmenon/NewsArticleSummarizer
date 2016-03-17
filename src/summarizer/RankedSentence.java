package summarizer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class RankedSentence {
   @Getter private final String sentence;
   @Getter @Setter private int rank;
   @Getter private Map<String, Integer> wordFrequencyMap;
  
   
  public RankedSentence(String sentence){
    this.sentence = sentence;
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
  
}
