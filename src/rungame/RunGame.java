package rungame;

import database.PayContract;
import database.Consumers;
import database.Distributors;
import database.Producers;

import input.InputData;
import input.MonthlyUpdates;


/**
 * Run the game using a object of this type.
 * This will run the initial round, next will run the rest of them.
 */

public class RunGame {

    /**
     * Run the first turn. At the end of it the Consumers and Distributors data
     * will be updated.
     * @param inputData form this object i will get the monthly updates
     * @param distributors from this object i will get the distributor data and their state
     * @param consumers from this object i will get the consumers data and their state
     * @param producers from this object i will get the producers data and their state
     */
    public void runInitialRound(final InputData inputData, final Distributors distributors,
                                final Consumers consumers, final Producers producers) {

        //update prices of the distributors, all are zeros so i have to update the cost using
        //the cost of infrastructure and the cost of production then, next step after that
        distributors.updateDistributorsProduction(producers.getProducerData());
        distributors.initialUpdatePrices();

        //the consumers have to chose a contract, the min-cost from the lists of distributors;
        consumers.consumersContract(inputData.getInitialData().getConsumers(), distributors);

        //consumers get their monthly payment
        consumers.payMonthlyIncome();

        //class that calculate the payment - OBJECT TO THAT CLASS
        PayContract payContract = new PayContract();

        //the consumers have to pay to the distributor they have chosen before te price of contract
        payContract.payAllMonthly(consumers.getConsumers());

        //distributors pay their fees
        distributors.payTaxes();

    }

    /**
     * Run the numberOfTurns turns.
     * @param inputData form this object i will get the monthly updates
     * @param distributors from this object i will get the distributor data and their state
     * @param consumers from this object i will get the consumers data and their state
     * @param producers from this object i will get the producers data and their state
     */
    public void runGame(final InputData inputData, final Distributors distributors,
                        final Consumers consumers, final Producers producers) {

        PayContract payContract = new PayContract();
        for (MonthlyUpdates monthlyUpdates : inputData.getMonthlyUpdates()) {

            //update distributors
            distributors.updateDistributors(monthlyUpdates.distributorChanges(),
                    producers.getProducerData());

            //first of all have to updated the list of consumers in
            //case it has new ones it will be modified
            consumers.addNewConsumers(monthlyUpdates.getNewConsumers(), distributors);

            //pay the consumers, will e included the new ones too
            consumers.payMonthlyIncome();

            //search for distributors, find the min-price one and then make the object contract
            //to correspond with the new data about the new price, new length of contract,
            //set the consumer if is not init from before - and then add the contract to the
            //distributor list of contracts, in this way the data will be processed correctly.
            consumers.searchAndSingDistributor(distributors);

            //put consumers to pay the contract (Consumers is an object defined by a
            // list of consumers of type DataConsumers
            payContract.payAllMonthly(consumers.getConsumers());

            //distributor pay
            distributors.payTaxes();

            //update producers
            producers.updateProducers(monthlyUpdates.getProducerChanges());

            //inform distributors about the changes in the producers objects.
            distributors.updateDistributorsProduction(producers.getProducerData());

            //update data about monthly stats - the list of distributors - in the
            //producers database.
            producers.monthlyUpdate();

        }
    }
}
