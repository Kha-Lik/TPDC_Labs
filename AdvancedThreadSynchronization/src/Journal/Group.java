package Journal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Group {
  private final String groupName;
  public ConcurrentHashMap<Integer, List<String>> groupList = new ConcurrentHashMap<>();

  public Group(String groupName, int sizeOfGroup) {
    this.groupName = groupName;
    generateGroupList(sizeOfGroup);
  }

  private void generateGroupList(int sizeOfGroup) {
    for (int i = 0; i < sizeOfGroup; i++) {
      this.groupList.put(i + 1, new ArrayList<>());
    }
  }

  public String getGroupName() {
    return groupName;
  }
}
