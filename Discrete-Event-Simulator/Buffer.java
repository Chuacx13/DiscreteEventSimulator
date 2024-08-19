class Buffer extends Event {

    private final Server server;

    Buffer(Customer customer, Server server) {
        super(customer);
        this.server = server;
    }

    @Override 
    public String toString() {
        return "";
    }

    @Override 
    public int getServerId() {
        return this.server.getId();
    }

    @Override
    public double getTime() {
        return this.server.getFreeTime();
    }

    @Override 
    public Pair<Event, ImList<Server>> getNextEvent(
            ImList<Server> serverList, ImList<Server> selfCheckOutList) {

        ImList<Server> newList = new ImList<Server>();
        Server server = this.server;
        if (this.server.getId() <= this.server.getNumOfServers()) {
            newList = serverList;
            server = newList.get(this.server.getId() - 1);
        } else {
            newList = selfCheckOutList;
            server = newList.get(this.server.getId() - this.server.getNumOfServers() - 1);
            for (Server s: selfCheckOutList) {
                Server newServer = new Server(s.getId(), s.getFreeTime(), 
                    this.server.getQueue(), s.getOriginalQueue(), 
                    s.getRestTime(), s.getNumOfServers());
                if (server.getFreeTime() > s.getFreeTime()) {
                    server = newServer;
                } else if (server.getFreeTime() == s.getFreeTime() 
                    && server.getId() > s.getId()) {
                    server = newServer;
                } 
            }
            if (server.getId() != this.server.getId()) {
                return new Pair<>(new Buffer(this.customer, server), selfCheckOutList);
            }
        }

        if (server.getOriginalQueue() - this.server.getQueue() == 1 && 
                server.getFreeTime() >= this.server.getFreeTime() &&
                !server.getResting()) {
            return new Pair<>(new Serve(this.customer, server), newList);
        } else if (server.getOriginalQueue() - this.server.getQueue() != 1 &&
                server.getFreeTime() >= this.server.getFreeTime() &&
                !server.getResting()) {
            Server s = new Server(this.server.getId(), server.getFreeTime(), 
                    this.server.getQueue() + 1, this.server.getOriginalQueue(),
                    this.server.getRestTime(), this.server.getNumOfServers());
            return new Pair<>(new Buffer(this.customer, s), newList);
        } else {
            Server s = new Server(this.server.getId(), server.getFreeTime(), 
                    this.server.getQueue(), this.server.getOriginalQueue(),
                    this.server.getRestTime(), this.server.getNumOfServers());
            return new Pair<>(new Buffer(this.customer, s), newList);
        }
    }

    @Override
    public Stats getValues(ImList<Server> serverList,
            ImList<Server> selfCheckOutList, Stats stats) {
        return new Stats(stats.getServed(), stats.getLeave(), 
                stats.getWait(), stats.getWaitTime());
    }

}
