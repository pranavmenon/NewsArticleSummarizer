# NewsArticleSummarizer
<pre>INPUT: The URL of a webpage containing a news article</pre>
<pre>OUTPUT: The summary of the news article</pre>
<br>
How it works:
<ol>
  <li>Scrapes article sentences from the given web page</li>
  <li>Breaks each sentence into a list of words</li>
  <li>Gets the lemma form of each word (https://en.wikipedia.org/wiki/Lemma_(morphology))</li>
  <li>Calculates the score (number of occurrences) of each lemma</li>
  <li>Assigns a rank to each sentence based on the scores of its individual lemmas</li>
  <li>Returns the highest ranked sentences</li>
</ol>
