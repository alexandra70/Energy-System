package database;

import java.util.ArrayList;

/**
 * This class will make the consumer pay the bill and add
 * this is more like a command put all consumers pay the bill, like i said before i still have
 * no idea where this program will and direction this project will go have to make the tests pass.
 */
public class PayContract {

    /**
     * For each consumer i want to pay the debt to the distributor
     * get income at every new month , this method will
     * be called only when we want to get payment, to pay or
     * to exclude the ones that cant pay anymore
     * @param consumersData - the list of consumers that have to pay
     */
    public void payAllMonthly(final ArrayList<ConsumerData> consumersData) {
        for (ConsumerData consumerData : consumersData) {
            if (consumerData.isBankrupt()) {
                continue;
            }
            consumerData.payPrice();
        }
    }
}
