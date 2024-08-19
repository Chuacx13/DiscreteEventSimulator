class Stats {

    private final int numOfServed;
    private final int numOfLeave;
    private final int numOfWait;
    private final double totalWaitingTime;

    Stats(int numOfServed, int numOfLeave, int numOfWait, double totalWaitingTime) {
        this.numOfServed = numOfServed;
        this.numOfLeave = numOfLeave;
        this.numOfWait = numOfWait;
        this.totalWaitingTime = totalWaitingTime;
    }

    int getServed() {
        return this.numOfServed;
    }

    int getLeave() {
        return this.numOfLeave;
    }

    int getWait() {
        return this.numOfWait;
    }

    double getWaitTime() {
        return this.totalWaitingTime;
    }

    @Override 
    public String toString() {
        if (this.numOfWait != 0) {
            double averageWaitTime = this.totalWaitingTime / this.numOfServed; 
            return String.format("[%.3f %d %d]", 
                    averageWaitTime, this.numOfServed, this.numOfLeave);
        } else {
            return String.format("[0.000 %d %d]", this.numOfServed, this.numOfLeave);
        }
    }
}
