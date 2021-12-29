package input;

/**
 * Class that contains all the information that has been read - the ones that refers
 * to the distributor are transferred in a object like this. Form json input-file.
 */

public class Distributor {
    private int id;
    private int contractLength;
    private int initialBudget;
    private int initialInfrastructureCost;
    private double energyNeededKW;
    private String producerStrategy;

    public Distributor() {
    }

    public Distributor(final int id, final int contractLength, final int initialBudget,
                       final int initialInfrastructureCost, final double energyNeededKW,
                       final String producerStrategy) {
        this.id = id;
        this.contractLength = contractLength;
        this.initialBudget = initialBudget;
        this.initialInfrastructureCost = initialInfrastructureCost;
        this.energyNeededKW = energyNeededKW;
        this.producerStrategy = producerStrategy;
    }

    /**
     * @return Get id of this distributor.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Get contractLength of this distributor.
     */
    public int getContractLength() {
        return contractLength;
    }

    /**
     * @return Get initialBudget of this distributor.
     */
    public int getInitialBudget() {
        return initialBudget;
    }

    /**
     * @return Get initialInfrastructureCost of this distributor.
     */
    public int getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    /**
     * @return Get the strategy of the producer.
     */
    public String getProducerStrategy() {
        return producerStrategy;
    }

    /**
     * @return Get the amount of energy this producer requires.
     */
    public double getEnergyNeededKW() {
        return energyNeededKW;
    }
}
