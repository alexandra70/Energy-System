package input;

/**
 * A class that will help me to build the MonthlyUpdate Object - the
 * monthly object cannot exist entirety without this costChanges objects.
 * CostChanges will be created every at each month and will be populated
 * with information about the changes that will modify the distributors
 * consumers interaction and their data.
 */
public class DistributorChanges {

    //id represents the idx of the distributor (initial order)
    //and the next two represents the new cost for
    //the distributor-id;
    private int id;
    private long infrastructureCost;

    public DistributorChanges() {

    }

    public DistributorChanges(final int id, final long infrastructureCost) {
        this.id = id;
        this.infrastructureCost = infrastructureCost;
    }

    /**
     * @return Get id of this distributor that is updated.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Get infrastructureCost of this update-distributor.
     */
    public long getInfrastructureCost() {
        return infrastructureCost;
    }

}
