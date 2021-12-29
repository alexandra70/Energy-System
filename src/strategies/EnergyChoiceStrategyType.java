package strategies;

/**
 * Strategy types for distributors to choose their producers
 */
public enum EnergyChoiceStrategyType {
    GREEN("GREEN"),
    PRICE("PRICE"),
    QUANTITY("QUANTITY");
    private final String label;

    /**
     * @param label receive a string and transform this in this object.
     */
     EnergyChoiceStrategyType(final String label) {
        this.label = label;
    }

    /**
     * @return the label of the enum, the string value.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * This method is used as it is presented in the lab(the one about strategies).
     * Find the object that has that label and return that enum.
     * @param energy The string that i want to find.
     * @return Return (in case it type of strategy exists) the enum.
     */
    public static EnergyChoiceStrategyType strategyType(String energy) {
        // go trough all the enums and verify is the string check any enum label value.
        for (EnergyChoiceStrategyType energyChoiceStrategyType
                : EnergyChoiceStrategyType.values()) {
            if (energyChoiceStrategyType.label.equals(energy)) {
                return energyChoiceStrategyType;
            }
        }
        // null otherwise
        return null;
    }
}
