package Journal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Journal {
    public ConcurrentHashMap<String, Group> groups = new ConcurrentHashMap<>();

    public Journal() {
        Group group1 = new Group("ІT-81", 29);
        Group group2 = new Group("ІT-82", 30);
        Group group3 = new Group("ІT-83", 28);

        this.groups.put(group1.getGroupName(), group1);
        this.groups.put(group2.getGroupName(), group2);
        this.groups.put(group3.getGroupName(), group3);
    }

    public void addMark(String groupName, Integer studentName, String mark) {
        synchronized (this.groups.get(groupName).groupList.get(studentName)) {
            this.groups.get(groupName).groupList.get(studentName).add(mark);
        }
    }

    public void show() {
        var groupNames = groups.keySet().stream().sorted().collect(Collectors.toList());

        for (String groupName : groupNames) {
            System.out.printf("Group name: %6s\n", groupName);
            var studentNames = groups.get(groupName).groupList.keySet().stream().sorted().collect(Collectors.toList());

            for (Integer studentName : studentNames) {
                System.out.printf("Student %4s %4s", studentName, "-");
                var marks = groups.get(groupName).groupList.get(studentName);

                for (String mark : marks) {
                    System.out.printf("%25s", mark);
                }

                System.out.println();
            }

            System.out.println();
        }
    }
}