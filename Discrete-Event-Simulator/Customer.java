import java.util.function.Supplier;

class Customer {
    private final int id;
    private final double arrivalTime;
    private final Supplier<Double> serviceTime;

    Customer(int id, double arrivalTime, Supplier<Double> serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }
    
    public double getArrivalTime() { 
        return this.arrivalTime;
    }
    
    public double getServiceTime() { 
        return this.serviceTime.get();
    }

    public double getEndServiceTime() {
        return this.arrivalTime + this.serviceTime.get();
    }

    public Supplier<Double> getServiceLength() {
        return this.serviceTime;
    }
    
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.format("%.3f customer %d", this.arrivalTime, this.id);
    }
}
