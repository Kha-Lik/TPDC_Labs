package Journal;

import java.util.LinkedList;

public class JournalMain {
    public static void main(String[] args) {
        Journal journal = new Journal();
        int nWeeks = 2;

        var threads = new LinkedList<Thread>();

        threads.add(new Thread(new Teacher("Lecturer 1", nWeeks, journal)));
        threads.add(new Thread(new Teacher("Assistant 1", nWeeks, journal)));
        threads.add(new Thread(new Teacher("Assistant 2", nWeeks, journal)));
        threads.add(new Thread(new Teacher("Assistant 3", nWeeks, journal)));

        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        journal.show();
    }
}
