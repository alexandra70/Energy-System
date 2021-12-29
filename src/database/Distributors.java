package database;

import strategies.DistributorStrategy;
import input.DistributorChanges;
import input.Distributor;

import java.util.ArrayList;
import java.util.List;

/**
 * Distributors Class represents an object where i will create
 * the DistributorsData objects and process this data
 */

public class Distributors {

    private ArrayList<DistributorData> distributorsData = new ArrayList<>();

    public Distributors() { }

    /**
     * @return Get the distributors arrayList that contains all needed data.
     */
    public ArrayList<DistributorData> getDistributors() {
        return distributorsData;
    }

    /**
     * Get from INPUT-DISTRIBUTOR(this is the first object that get all data given in input-file
     * and take the final list of them - this will be final because at monthly updates i will no
     * longer get another one. But there will be updates for ones that are already in list.
     * Create DistributorData-Objects form Distributor list i received
     * @param distributors the received distributors
     */
    public void makeDistributorsList(final List<Distributor> distributors) {
        for (Distributor distributor : distributors) {
            //create object - distributor, then add it to the list-distributorsData
            DistributorData distributorData = new DistributorData(distributor.getId(),
                    distributor.getContractLength(), distributor.getInitialBudget(),
                    distributor.getInitialInfrastructureCost(),
                    distributor.getEnergyNeededKW(), distributor.getProducerStrategy());
            this.distributorsData.add(distributorData);

        }
    }

    /**
     * Each distributor will pay the fees. Those fees will reduces his total budget.
     */
    public void payTaxes() {
        for (DistributorData distributor : this.distributorsData) {
            distributor.payTaxes();
        }
    }

    /**
     * Update each distributor given in the list.
     * @param costChanges the list with changes - the productions cost and infrastructure cost
     * @param producerData producers list that has to be updated(each) if the changes
     *                     from cotChanges says to do so.
     */
    public void updateDistributors(final List<DistributorChanges> costChanges,
                                   final List<ProducerData> producerData) {
        DistributorStrategy distributorStrategy
                = new DistributorStrategy((ArrayList<ProducerData>) producerData);
        for (DistributorChanges costChanges1 : costChanges) {
            //find the distributor, update only if, obviously, it exists
            DistributorData distributorData = getDistributorAtIdx(costChanges1.getId());
            if (distributorData.isBankrupt()) {
                continue;
            }
            if (distributorData.getProducers().size() == 1) {
                if (distributorData.getEnergyNeededKW()
                        <= distributorData.getProducers().get(0).getEnergyPerDistributor()) {
                    distributorData.setInfrastructureCost(costChanges1.getInfrastructureCost());
                    distributorData.setProductionCost();
                    continue;
                }
            }
            distributorData.setInfrastructureCost(costChanges1.getInfrastructureCost());
            distributorData.resetProducers();
            distributorStrategy.subscribeToProducers(distributorData);
            distributorData.setProductionCost();

        }
        //go trough all and actualize their prices
        initialUpdatePrices();
    }

    /**
     * @param producerData Update the observers of this producerData.
     */
    public void updateDistributorsProduction(final List<ProducerData> producerData) {

        DistributorStrategy distributorStrategy
                = new DistributorStrategy((ArrayList<ProducerData>) producerData);

        for (DistributorData distributorData : this.distributorsData) {
            if (distributorData.getReapplyStrategy() && !distributorData.isBankrupt()) {
                //then reapply the strategy
                distributorStrategy.subscribeToProducers(distributorData);
                //will be updated at the list of producers from there.
                distributorData.setProductionCost();
                distributorData.setReapplyStrategy(false); //do not reapply this UNTIL THE
                //OBSERVABLE SAYS TO DO THAT - THAT MEANS IT STAYS FALSE UNTIL UPDATE IS CALLED.
            }
        }
    }

    /**
     * This method will go trough all distributors and calculate
     * put them to calculate their prices
     */
    public void initialUpdatePrices() {
        for (DistributorData distributorData : this.distributorsData) {
            distributorData.calculateContractPrice();
        }
    }

    /**
     * @param idx the index of wanted-distributor-object
     * @return the -specific- distributor i want
     */
    public DistributorData getDistributorAtIdx(final int idx) {
        if (this.distributorsData.get(idx) == null) {
            return null;
        }
        return this.distributorsData.get(idx);
    }

    /**
     * This method will return us the first min-price distributor that is not bankrupt.
     * @return will return us the first min-price distributor
     */
    public DistributorData minPaymentDistributor() {

        long min = utils.Constants.BIG_NUMBER;
        int idx = -1;
        for (DistributorData distributorData : this.distributorsData) {
            if (min > distributorData.getContractPrice() && !distributorData.isBankrupt()) {
                min = distributorData.getContractPrice();
                idx = distributorData.getId();
            }
        }
        if (idx != -1) {
            return distributorsData.get(idx);
        }
        return null;
    }

}
