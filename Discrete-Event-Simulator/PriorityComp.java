import java.util.Comparator;

class PriorityComp implements Comparator<Event> {

    public int compare(Event event1, Event event2) { 
        double diff = event1.getTime() - event2.getTime();
        if (diff < 0) {
            return -1;
        } else if (diff > 0) {
            return 1;
        } else {
            int diff2 = event1.getCustomerId() - event2.getCustomerId();
            if (diff2 < 0) { 
                return -1;
            } else if (diff2 > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
