class Break extends Event {

    private final Server server;

    Break(Customer customer, Server server) {
        super(customer);
        this.server = server;
    }

    @Override 
    public String toString() {
        return "";
    }

    @Override
    public double getTime() {
        return this.server.getFreeTime();
    }

    @Override 
    public int getServerId() {
        return this.server.getId();
    }

    @Override 
    public Pair<Event, ImList<Server>> getNextEvent(
            ImList<Server> serverList, ImList<Server> selfCheckOutList) {
        Server server = serverList.get(this.server.getId() - 1);
        Server s = new Server(this.server.getId(), this.server.getFreeTime(), 
                server.getQueue(), this.server.getOriginalQueue(), 
                this.server.getRestTime(), false, this.server.getNumOfServers());
        serverList = serverList.set(this.server.getId() - 1, s);
        return new Pair<>(this, serverList);       
    }

    @Override
    public Stats getValues(ImList<Server> serverList, 
            ImList<Server> selfCheckOutList, Stats stats) {
        return new Stats(stats.getServed(), stats.getLeave(), stats.getWait(), stats.getWaitTime());
    } 

}
