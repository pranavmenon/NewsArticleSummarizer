package textfetcher;

import java.util.Collections;
import java.util.List;

import lombok.Getter;

public class Article {
  @Getter private final List<String> sentences;
  @Getter private final int length;
  
  public Article(List<String> sentences){
    this.sentences = Collections.unmodifiableList(sentences);
    int len = 0;
    for(String s : this.sentences){
      len += s.length();
    }
    length = len;
  }
}
