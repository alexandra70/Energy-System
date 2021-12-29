package strategies;

import database.DistributorData;
import database.ProducerData;
import java.util.ArrayList;

/**
 * Interface for the strategies.
 */
public interface Strategy {

    /**
     * This method must be implemented by all the classes the implements this.
     * @param distributorData The distributor that want producers.
     * @param producerData Producers that can be selected in strategy process.
     */
    void execute(DistributorData distributorData, ArrayList<ProducerData> producerData);

}
