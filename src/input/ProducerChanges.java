package input;

/**
 * Class that accumulate the info from json about
 * monthly updates of each producer about their energy that
 * can provide the distributors with.
 */
public class ProducerChanges {

    private int id;
    private double energyPerDistributor;

    public ProducerChanges() {
    }

    public ProducerChanges(int id, double energyPerDistributor) {
        this.id = id;
        this.energyPerDistributor = energyPerDistributor;
    }

    /**
     * @return Get the modify energy of this producer.
     */
    public double getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    /**
     * @return Get the id.
     */
    public int getId() {
        return id;
    }
}
