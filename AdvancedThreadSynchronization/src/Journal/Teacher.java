package Journal;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Teacher implements Runnable {
  private final String teacherName;
  private final Journal journal;
  private final int nWeeks;

  public Teacher(String teacherName, int nWeeks, Journal journal) {
    this.teacherName = teacherName;
    this.journal = journal;
    this.nWeeks = nWeeks;
  }

  @Override
  public void run() {
    for (int i = 0; i < nWeeks; i++) {
      var groupNames = journal.groups.keySet().stream().sorted().collect(Collectors.toList());
      for (String groupName : groupNames) {
        var studentNames = new ArrayList<>(this.journal.groups.get(groupName).groupList.keySet());
        for (Integer studentName : studentNames) {
          int mark = (int) (Math.round(100 * Math.random()));
          journal.addMark(groupName, studentName, mark + ": " + this.teacherName + "(w" + (i+1) + ")");
        }
      }
    }
  }
}
