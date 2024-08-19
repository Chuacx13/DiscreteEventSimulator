import java.util.function.Supplier;

class SelfCheckOut {

    private final ImList<Server> selfCheckers;

    SelfCheckOut(int numOfSelfChecks, int numOfServers, 
            int currentQueue, int originalQueue, Supplier<Double> restTime) {
        ImList<Server> selfCheckOutList = new ImList<Server>();
        for (int i = 1; i <= numOfSelfChecks; i++) {
            Server server = new Server(numOfServers + i, currentQueue, 
                    originalQueue, restTime, numOfServers); 
            selfCheckOutList = selfCheckOutList.add(server);
        }
        this.selfCheckers = selfCheckOutList;
    }

    SelfCheckOut(ImList<Server> selfCheckOut, int queue) {
        ImList<Server> selfCheckOutList = selfCheckOut;
        for (int i = 0; i < selfCheckOut.size(); i++) {
            Server s = selfCheckOut.get(i);
            Server newServer = new Server(s.getId(), s.getFreeTime(), queue, 
                    s.getOriginalQueue(), s.getRestTime(), s.getNumOfServers());
            selfCheckOutList = selfCheckOutList.set(i, newServer);
        }
        this.selfCheckers = selfCheckOutList;
    }

    SelfCheckOut(ImList<Server> selfCheckOut) {
        this.selfCheckers = selfCheckOut;
    }

    public int getQueue() {
        return this.selfCheckers.get(0).getQueue();
    }

    public int getOriginalQueue() {
        return this.selfCheckers.get(0).getOriginalQueue();
    }

    public ImList<Server> getSelfCheck() {
        return this.selfCheckers;
    }

}
