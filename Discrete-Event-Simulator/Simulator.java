import java.util.function.Supplier;

class Simulator { 
    private final int numOfServers;
    private final int numOfSelfChecks;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;
    private final Supplier<Double> restTimes;

    Simulator(int numOfServers, int numOfSelfChecks, int qmax,
            ImList<Double> arrivalTimes, Supplier<Double> serviceTimes, 
            Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.numOfSelfChecks = numOfSelfChecks;
        this.qmax = qmax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
        this.restTimes = restTimes;
    }

    private ImList<Server> createServerList() {   
        ImList<Server> serverList = new ImList<Server>();
        for (int i = 1; i <= this.numOfServers; i++) {
            serverList = serverList.add(new Server(i, this.qmax, 
                        this.qmax, this.restTimes, this.numOfServers));
        }
        return serverList;
    }

    private PQ<Event> createArriveList() {
        PQ<Event> eventList = new PQ<Event>(new PriorityComp());
        for (int i = 1; i <= this.arrivalTimes.size(); i++) {
            Customer c = new Customer(i, arrivalTimes.get(i - 1), this.serviceTimes);
            eventList = eventList.add(new Arrive(c));
        }
        return eventList;
    }

    private SelfCheckOut createSelfCheckOut() {
        SelfCheckOut so = new SelfCheckOut(this.numOfSelfChecks, this.numOfServers, 
                this.qmax, this.qmax, this.restTimes);
        return so;
    }

    public String simulate() {
        ImList<Server> serverList = createServerList();
        PQ<Event> eventList = createArriveList();
        SelfCheckOut counters = createSelfCheckOut();
        Event newEvent;
        Stats stats = new Stats(0, 0, 0, 0);
        String result = "";
        //for (int i = 0; i <= 80; i++) {
        while (!eventList.isEmpty()) {
            ImList<Server> selfCheckOut = counters.getSelfCheck();
            Pair<Event, PQ<Event>> product = eventList.poll();
            Pair<Event, ImList<Server>> next = product.first()
                .getNextEvent(serverList, selfCheckOut);
            newEvent = next.first();
            stats = product.first().getValues(serverList, selfCheckOut, stats);
            eventList = product.second();

            if (newEvent.getServerId() <= this.numOfServers) {
                serverList = next.second();
            } else {
                counters = new SelfCheckOut(next.second());
            }
            if (product.first().toString() != "") {
                result += product.first() + "\n";
            }
            if (newEvent != product.first()) {
                eventList = eventList.add(newEvent);
            }
        }
        result += stats;
        return result;
    }
}
