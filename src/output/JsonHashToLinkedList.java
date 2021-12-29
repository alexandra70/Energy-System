package output;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

/**
 * This class main purpose is to modify the way the json put the
 * objects that are passed to it. Its internal way to do this
 * is done using a hashMap, in this case the thing that is wanted
 * is to make this structure a linkedList. By doing this - the json
 * out-file can be written the way i want.
 */
public class JsonHashToLinkedList {

    public JsonHashToLinkedList() {

    }

    /**
     * This allows me to add objects in json in the order i decided.
     * Not the order based on hashMap applied function.
     * @param jsonObject the object to be modified.
     */
    public void jsonHelper(JSONObject jsonObject) {
        try {
            Field changeMap = jsonObject.getClass().getDeclaredField("map");
            changeMap.setAccessible(true);
            changeMap.set(jsonObject, new LinkedHashMap<>());
            changeMap.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            System.out.println("illegal");
        }
    }

}
