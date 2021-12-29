package entities;

/**
 * Types of energy produced by EnergyProducers
 */

public enum EnergyType {
    WIND("WIND", true),
    SOLAR("SOLAR", true),
    HYDRO("HYDRO", true),
    COAL("COAL", false),
    NUCLEAR("NUCLEAR", false);

    private final String label;
    private final boolean renewable;

    EnergyType(String label, boolean renewable) {
        this.label = label;
        this.renewable = renewable;
    }

    /**
     * @return what type of object it is - what label this enum has.
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return if it is this object renewable.
     */
    public boolean isRenewable() {
        return renewable;
    }

    /**
     * @param label pass it a string - the strategy but in String format.
     * @return Get a the strategy, but in EnergyType format.
     */
    public static EnergyType energyType(String label) {
        for (EnergyType energyType : EnergyType.values()) {
            if (energyType.label.equals(label)) {
                return energyType;
            }
        }
        return null;
    }
}
