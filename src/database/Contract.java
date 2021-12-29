package database;

/**
 * The contract will be made between a consumer and a distributor.
 * Class that will provide me the information about consumers and
 * the distributor it has.
 */
public class Contract {

    private ConsumerData consumerData = null;
    private int remainedContractMonths;
    private long price;
    private boolean valid;
    //any contract that will be made will be valid, the stat
    //of invalid will be a result of a specific action

    /**
     * @param monthsLeft Set the months.
     * @param price Set the initial price.
     */
    public Contract(final int monthsLeft, final long price) {
        this.remainedContractMonths = monthsLeft;
        this.price = price;
        this.valid = true;
    }

    /**
     * @return Get the id of the owner of this contract.
     */
    public final int getConsumerId() {
        return this.consumerData.getId();
    }

    /**
     * @param price Set the price of the contract. Take the price given by distributor.
     */
    public void setPrice(final long price) {
        this.price = price;
    }

    /**
     * @return Get the price of ths contract. (Current price).
     */
    public final long getPrice() {
        return this.price;
    }

    /**
     * Set the actual state of the contact.
     * @param valid is the contract valid - true or it is not - flase.
     */
    public void setValid(final boolean valid) {
        this.valid = valid;
    }

    /**
     * @return Get the consumer - the owner of this contract.
     */
    public final ConsumerData getConsumerData() {
        return consumerData;
    }

    /**
     * @param consumerData Set for this contract the owner.
     */
    public void setConsumerData(final ConsumerData consumerData) {
        this.consumerData = consumerData;
    }

    /**
     * @param mothsLeft Set for this contract the number of months - this will take the
     *                  the months given by the distributor-object.
     */
    public void setMothsLeft(final int mothsLeft) {
        this.remainedContractMonths = mothsLeft;
    }

    /**
     * @return Get the remaining contract months.
     */
    public final int getRemainedContractMonths() {
        return remainedContractMonths;
    }

}
