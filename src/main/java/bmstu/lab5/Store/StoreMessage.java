package bmstu.lab5.Store;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreMessage {
    private int packageID;
    private ArrayList<HashMap<String, Boolean>> tests;

    @JsonCreator
    public StoreMessage(@JsonProperty("packageID") int packageID, @JsonProperty("tests") ArrayList<HashMap<String, Boolean>> tests) {
        this.packageID = packageID;
        this.tests = tests;
    }

    @Override
    public String toString() {
        return "packageID = " + packageID + " : " + tests;
    }

    public int getPackageID() {
        return packageID;
    }

    public ArrayList<HashMap<String, Boolean>> getTests() {
        return tests;
    }
}
