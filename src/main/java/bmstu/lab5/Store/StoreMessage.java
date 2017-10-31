package bmstu.lab5.Store;

import bmstu.lab5.Test.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class StoreMessage {
    private int packageID;
    private ArrayList<Test> tests;

    private final String PACKAGE_ID = "packageID";
    private final String TESTS = "tests";

    @JsonCreator
    public StoreMessage(@JsonProperty(PACKAGE_ID) int packageID, @JsonProperty(TESTS) ArrayList<Test> tests) {
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

    public ArrayList<Test> getTests() {
        return tests;
    }
}
