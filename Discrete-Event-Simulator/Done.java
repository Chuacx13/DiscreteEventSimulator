class Done extends Event {

    private final Server server;

    Done(Customer customer, Server server) {
        super(customer);
        this.server = server;
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
        double rest = this.server.getRest();
        int diff = this.server.getId() - this.server.getNumOfServers();
        if (rest == 0 && diff <= 0) {
            return new Pair<>(this, serverList);
        } else if (diff > 0) {
            return new Pair<>(this, selfCheckOutList);
        } else {
            Server server = serverList.get(this.server.getId() - 1);
            Server s = new Server(this.server.getId(), this.server.getFreeTime() + rest, 
                    server.getQueue(), this.server.getOriginalQueue(), 
                    this.server.getRestTime(), true, this.server.getNumOfServers());
            serverList = serverList.set(this.server.getId() - 1, s);
            return new Pair<>(new Break(this.customer, s), serverList);
        }
    }

    @Override
    Stats getValues(ImList<Server> serverList, 
            ImList<Server> selfCheckOutList, Stats stats) {
        return new Stats(stats.getServed(), stats.getLeave(), stats.getWait(), stats.getWaitTime());
    }

    @Override 
    public String toString() { 
        return String.format("%.3f %d done serving by %s", 
                this.server.getFreeTime(), this.customer.getId(), this.server.toString());
    }
}
