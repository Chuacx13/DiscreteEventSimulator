class Arrive extends Event {

    Arrive(Customer customer) {
        super(customer);
    }

    @Override 
    public String toString() {
        return String.format("%.3f %d arrives", 
                this.customer.getArrivalTime(), this.customer.getId());
    }

    @Override 
    public int getServerId() {
        return 0;
    }

    @Override
    public Pair<Event, ImList<Server>> getNextEvent(ImList<Server> serverList, 
            ImList<Server> selfCheckOutList) {

        for (Server s: serverList) {
            if (this.getTime() >= s.getFreeTime()) {
                return new Pair<>(new Serve(this.customer, s), serverList);
            }
        } 

        for (Server s: selfCheckOutList) {
            if (this.getTime() >= s.getFreeTime() && selfCheckOutList.get(0).getQueue() 
                    == selfCheckOutList.get(0).getOriginalQueue()) {
                return new Pair<>(new Serve(this.customer, s), selfCheckOutList);
            }
        }

        for (Server s: serverList) {
            if (s.getQueue() > 0) {
                Server server = new Server(s.getId(), s.getFreeTime(), s.getQueue() - 1,
                        s.getOriginalQueue(), s.getRestTime(), s.getNumOfServers());
                serverList = serverList.set(s.getId() - 1, server);
                return new Pair<>(new Wait(this.customer, server), serverList);
            }
        }

        for (Server s: selfCheckOutList) { 
            if (s.getQueue() > 0) {
                Server server = new Server(s.getId(), s.getFreeTime(), s.getQueue() - 1, 
                        s.getOriginalQueue(), s.getRestTime(), s.getNumOfServers());
                selfCheckOutList = selfCheckOutList.set(s.getId() - 
                        s.getNumOfServers() - 1, server);
                SelfCheckOut selfCheck = new SelfCheckOut(selfCheckOutList, server.getQueue());
                return new Pair<>(new Wait(this.customer, server), selfCheck.getSelfCheck());
            }
        }

        return new Pair<>(new Leave(this.customer), serverList);
    }

    @Override
    public Stats getValues(ImList<Server> serverList, 
            ImList<Server> selfCheckOutList, Stats stats) {

        for (Server s: serverList) {
            if (this.getTime() >= s.getFreeTime()) {
                return new Stats(stats.getServed() + 1, 
                        stats.getLeave(), stats.getWait(), stats.getWaitTime());
            }
        }

        for (Server s: selfCheckOutList) {
            if (this.getTime() >= s.getFreeTime() && selfCheckOutList.get(0).getQueue() 
                    == selfCheckOutList.get(0).getOriginalQueue()) {
                return new Stats(stats.getServed() + 1, 
                        stats.getLeave(), stats.getWait(), stats.getWaitTime());
            }
        }

        for (Server s: serverList) {
            if (s.getQueue() > 0) {
                return new Stats(stats.getServed() + 1, stats.getLeave(), stats.getWait() + 1, 
                        stats.getWaitTime());
            }
        }

        for (Server s: selfCheckOutList) {
            if (s.getQueue() > 0) {
                return new Stats(stats.getServed() + 1, stats.getLeave(), stats.getWait() + 1, 
                    stats.getWaitTime());
            }
        }

        return new Stats(stats.getServed(), 
                stats.getLeave() + 1, stats.getWait(), stats.getWaitTime());
    }
}
