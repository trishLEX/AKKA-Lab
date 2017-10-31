package bmstu.lab5.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TestPackageMessage {
    private int packageID;
    private String jsScript;
    private String functionName;
    private ArrayList<Test> tests;

    private final String PACKAGE_ID = "packageID";
    private final String JSSCRIPT = "jsScript";
    private final String FUNCTION_NAME = "functionName";
    private final String TESTS = "tests";

    @JsonCreator
    public TestPackageMessage(@JsonProperty(PACKAGE_ID) int packageID, @JsonProperty(JSSCRIPT) String jsScript,
                              @JsonProperty(FUNCTION_NAME) String functionName, @JsonProperty(TESTS) ArrayList<Test> tests) {
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
