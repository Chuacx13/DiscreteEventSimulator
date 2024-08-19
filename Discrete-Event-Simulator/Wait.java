class Wait extends Event {

    private final Server server;

    Wait(Customer customer, Server server) {
        super(customer);
        this.server = server;
    }

    @Override 
    public int getServerId() {
        return this.server.getId();
    }

    @Override 
    public Pair<Event, ImList<Server>> getNextEvent(
            ImList<Server> serverList, ImList<Server> selfCheckOutList) {
        if (this.getServerId() <= this.server.getNumOfServers()) {
            return new Pair<>(new Buffer(this.customer, this.server), serverList);
        } else {
            Server setServer = selfCheckOutList.get(this.server.getId() - 
                    this.server.getNumOfServers() - 1);
            for (Server s: selfCheckOutList) {
                Server newServer = new Server(s.getId(), s.getFreeTime(), this.server.getQueue(), 
                        s.getOriginalQueue(), s.getRestTime(), s.getNumOfServers());
                if (setServer.getFreeTime() > s.getFreeTime()) {
                    setServer = newServer;
                } else if (setServer.getFreeTime() == s.getFreeTime() 
                        && setServer.getId() > s.getId()) {
                    setServer = newServer;
                } 
            }
            return new Pair<>(new Buffer(this.customer, setServer), selfCheckOutList);
        }
    }

    @Override
    public Stats getValues(ImList<Server> serverList, 
            ImList<Server> selfCheckOutList, Stats stats) {
        return new Stats(stats.getServed(), stats.getLeave(), stats.getWait(), stats.getWaitTime());
    }

    @Override
    public String toString() {
        return String.format("%.3f %d waits at %s", 
                this.customer.getArrivalTime(), this.customer.getId(), this.server.toString());
    }
}

