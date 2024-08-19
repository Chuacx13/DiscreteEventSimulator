class Serve extends Event {

    private final Server server;

    Serve(Customer customer, Server server) {
        super(customer);
        this.server = server;
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
        if (this.getServerId() <= this.server.getNumOfServers()) {
            newList = serverList;
            server = newList.get(this.server.getId() - 1);
        } else {
            newList = selfCheckOutList;
            server = newList.get(this.server.getId() - this.server.getNumOfServers() - 1);
        }

        Server s = this.server;
        if (this.server.getQueue() < this.server.getOriginalQueue()) {
            s = new Server(this.server.getId(), 
                    this.customer.getServiceTime() + server.getFreeTime(),  
                    server.getQueue() + 1, this.server.getOriginalQueue(), 
                    this.server.getRestTime(), this.server.getNumOfServers());
        } else {
            s = new Server(this.server.getId(), this.customer.getEndServiceTime(), 
                    this.server.getQueue(), this.server.getOriginalQueue(), 
                    this.server.getRestTime(), this.server.getNumOfServers());
        }

        if (this.getServerId() <= this.server.getNumOfServers()) {
            newList = newList.set(this.server.getId() - 1, s);
            return new Pair<>(new Done(this.customer, s), newList);
        } else {
            newList = newList.set(this.server.getId() - this.server.getNumOfServers() - 1, s);
            SelfCheckOut selfCheck = new SelfCheckOut(newList, s.getQueue());
            return new Pair<>(new Done(this.customer, s), selfCheck.getSelfCheck());
        }
    }

    @Override
    public Stats getValues(ImList<Server> serverList, 
            ImList<Server> selfCheckOutList, Stats stats) {
        if (this.server.getQueue() < this.server.getOriginalQueue()) {
            ImList<Server> newList = new ImList<Server>();
            Server server = this.server;
            if (this.getServerId() <= this.server.getNumOfServers()) {
                newList = serverList;
                server = newList.get(this.server.getId() - 1);
            } else {
                newList = selfCheckOutList;
                server = newList.get(this.server.getId() - this.server.getNumOfServers() - 1);
            }
            Stats printStats = new Stats(stats.getServed(), stats.getLeave(), stats.getWait(), 
                stats.getWaitTime() + server.getFreeTime() - this.customer.getArrivalTime());
            return printStats;
       
        } else {
            return new Stats(stats.getServed(), stats.getLeave(), 
                    stats.getWait(), stats.getWaitTime());
        }
    }

    @Override
    public String toString() {
        if (this.server.getQueue() < this.server.getOriginalQueue()) {
            return String.format("%.3f %d serves by %s", this.server.getFreeTime(),
                    this.customer.getId(), this.server.toString());
        } else {
            return String.format("%.3f %d serves by %s", this.customer.getArrivalTime(),
                    this.customer.getId(), this.server.toString());
        }
    }
}


