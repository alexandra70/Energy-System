package input;

/**
 * Class that contains all the information that has been read - the ones that refers
 * to the consumer are transferred in a object like this.
 */

public class Consumer {

    private int id;
    private int initialBudget;
    private int monthlyIncome;

    public Consumer() {
    }

    public Consumer(final int id, final int initialBudget, final int monthlyIncome) {
        this.id = id;
        this.initialBudget = initialBudget;
        this.monthlyIncome = monthlyIncome;
    }

    /**
     * @return Get getMonthlyIncome of this Consumer.
     */
    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    /**
     * @return Get getInitialBudget of this Consumer.
     */
    public int getInitialBudget() {
        return initialBudget;
    }

    /**
     * @return Get id of this Consumer.
     */
    public int getId() {
        return id;
    }
}
