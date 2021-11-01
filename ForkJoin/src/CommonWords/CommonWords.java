package CommonWords;

import java.io.File;
import java.io.IOException;

public class CommonWords {
  public static void main(String[] args) throws IOException {
    String folderName = "./ForkJoin/resources/commonWords";

    WordCounter wordCounter = new WordCounter();
    Folder folder = Folder.fromDirectory(new File(folderName));

    System.out.println(wordCounter.getCommonWordsInParallel(folder));
  }
}
