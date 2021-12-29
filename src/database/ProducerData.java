package database;

import entities.EnergyType;

import java.util.*;

public class ProducerData extends Observable {

    private final int id;
    private final EnergyType energyType;
    private final int maxDistributors;
    private final double priceKW;
    private double energyPerDistributor;
    private ArrayList<DistributorData> observers;
    /**each producer data will have an monthly report that
     * will be updated only after initial round
     */
    private final HashMap<Integer, ArrayList<Integer>> monthlyStats;

    /**
     * Constructor of this object.
     * @param id The id.
     * @param energyType The energy type that i want.
     * @param maxDistributors Max distributors that i need.
     * @param priceKW Price that is required.
     * @param energyPerDistributor The max energy that this distributor can offer.
     */
    public ProducerData(final int id, String energyType, final int maxDistributors,
                        final double priceKW, final double energyPerDistributor) {
        this.id = id;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
        this.energyType = EnergyType.energyType(energyType);
        this.observers = new ArrayList<DistributorData>();
        this.monthlyStats = new HashMap<>();
    }

    /**
     * Update the list of observers at each month and put in a hashMap
     * at the key - month -> values [ distributors for this month ] .
     * @param month The number of month.
     */
    public void updateMonthlyStats(final int month) {
        //construct for this producer his monthly stat
        ArrayList<Integer> currentMonth = new ArrayList<>();
        for (DistributorData distributor : this.observers) {
            currentMonth.add(distributor.getId());
        }
        this.monthlyStats.put(month, currentMonth);
    }

    /**
     * @return Get the id of the current producer.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return Get the energy of this distributor.
     */
    public double getEnergyPerDistributor() {
        return this.energyPerDistributor;
    }

    /**
     * @return Get the id of the current producer.
     */
    public double getPriceKW() {
        return this.priceKW;
    }

    /**
     * @return Get maxNr of distributors that current producer can have.
     */
    public int getMaxDistributors() {
        return this.maxDistributors;
    }

    /**
     * @return Get the energyType of the current producer.
     * It is an enum.
     */
    public EnergyType getEnergyType() {
        return energyType;
    }

    /**
     * @param energyPerDistributor
     */
    public void setEnergyPerDistributor(final double energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    /**
     * @return Get the number of observers that this producer has.
     */
    public int getNrDistributors() {
        return this.observers.size();
    }

    /**
     * @param observer Add the observer to the list of observers.
     */
    public void addObservers(final DistributorData observer) {
        this.observers.add(observer);
    }

    /**
     * @param observer Remove the observer form its observers-list.
     */
    public void removeObserver(final DistributorData observer) {
        if (this.observers.contains(observer)) {
            this.observers.remove(observer);
        }
    }

    /**
     * Method used to go trough all observers of this producers, and update them
     * (notify them obout a change that occurred in this structure).
     */
    public void updateObservers() {
        //when updateObs will be called the observers should be reapply their strategies
        //will search again for producers to be added in a specific producer observers list.
        for (DistributorData distributor : this.observers) {
            distributor.update(this, this.getPriceKW());
        }
    }

    /**
     * @return A hashmap of the months and the distributors at each month.
     */
    public HashMap<Integer, ArrayList<Integer>> getMonthlyStats() {
        return this.monthlyStats;
    }

    /**
     * @return Get the observers at current time(when this is called).
     */
    public ArrayList<DistributorData> getObservers() {
        return observers;
    }

}
