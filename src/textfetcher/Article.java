package textfetcher;

import java.util.Collections;
import java.util.List;

import lombok.Getter;

public class Article {
  @Getter private final List<String> sentences;
  
  public Article(List<String> sentences){
    this.sentences = Collections.unmodifiableList(sentences);
  }
}
