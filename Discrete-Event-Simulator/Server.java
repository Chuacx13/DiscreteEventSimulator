import java.util.function.Supplier;

class Server {
    private final int id;
    private final double freeTime;
    private final int qmax;
    private final int originalQueue;
    private final Supplier<Double> restTime;
    private static final int DEFAULTTIME = 0;
    private final boolean resting;
    private static final boolean RESTING = false;
    private final int numOfServers;

    Server(int id, double freeTime, int qmax, int originalQueue, 
            Supplier<Double> restTime, boolean resting, int numOfServers) {
        this.id = id;
        this.freeTime = freeTime;
        this.qmax = qmax;
        this.originalQueue = originalQueue;
        this.restTime = restTime;
        this.resting = resting;
        this.numOfServers = numOfServers;
    }

    Server(int id, double freeTime, int qmax, int originalQueue, 
            Supplier<Double> restTime, int numOfServers) {
        this.id = id;
        this.freeTime = freeTime;
        this.qmax = qmax;
        this.originalQueue = originalQueue;
        this.restTime = restTime;
        this.resting = RESTING;
        this.numOfServers = numOfServers;
    }

    Server(int id, int qmax, int originalQueue, 
            Supplier<Double> restTime, int numOfServers) {
        this.id = id;
        this.freeTime = DEFAULTTIME;
        this.qmax = qmax;
        this.originalQueue = originalQueue;
        this.restTime = restTime;
        this.resting = RESTING;
        this.numOfServers = numOfServers;
    }

    public int getNumOfServers() {
        return this.numOfServers;
    }

    public boolean getResting() {
        return this.resting;
    }

    public Supplier<Double> getRestTime() {
        return this.restTime;
    }

    public double getRest() {
        if (this.id > this.numOfServers) {
            return DEFAULTTIME;
        } else {
            return this.restTime.get();
        }
    }

    public double getFreeTime() {
        return this.freeTime;    
    }

    public int getId() {
        return this.id;
    }

    public int getQueue() {
        return this.qmax;
    }

    public int getOriginalQueue() {
        return this.originalQueue;
    }

    @Override
    public String toString() {
        if (this.numOfServers >= this.id) {
            return String.format("%d", this.id);
        } else {
            return String.format("self-check %d", this.id);
        }
    }
}
