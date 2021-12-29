package database;

import utils.Constants;

/**
 * This is the object-consumer i will change it during the execution of the program.
 */
public class ConsumerData {

    private final int id;
    private final int monthlyIncome;
    private long actualBudget;
    private boolean isBankrupt = false;
    private Contract contract;
    private DistributorData distributorData = null;
    private DistributorData oldDistributor = null;
    private int monthPassedUnpaid = 0;
    private long monthPassedDebt = 0;

    public ConsumerData(final int id, final int monthlyIncome, final int actualBudget) {
        this.id = id;
        this.monthlyIncome = monthlyIncome;
        this.actualBudget = actualBudget;
        this.contract = null;
    }

    /**
     * @return Get monthly income of this consumerData.
     */
    public int getMonthlyIncome() {
        return this.monthlyIncome;
    }

    /**
     * Set the contract of the consumer at this one.
     * @param contract The contract i want to set.
     */
    public void setContract(final Contract contract) {
        this.contract = contract;
    }

    /**
     * Increase the actual budget of this consumer with his monthly income. (Get paid).
     */
    public void monthlyPayment() {
        this.actualBudget += monthlyIncome;
    }

    /**
     * @return Get id of this consumerData.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return Get actual state of this consumerData.
     */
    public boolean isBankrupt() {
        return this.isBankrupt;
    }

    /**
     * @return Get actual budget of this consumerData.
     */
    public long getActualBudget() {
        return this.actualBudget;
    }

    /**
     *  Set the consumer bank-state.
     * @param bankrupt it is or it is not bankrupt.
     */
    public void setBankrupt(final boolean bankrupt) {
        this.isBankrupt = bankrupt;
    }

    /**
     * Set the budget after each update to keep track of this.
     * @param actualBudget the actual budget. The initial budget will be updated.
     */
    public void setActualBudget(final long actualBudget) {
        this.actualBudget = actualBudget;
    }

    /**
     * @return Get actual contract of this consumerData.
     */
    public Contract getContract() {
        return this.contract;
    }

    /**
     * Set the distributor of this consumer. This is set when the contract will be done.
     * @param distributorData The actual distributor of this consumer.
     */
    public void setDistributorData(final DistributorData distributorData) {
        this.distributorData = distributorData;
    }

    /**
     * @return Get actual distributorData of this consumerData.
     */
    public DistributorData getDistributorData() {
        return this.distributorData;
    }

    /**
     * The current Consumer - this -  will pay the contract.
     * This will also update his current budget
     */
    public void payPrice() {

        if (this.isBankrupt()) {
            return;
        }

        //have to pay the fees to the distributorData
        if (this.monthPassedUnpaid == 0) {
            //old taxes have been paid, now have to pay the actual one if it can be done
            if (this.getActualBudget() >= this.contract.getPrice()) {
                //this means he can pay tax
                this.setActualBudget(this.getActualBudget() - this.contract.getPrice());
                distributorData.increaseBudget(this.contract.getPrice());
                this.monthPassedUnpaid = 0;
                this.oldDistributor = null;
                this.monthPassedDebt = 0;
            } else {
                //it means he cant pay the tax, but he does not have debts
                this.monthPassedUnpaid = 1;
                this.oldDistributor = distributorData;
                this.monthPassedDebt = this.contract.getPrice();
                this.monthPassedDebt += (long) Math.floor(Constants.RATE
                        * this.contract.getPrice());
                //and will add the penalties
            }
        } else {
            //in this case if he cant pay he go bankrupt for good(IF HE IS AT THE SAME DISTRIBUTOR)
            //BECAUSE THE DISTRIBUTOR ALWAYS, AT THE BEGINNING OF THE MONTH, CAN BE CHANGED
            if (this.oldDistributor.equals(distributorData)) {
                //have the same distributor, lets see what will happen
                if (this.getActualBudget() >= (this.contract.getPrice()
                        + this.monthPassedDebt)) {
                    //have to pay all, and the good news is that he can do that this time
                    this.setActualBudget(this.getActualBudget()
                            - (this.contract.getPrice() + this.monthPassedDebt));
                    distributorData.increaseBudget(this.contract.getPrice()
                            + this.monthPassedDebt);
                    this.monthPassedUnpaid = 0;
                    this.oldDistributor = null;
                    this.monthPassedDebt = 0;
                } else {
                    //as a result of insufficient founds he will o bankrupt
                    this.setBankrupt(true);
                    contract.setValid(false);
                }
            } else {
               /**
                * if the consumer does not have the same distributor as
                * last month, that means we have to first thing first see if
                * he can pay the debt, immediately after that can he pay the
                * current contract? if he can, he will proceed with the payment,
                * if he cannot do that - > he will borrow some time till next month
                * and see what will happen after he will receive the monthly income
                */
                if (this.getActualBudget() >= this.monthPassedDebt) {
                    //set the actual budget, after the payment of the debts
                    this.setActualBudget(this.getActualBudget() - this.monthPassedDebt);
                    this.oldDistributor.increaseBudget(this.monthPassedDebt);

                    if (this.getActualBudget() >= this.contract.getPrice()) {
                        //pay to the actual distributor his taxes
                        this.setActualBudget(this.getActualBudget() - this.contract.getPrice());
                        distributorData.increaseBudget(this.contract.getPrice());
                        this.monthPassedUnpaid = 0;
                        this.oldDistributor = null;
                        this.monthPassedDebt = 0;
                    } else {
                        //set the debt and the payee object - have to pay
                        // the next month to this - theOldDistributor
                        this.monthPassedUnpaid = 1;
                        this.oldDistributor = distributorData;
                        this.monthPassedDebt = this.contract.getPrice();
                        this.monthPassedDebt += (long) Math.floor(Constants.RATE
                                * this.monthPassedDebt);
                    }
                } else {
                    //the consumer will go bankrupt
                    this.setBankrupt(true);
                    this.contract.setValid(false);
                }
            }
        }
        //decrease months left
        int month = contract.getRemainedContractMonths() - 1;
        contract.setMothsLeft(month);
        if (contract.getRemainedContractMonths() == 0) {
            contract.setValid(false);
        }
    }

    /**
     * This method will do a new contract, if the contract
     * existed, the consumer had at another time a contract
     * it means this object exists, so i decided just tu update his
     * contract to this new one, setting the current sate of that at valid
     * and months set at zero, otherwise the contract was set at the beginning
     * in attributes area.
     *
     * @param distributorData1 the actual distributor with whom i want to sing with
     */
    public void singContract(final DistributorData distributorData1) {
        if (this.isBankrupt) {
            return;
        }
        Contract contract1 = new Contract(distributorData1.getContractLength(),
                distributorData1.getContractPrice());
        if (this.contract != null) {
            this.contract.setValid(false);
            this.contract.setMothsLeft(0);
        }
        contract1.setConsumerData(this);
        //this price will remain at the actual price of the distributor until
        //it will be updated, it means the distributor have gone bankrupt, the contract
        //will expire.
        //but at that moment, not another time
        contract1.setPrice(distributorData1.getContractPrice());
        this.setContract(contract1);
        this.setDistributorData(distributorData1);
        distributorData1.addContract(contract1);
    }
}
