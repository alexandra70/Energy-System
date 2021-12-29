package strategies;

import database.DistributorData;
import database.ProducerData;

import java.util.ArrayList;

/**
 * This class main purpose is to apply in the distributor that is received
 * the strategy - that means i want to updated his list of producers
 * i want to iterate tough producer list and that as many as i need
 * to cover the need of this distributor - his need is COVER HIS QUANTITY OF
 * ENERGY HE NEEDS - in this sense i have to go in the desired order and
 * take them in producers list until i exceed the desired quantity.
 */

public class DistributorStrategy {

    private ArrayList<ProducerData> producerData = new ArrayList<ProducerData>();
    //THIS CALL THE EXECUTE METHOD TO BE SPECIFICALLY EXECUTED ON
    //WHAT I REALLY WANT. - > strategyObject;
    private final ChooseStrategyFactory strategyObject;

    public DistributorStrategy(final ArrayList<ProducerData> producerData) {
        this.producerData = producerData;
        this.strategyObject = new ChooseStrategyFactory();
    }

    /**
     * This method helps me to remove the distributor from observer
     * list of each producer.
     * @param distributorData the distributor that has to be removed.
     */
    public void removeDistributor(final DistributorData distributorData) {
        for (ProducerData producerData1 : this.producerData) {
            if (producerData1.getObservers().contains(distributorData)) {
                producerData1.removeObserver(distributorData);
            }
        }
    }

    /**
     * Subscribe - find for distributor the producers that meets the requirements
     * (the strategy).
     * @param distributorData The distributor that wants to find producers to subscribe to.
     */
    public void subscribeToProducers(DistributorData distributorData) {

        //for distributor that has to be updated collect th stategy
        Strategy strategy = this.strategyObject.chooseStrategyFactory(distributorData,
                this.producerData);
        /**
         * IN ANY CONTEXT THIS WILL BE EXECUTED OF EACH DISTRIBUTOR DATA THAT WANTS TO
         * DO THAT - IT SAYS THIS - TAKE ME - DISTRIBUTOR - AND YOU HAVE THE LIST OF PRODUCERS
         * SO YOU CAN DO THINGS FOR ME - LIKE CLEAN ME FROM MY DISTRIBUTORS - GIVE ME OTHERS
         * AND MAKE ME HAPPY - WITH MY STRATEGY ------- THIS REPRESENT ONE DISTRIBUTOR THAT
         * HAS BEEN UPDATED BEFORE AND NEED OTHERS TO PROCEEDS WITH HIS ACTIONS
         */
        //then remove him from another list of observers
        this.removeDistributor(distributorData);
        // then add them to others lists of observers, and
        // accept to be added as observer in theirs lists
        strategy.execute(distributorData, this.producerData);
    }


}
