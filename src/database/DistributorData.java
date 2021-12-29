package database;

import strategies.EnergyChoiceStrategyType;
import utils.Constants;

import java.util.*;

/**
 * This cass main purpose is to update the data of the distributorData
 * by doing specific actions
 */

public class DistributorData implements Observer {

    private int id;
    private int contractLength;
    private long budget;
    private long infrastructureCost;
    private double energyNeededKW; // those will be received when the object will be made
    private EnergyChoiceStrategyType producerStrategy;
    private long contractPrice = 0;
    private long productionCost;
    private boolean isBankrupt = false;
    private ArrayList<Contract> contracts = new ArrayList<>();
    private ArrayList<ProducerData> producers = new ArrayList<>();
    private Boolean reapplyStrategy; //initial this have to be true

    //constructor construct with the initial data from distributor.class
    public DistributorData(final int id, final int contractLength, final int budget,
                           final long infrastructureCost, final double energyNeededKW,
                           final String producerStrategy) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = budget;
        this.infrastructureCost = infrastructureCost;
        this.energyNeededKW = energyNeededKW;
        // this is learned from lab-basic-strategy-step-count.
        this.producerStrategy = EnergyChoiceStrategyType.strategyType(producerStrategy);
        this.reapplyStrategy = true;
    }

    /**
     * @return Get the list of producers.
     */
    public ArrayList<ProducerData> getProducers() {
        return producers;
    }

    /**
     * Reinitialize the producer list of this distributor.
     */
    public void resetProducers() {
        this.producers = new ArrayList<>();

    }

    /**
     * @return this tells if is the case to really the strategy.
     */
    public Boolean getReapplyStrategy() {
        return this.reapplyStrategy;
    }

    /**
     * @param reapplyStrategy set if is the case to reapply the strategy.
     */
    public void setReapplyStrategy(Boolean reapplyStrategy) {
        this.reapplyStrategy = reapplyStrategy;
    }

    /**
     * @return Get the energy this producer wants to get from the producer.
     */
    public double getEnergyNeededKW() {
        return energyNeededKW;
    }

    /**
     * @return Get the strategy this producer requires for its necessities.
     */
    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    /**
     * At each month this will be set at new infrastructureCost cost, only if the list
     * of CostChanges tell this object to do so.
     * @param infrastructureCost the new infrastructureCost cost - or initial one.
     */
    public void setInfrastructureCost(final long infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    /**
     * @param contract add a new contract in its list.
     */
    public void addContract(final Contract contract) {
        this.contracts.add(contract);
    }

    /**
     * @return get contractLen.
     */
    public int getContractLength() {
        return this.contractLength;
    }

    /**
     * @return Get all contracts at the moment.
     */
    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    /**
     * @param contracts Set contracts this is used to remove contract.
     */
    public void setContracts(final ArrayList<Contract> contracts) {
        this.contracts = contracts;
    }

    /**
     * @return Get the id of this distributor.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return Get the budget of this distributor.
     */
    public long getBudget() {
        return budget;
    }

    /**
     * @return Get the stata of this distributor.
     */
    public boolean isBankrupt() {
        return isBankrupt;
    }

    /**
     * This will be set at true - is bankrupt only if he will no longer
     * can pay his debts.
     * @param bankrupt Set the actual distributorData at a bank-state.
     */
    public void setBankrupt(final boolean bankrupt) {
        this.isBankrupt = bankrupt;
    }

    /**Set the production cost after the distributor choose the producer
     * and producer giver him the data to process it. The setProductionCost will
     * be updated only when the distributors list will be updated
     */
    public void setProductionCost() {
        double cost = 0.0;
        for (ProducerData producerData : this.producers) {
            cost += (producerData.getEnergyPerDistributor()
                        * producerData.getPriceKW());
        }

        this.productionCost = Math.round(Math.floor(cost / Constants.TEN));
    }

    /**
     * Distributor calculate the contract price.
     */
    public void calculateContractPrice() {
        //just actualize the data just to can make
        // a correct price for followings operations
        this.eliminateBankruptConsumers();
        if (this.contracts.size() == 0) {
            this.contractPrice = this.infrastructureCost + this.productionCost + this.getProfit();
            actualizeContracts();
            return;
        }
        this.contractPrice = Math.round(this.infrastructureCost / this.contracts.size())
                + this.productionCost + this.getProfit();
        this.actualizeContracts();
    }

    /**
     * @return Generate the profit at the end of the month.
     */
    public long getProfit() {
        return Math.round(Math.floor(Constants.PERCENT * this.productionCost));
    }

    /**
     * This - Distributor have to sub the tax form
     */
    public void payTaxes() {
        if (this.isBankrupt()) {
            return;
        }
        this.budget = this.budget - (this.infrastructureCost
                + this.contracts.size() * this.productionCost);

        if (this.budget < 0) {
            this.setBankrupt(true);
        }
        this.eliminateBankruptConsumers();
    }

    /**
     * Go through the list of consumers and update them.
     */
    public void actualizeContracts() {
        List<Contract> elemToEliminate = new ArrayList<>();
        for (Contract contract : this.contracts) {
            if (contract.getRemainedContractMonths() == 0
                    || contract.getConsumerData().isBankrupt()) {
                elemToEliminate.add(contract);
            }
        }
        this.contracts.removeAll(elemToEliminate);
    }

    /**
     * Go through the list of consumers and eliminate
     * the bankrupt ones.
     */
    public void eliminateBankruptConsumers() {
        List<Contract> elemToEliminate = new ArrayList<>();

        for (Contract contract : this.contracts) {
            if (contract.getConsumerData().isBankrupt()) {
                elemToEliminate.add(contract);
            }
        }
        this.contracts.removeAll(elemToEliminate);
    }

    /**
     * @return Get the price this distributor ask for - at the moment(this month).
     */
    public long getContractPrice() {
        return this.contractPrice;
    }

    /**
     * Collect the payment from consumers.
     *
     * @param payment the payment from consumer
     */
    public void increaseBudget(final long payment) {
        this.budget += payment;
    }


    /**
     * When i reach this method i surely know that i
     * have to find another set of producers.
     * @param o the observable object
     * @param arg hte price
     */
    @Override
    public void update(Observable o, Object arg) {
        this.reapplyStrategy = true;
        // reset the list of producers
        // (anyways, i will make new assignments)
        this.resetProducers();
    }
}
