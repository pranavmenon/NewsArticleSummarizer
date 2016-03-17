package summarizer;

import lombok.Getter;
import lombok.Setter;

public class RankedSentence {
   @Getter private final String sentence;
   @Getter @Setter private int rank;
  
  public RankedSentence(String sentence){
    this.sentence = sentence;
    this.rank = 0;
  }
  
}
