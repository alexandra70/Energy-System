package database;

import input.Producer;
import input.ProducerChanges;

import java.util.ArrayList;
import java.util.List;

/**
 * Collect all the ProducerData in this object.
 */

public class Producers {

    private ArrayList<ProducerData> producerData = new ArrayList<ProducerData>();
    private int month = 1; // month 1. Start from there.

    public Producers() {
    }

    /**
     * @return get the list of producers.
     */
    public ArrayList<ProducerData> getProducerData() {
        return producerData;
    }

    /**
     * @param producers List of producers that i will transfer in another object very
     *                  similar to this one, but i decided to simplify my process by
     *                  making another class ProducerData where i can add more fields
     *                  in order to process my data more efficiently for me. So i copy
     *                  what i needed for this in my ProducerData and then collect them in
     *                  this object Producers that sore all my ProducerData.
     */

    public void makeProducers(final List<Producer> producers) {
        for (Producer producer : producers) {
            ProducerData producerData1 = new ProducerData(producer.getId(),
                    producer.getEnergyType(), producer.getMaxDistributors(),
                    producer.getPriceKW(), producer.getEnergyPerDistributor());
            this.producerData.add(producerData1);
        }
    }

    /**
     * This method get at each month ProducerChanges and applied them on the
     * producers data.
     * @param producerChanges the list of changes.
     */
    public void updateProducers(final List<ProducerChanges> producerChanges) {

        if (producerChanges.size() == 0) {
            return;
        }
        for (ProducerChanges change : producerChanges) {
            //find that one with that id and make its updates
            ProducerData producerData1 = this.producerData.get(change.getId());
            //i assume that will be for sure this producer.
            producerData1.setEnergyPerDistributor(change.getEnergyPerDistributor());
            this.notifyDistributorsOfProducer(producerData1);
        }
    }

    /**
     * Update at each month the list of distributors that are placed in
     * a hashmap. - But tell them to update for this month.
     */
    public void monthlyUpdate() {
        for (ProducerData producerData1 : this.producerData) {
            producerData1.updateMonthlyStats(this.month);
        }
        this.month++;
    }

    /**
     * The observers were stored in observers list
     * so now after some changes occurred this have
     * to be notify about those changes in the structure
     * @param producer This producer data were modify
     *                     so in this order i have to notify
     *                     its observers.
     */
    public void notifyDistributorsOfProducer(ProducerData producer) {
        producer.updateObservers();
    }
}
