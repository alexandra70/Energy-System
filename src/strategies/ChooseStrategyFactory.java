package strategies;

import database.DistributorData;
import database.ProducerData;

import java.util.ArrayList;

/**
 * Choose the strategy based on the strategyType of the distributor received.
 */

public class ChooseStrategyFactory {
    /**
     * See what strategy type the distributor needs to apply then pass it
     * an instance of that strategy and proceed with the process of choosing.
     * @param distributorData The one that want to subscribe.
     * @param producerData Choose form this list producers.
     * @return The strategy the distributor data has to apply.
     */
    public Strategy chooseStrategyFactory(DistributorData distributorData,
                                          ArrayList<ProducerData> producerData) {
        if (distributorData.getProducerStrategy().getLabel().equals("GREEN")) {
            return new GreenStrategy();
        }
        if (distributorData.getProducerStrategy().getLabel().equals("PRICE")) {
            return new PriceStrategy();
        }
        if (distributorData.getProducerStrategy().getLabel().equals("QUANTITY")) {
            return new QuantityStrategy();
        }
        System.out.println("INVALID STRATEGY-REQUIREMENT");
        return null;
    }






}
