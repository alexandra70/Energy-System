package database;

import rungame.RunGame;
import utils.Constants;

/**
 * basically a factory object is mostly used to generate instances
 * of a specific class(es), so ue use that this way
 * put some thing in that to generate what i want ,
 * i want after that to make it a singleton because
 * i will use just one factory - this factory can be instantiation
 * more that one time
 */
public final class EnergyObjectsFactory {

    private static EnergyObjectsFactory instance = null;

    private EnergyObjectsFactory() {
    }

    /**
     * Singleton - Object
     * @return the unique instance if of the EnergyObjectsFactory
     */
    public static EnergyObjectsFactory getInstance() {
        if (instance == null) {
            instance = new EnergyObjectsFactory();
        }
        return instance;
    }

    /**
     *
     * @param typeObject represent the type of object to be created
     * @return an object of following types :  RunGame, Consumers, Distributors
     */
    public Object energyObjectsFactory(final String typeObject) {

        if (typeObject.equals(Constants.RUN_GAME)) {
            return new RunGame();
        }

        if (typeObject.equals(Constants.CONSUMERS)) {
            return new Consumers();
        }

        if (typeObject.equals(Constants.DISTRIBUTORS)) {
            return new Distributors();
        }

        if(typeObject.equals(Constants.PRODUCERS)) {
            return new Producers();
        }
        return null;
    }

}
