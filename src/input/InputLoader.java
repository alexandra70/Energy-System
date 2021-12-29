package input;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


/**
 * The class reads the data from json file and parses it.
 */

public final class InputLoader {

    //the path to the input file
    private String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * Read the data from input file.
     * @return an input object - contains all data that i will need
     * @throws IOException will give an error when reading file.
     */
    public InputData readInputData() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(this.inputPath);
        InputData inputData = objectMapper.readValue(file, InputData.class);

        return inputData;
    }
}
