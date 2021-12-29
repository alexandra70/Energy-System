package database;

import input.Consumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Main functionality of this is - set consumers contract at the one that have the minimum cost.
 * Those consumers were gotten/obtained from input class the one used to extract data from json
 */

public class Consumers {

    private ArrayList<ConsumerData> consumerData = new ArrayList<>();

    public Consumers() {
    }

    /**
     * Initial consumers that wants to obtain a contract.
     *
     * @param consumerList the initial list of consumers that wants to look for a contract.
     * @param distributors the list that will allow me to find the min-one price contract.
     */
    public void consumersContract(final List<Consumer> consumerList,
                                  final Distributors distributors) {
        DistributorData distributorData = distributors.minPaymentDistributor();
        if (distributorData == null) {
            return;
        }

        for (Consumer consumer : consumerList) {
            ConsumerData consumerData1 = new ConsumerData(consumer.getId(),
                    consumer.getMonthlyIncome(), consumer.getInitialBudget());

            Contract contract = new Contract(distributorData.getContractLength(),
                    distributorData.getContractPrice());

            contract.setConsumerData(consumerData1);
            contract.setValid(true);
            contract.setPrice(distributorData.getContractPrice());

            consumerData1.setDistributorData(distributorData);
            consumerData1.setContract(contract);

            distributorData.addContract(contract);

            this.consumerData.add(consumerData1);
        }
    }

    /**
     * This method will receive a list of new consumers, we assume that they can be not new,
     * so i decided to make sure i will not have problems.
     *
     * @param consumerList the list of new consumers that wants to be added
     * @param distributors the list all of distributors - that will provide me the min-cost one
     */
    public void addNewConsumers(final List<Consumer> consumerList,
                                final Distributors distributors) {
        if (consumerList.size() == 0) {
            return;
        }
        //list where to add the ones that are not find in both lists
        List<Consumer> consumers = new ArrayList<>();
        //else see if one of them is already in the list
        for (Consumer consumer : consumerList) {
            int exists = 0;
            for (ConsumerData consumerData1 : this.consumerData) {
                if (consumer.getId() == consumerData1.getId()) {
                    exists = 1;
                }
            }
            if (exists == 0) {
                consumers.add(consumer);
            }
        }
        //now add them in the final list using the method - consumersContract
        this.consumersContract(consumers, distributors);
    }

    /**
     * @return Get the consumers, all the them a the moment. List of consumers can always be
     * extended.
     */
    public ArrayList<ConsumerData> getConsumers() {
        return this.consumerData;
    }

    /**
     * Go trough all consumers and give them the monthly income.
     */
    public void payMonthlyIncome() {
        for (ConsumerData consumerData1 : this.consumerData) {
            if (consumerData1.isBankrupt()) {
                continue;
            }
            consumerData1.monthlyPayment();
        }
    }

    /**
     * Search the minimum distributor price and them sign with this.
     *
     * @param distributors list of distributors that will provide the min-cost one
     */
    public void searchAndSingDistributor(final Distributors distributors) {
        //find that one - the one that have the min-price-request and sign with it
        DistributorData distributorData = distributors.minPaymentDistributor();

        if (distributorData == null) {
            return;
        }

        for (ConsumerData consumerData1 : this.consumerData) {
            //the consumer will try to find a contract only of
            //1.does not have any months left in the current contract
            //and it have to be valid of paying - so it is not bankrupt
            //2.the distributor is no longer valid - bankrupt;
            if (consumerData1.getContract().getRemainedContractMonths() == 0
                    && !consumerData1.isBankrupt()
                    || consumerData1.getDistributorData().isBankrupt()) {
                //now sign with this distributor
                consumerData1.singContract(distributorData);
            }
        }
    }
}
