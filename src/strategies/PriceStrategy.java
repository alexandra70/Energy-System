package strategies;

import database.DistributorData;
import database.ProducerData;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Select the producers for distributor - main purpose.
 * Using PriceStrategy strategy.
 */

public class PriceStrategy implements Strategy {

    public PriceStrategy() {
    }

    /**
     * This method will be called by the execute method.
     * It allows me to select the producers as desired.
     * @param distributorData The distributor that wants producers.
     * @param producerData The producers list.
     */
    public void priceStrategy(final DistributorData distributorData,
                              final ArrayList<ProducerData> producerData) {
        ArrayList<ProducerData> finalChoose = new ArrayList<ProducerData>();

        for (ProducerData producer : producerData) {
            ProducerData p = new ProducerData(producer.getId(),
                    producer.getEnergyType().getLabel(), producer.getMaxDistributors(),
                    producer.getPriceKW(), producer.getEnergyPerDistributor());
            finalChoose.add(p);
        }

        finalChoose.sort(new Comparator<ProducerData>() {
            @Override
            public int compare(ProducerData o1, ProducerData o2) {
                if (Double.compare(o2.getPriceKW(), o1.getPriceKW()) == 0) {
                    if (Double.compare(o2.getEnergyPerDistributor(),
                            o1.getEnergyPerDistributor()) == 0) {
                        return o1.getId() - o2.getId();
                    }
                    return Double.compare(o2.getEnergyPerDistributor(),
                            o1.getEnergyPerDistributor());
                }
                return Double.compare(o1.getPriceKW(), o2.getPriceKW());
            }
        });

        double energy = 0;

        for (ProducerData producer : finalChoose) {
            if (distributorData.getEnergyNeededKW() <= energy) {
                break;
            }
            ProducerData realProducerData = null;
            for (ProducerData producerData1 : producerData) {
                if (producerData1.getId() == producer.getId()) {
                    realProducerData = producerData1;
                }
            }
            if (realProducerData == null) {
                continue;
            }
            if (realProducerData.getMaxDistributors() > realProducerData.getNrDistributors()) {
                distributorData.getProducers().add(realProducerData);
                realProducerData.addObservers(distributorData);
                energy += producer.getEnergyPerDistributor();
            }
        }
    }

    /**
     * @param distributorData The distributor that want producers.
     * @param producerData Producers that can be selected in strategy process.
     */
    @Override
    public void execute(DistributorData distributorData, ArrayList<ProducerData> producerData) {
        this.priceStrategy(distributorData, producerData);
    }
}
