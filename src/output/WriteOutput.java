package output;

import database.Producers;
import database.Consumers;
import database.Distributors;
import database.DistributorData;
import database.ConsumerData;
import database.ProducerData;

import org.json.JSONException;
import utils.Constants;
import database.Contract;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public final class WriteOutput {

    /**
     *  Receive the distributors and consumers and pass them to the output
     * @param path The file where the output will be written.
     * @param consumers The consumers data. List of them - contains all info i need.
     * @param distributors The distributor data. List of them - contains all info i need.
     * @throws IOException Exception will give an error at opening/reading/writing the output-file.
     */
    public void writeJson(final String path, final Consumers consumers,
                          final Distributors distributors, final Producers producers)
            throws IOException, JSONException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        JSONArray jsonArray = new JSONArray();
        ArrayList<ConsumerData> consumers1 = consumers.getConsumers();

        JsonHashToLinkedList json = new JsonHashToLinkedList();

        consumers1.sort(new Comparator<ConsumerData>() {
            @Override
            public int compare(final ConsumerData o1, final ConsumerData o2) {
                return o1.getId() - o2.getId();
            }
        });
        for (ConsumerData consumerData : consumers1) {
            //an array formed by json object(more is we have more consumers)
            JSONObject jsonObjectConsumer = new JSONObject();

            //modify the internal way the json add objects.
            json.jsonHelper(jsonObjectConsumer);

            jsonObjectConsumer.put(Constants.ID, consumerData.getId());
            jsonObjectConsumer.put(Constants.IS_BANKRUPT, consumerData.isBankrupt());
            jsonObjectConsumer.put(Constants.BUDGET, consumerData.getActualBudget());
            jsonArray.add(jsonObjectConsumer);
        }

        JSONArray jsonArrayDistributors = new JSONArray();

        for (DistributorData distributorData : distributors.getDistributors()) {
            JSONObject jsonObjectDistributor = new JSONObject();
            json.jsonHelper(jsonObjectDistributor);

            jsonObjectDistributor.put(Constants.ID, distributorData.getId());
            jsonObjectDistributor.put(Constants.ENERGY_NEEDED,
                    distributorData.getEnergyNeededKW());
            jsonObjectDistributor.put(Constants.CONTRACT_COST, distributorData.getContractPrice());
            jsonObjectDistributor.put(Constants.BUDGET, distributorData.getBudget());
            jsonObjectDistributor.put(Constants.PRODUCER_STRATEGY,
                    distributorData.getProducerStrategy());
            jsonObjectDistributor.put(Constants.IS_BANKRUPT, distributorData.isBankrupt());

            JSONArray jsonContract = new JSONArray();
            ArrayList<Contract> contracts = distributorData.getContracts();
            contracts.sort(new Comparator<Contract>() {
                @Override
                public int compare(final Contract o1, final Contract o2) {
                    if (o1.getRemainedContractMonths() == o2.getRemainedContractMonths()) {
                        return o1.getConsumerId() - o2.getConsumerId();
                    }
                    return o1.getRemainedContractMonths() - o2.getRemainedContractMonths();
                }
            });

            distributorData.setContracts(contracts);
            for (Contract contract : contracts) {

                JSONObject jsonObjectContract = new JSONObject();
                json.jsonHelper(jsonObjectContract);

                jsonObjectContract.put(Constants.ID_CONSUMER, contract.getConsumerId());
                jsonObjectContract.put(Constants.PRICE, contract.getPrice());
                jsonObjectContract.put(Constants.REMAINED, contract.getRemainedContractMonths());
                jsonContract.add(jsonObjectContract);
            }
            jsonObjectDistributor.put(Constants.CONTRACTS, jsonContract);
            jsonArrayDistributors.add(jsonObjectDistributor);
        }

        JSONArray jsonProducesArray = new JSONArray();
        for (ProducerData producer : producers.getProducerData()) {
            JSONObject objectProducer = new JSONObject();
            //change in the json constructor - json in build as a hashmap, and we want to change
            //it at linkedHashmap - this will allow me to organise my data in the order i want.
            json.jsonHelper(objectProducer);

            objectProducer.put(Constants.ID, producer.getId());
            objectProducer.put(Constants.MAX_DISTRIBUTORS, producer.getMaxDistributors());
            objectProducer.put(Constants.PRICE_KW, producer.getPriceKW());
            objectProducer.put(Constants.ENERGY_TYPE, producer.getEnergyType());
            objectProducer.put(Constants.E_DISTRIBUTOR, producer.getEnergyPerDistributor());

            //make an object monthly stats that will allow to see distributors this has monthly.
            JSONArray monthlyStats = new JSONArray();
            for (Map.Entry entry : producer.getMonthlyStats().entrySet()) {
                //for each make a new object that will be added to jsonArray - monthlyStata;
                JSONObject objectStat = new JSONObject();
                json.jsonHelper(objectStat);

                objectStat.put(Constants.MONTH, entry.getKey());
                ArrayList<Integer> toBeSorted = (ArrayList<Integer>) entry.getValue();
                toBeSorted.sort(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o1.intValue() - o2.intValue();
                    }
                });
                objectStat.put(Constants.DISTRIBUTORS_IDS, toBeSorted);
                monthlyStats.add(objectStat);
            }
            objectProducer.put(Constants.MONTHLY_STATE, monthlyStats);
            jsonProducesArray.add(objectProducer);
        }

        JSONObject jsonObject = new JSONObject();
        json.jsonHelper(jsonObject);

        jsonObject.put(Constants.CONSUMERS, jsonArray);
        jsonObject.put(Constants.DISTRIBUTORS, jsonArrayDistributors);
        jsonObject.put(Constants.ENERGY_PRODUCERS, jsonProducesArray);

        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(jsonObject.toString());

        fileWriter.flush();
        fileWriter.close();
    }
}
