import database.Consumers;
import database.Distributors;
import database.EnergyObjectsFactory;
import database.Producers;
import input.InputData;
import input.InputLoader;
import output.WriteOutput;
import rungame.RunGame;
import utils.Constants;

/**
 * Entry point to the simulation
 */

public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {


        //need a inputData object - the one where all the file
        //is put - effective added to this inputData object.
        InputLoader inputLoader = new InputLoader(args[0]);
        InputData inputData = inputLoader.readInputData();

        EnergyObjectsFactory energyFactory = EnergyObjectsFactory.getInstance();
        RunGame runGame = (RunGame) energyFactory.energyObjectsFactory(Constants.RUN_GAME);

        Distributors distributors
                = (Distributors) energyFactory.energyObjectsFactory(Constants.DISTRIBUTORS);
        Consumers consumers
                = (Consumers) energyFactory.energyObjectsFactory(Constants.CONSUMERS);
        Producers producers
                = (Producers) energyFactory.energyObjectsFactory(Constants.PRODUCERS);

        producers.makeProducers(inputData.getInitialData().getProducers());
        distributors.makeDistributorsList(inputData.getInitialData().getDistributors());

        runGame.runInitialRound(inputData, distributors, consumers, producers);
        runGame.runGame(inputData, distributors, consumers, producers);

        WriteOutput writeOutput = new WriteOutput();
        writeOutput.writeJson(args[1], consumers, distributors, producers);
    }
}
