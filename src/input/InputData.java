package input;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains info about the input - this is the json object
 * commands -List<MonthlyUpdates>-  on what want to be processed - InitialData -
 * and how may times - numberOfTurns.
 */
public class InputData {

    //class that contains all data
    private int numberOfTurns;
    private InitialData initialData;
    private List<MonthlyUpdates> monthlyUpdates = new ArrayList<>();

    public InputData() { }

    public InputData(final int numberOfTurns, final InitialData initialData,
                     final List<MonthlyUpdates> monthlyUpdates) {
        this.numberOfTurns = numberOfTurns;
        this.initialData = initialData;
        this.monthlyUpdates = monthlyUpdates;
    }

    /**
     * @return the number of turns.
     */
    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    /**
     * @return the initial data.
     */
    public InitialData getInitialData() {
        return initialData;
    }

    /**
     * @return a list of monthly updates
     */
    public List<MonthlyUpdates> getMonthlyUpdates() {
        return monthlyUpdates;
    }

}
