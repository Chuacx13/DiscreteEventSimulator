abstract class Event {

    protected final Customer customer;

    Event(Customer customer) {
        this.customer = customer;
    }

    double getTime() { 
        return this.customer.getArrivalTime();
    }
    
    int getCustomerId() { 
        return this.customer.getId();
    }

    Customer getCustomer() {
        return this.customer;
    }

    abstract int getServerId();

    abstract Pair<Event, ImList<Server>> getNextEvent(
            ImList<Server> serverList, ImList<Server> selfCheckOutList);

    abstract Stats getValues(ImList<Server> serverList, 
            ImList<Server> selfCheckOutList, Stats stats);
}
