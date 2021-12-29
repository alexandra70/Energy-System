package input;

import java.util.List;

/**
 * Contains information - updates of the producers on each month.
 * The same goes for consumers - they will be new ones.
 */
public class MonthlyUpdates {

    private List<Consumer> newConsumers;
    private List<DistributorChanges> distributorChanges;
    private List<ProducerChanges> producerChanges;

    public MonthlyUpdates() { }

    /**
     * @param consumers list of the new consumers and the list of
     * @param distributorChanges the new costs for producers;
     */
    public MonthlyUpdates(final List<Consumer> consumers, final List<DistributorChanges>
            distributorChanges, final List<ProducerChanges> producerChanges) {
        this.newConsumers = consumers;
        this.distributorChanges = distributorChanges;
        this.producerChanges = producerChanges;
    }

    /**
     * @return The list of changes for this month - of all producers.
     */
    public List<ProducerChanges> getProducerChanges() {
        return this.producerChanges;
    }

    /**
     * @return The list of changes for this month - of all distributors.
     */
    public final List<DistributorChanges> distributorChanges() {
        return distributorChanges;
    }

    /**
     * Set the changes - information about distributors updates - changes in infrastructure
     * production costs of a specific distributor.
     * @param distributorChanges The new changes.
     */
    public void setDistributorChanges(final List<DistributorChanges> distributorChanges) {
        this.distributorChanges = distributorChanges;
    }

    /**
     * @return Get the new consumers for this month.
     */
    public final List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    /**
     * Add new consumers in the list of ConsumersData List.
     * @param newConsumers the new ones.
     */
    public void setNewConsumers(final List<Consumer> newConsumers) {
        this.newConsumers = newConsumers;
    }

}
