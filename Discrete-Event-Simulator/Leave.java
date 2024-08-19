class Leave extends Event {

    Leave(Customer customer) {
        super(customer);
    }

    @Override
    public int getServerId() {
        return 0;
    }

    @Override 
    public Pair<Event, ImList<Server>> getNextEvent(
            ImList<Server> serverList, ImList<Server> selfCheckOutList) {
        return new Pair<>(this, serverList);
    }

    @Override
    public Stats getValues(ImList<Server> serverList, 
            ImList<Server> selfCheckOutList, Stats stats) {
        return new Stats(stats.getServed(), stats.getLeave(), stats.getWait(), stats.getWaitTime());
    }

    @Override 
    public String toString() { 
        return String.format("%.3f %d leaves", this.customer.getArrivalTime(), 
                this.customer.getId());
    }
}   
