package bmstu.lab5.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TestPackageMessage {
    private int packageID;
    private String jsScript;
    private String functionName;
    private ArrayList<Test> tests;

    @JsonCreator
    public TestPackageMessage(@JsonProperty("packageID") int packageID, @JsonProperty("jsScript") String jsScript,
                              @JsonProperty("functionName") String functionName, @JsonProperty("tests") ArrayList<Test> tests) {
        this.packageID = packageID;
        this.jsScript = jsScript;
        this.functionName = functionName;
        this.tests = tests;
    }

    public int getPackageID() {
        return packageID;
    }

    public String getJsScript() {
        return jsScript;
    }

    public String getFunctionName() {
        return functionName;
    }

    public ArrayList<Test> getTests() {
        return tests;
    }
}
