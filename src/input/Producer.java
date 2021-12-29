package input;

/**
 * Class used to import the data from the json file in my project.
 */
public class Producer {

    private int id;
    private String energyType;
    private int maxDistributors;
    private double priceKW;
    private double energyPerDistributor;

    /**
     * Constructor for json.
     */
    public Producer() {
    }

    /**
     * @param id Set the id.
     * @param energyType et the energyType.
     * @param maxDistributors Set the maxDistributors.
     * @param priceKW Set the price.
     * @param energyPerDistributor Set the energy.
     */
    public Producer(final int id, String energyType, final int maxDistributors,
                    final double priceKW, final double energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
    }

    /**
     * @return Get the id of the current producer.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Get the Energy of the current producer.
     */
    public double getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    /**
     * @return Get the Price in KW of the current producer.
     */
    public double getPriceKW() {
        return priceKW;
    }

    /**
     * @return Get MaxDistributors of the current producer.
     */
    public int getMaxDistributors() {
        return maxDistributors;
    }

    /**
     * @return Get the energyType of the current producer.
     */
    public String getEnergyType() {
        return energyType;
    }
}
