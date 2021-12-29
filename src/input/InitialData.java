package input;

import java.util.List;

/**
 * InitialData - will be the object that take the initial input, and
 * this will have two fields consumers and distributors - that will
 * provide those types of elements - when i will need them
 * this will be useful at construction of the InputData
 * is more like a composite element of this -
 */
public class InitialData {

    private List<Consumer> consumers;
    private List<Distributor> distributors;
    private List<Producer> producers;

    public InitialData() {

    }

    /**
     * Used to take the data from json. All are the initial ones.
     * @param consumers consumers from json.
     * @param distributors distributor from json.
     * @param producers and producers from json.
     */
    public InitialData(final List<Consumer> consumers,
                       final List<Distributor> distributors, final List<Producer> producers) {
        this.consumers = consumers;
        this.distributors = distributors;
        this.producers = producers;
    }

    /**
     * @return a list of initial consumers.
     */
    public List<Consumer> getConsumers() {
        return this.consumers;
    }

    /**
     * @return a list of initial distributors.
     */
    public List<Distributor> getDistributors() {
        return this.distributors;
    }

    /**
     * @return a list of initial producers.
     */
    public List<Producer> getProducers() {
        return this.producers;
    }

}
