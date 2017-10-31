package bmstu.lab5.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Test {
    private String testName;
    private String expectedResult;
    private ArrayList<Integer> params;
    private boolean result;

    private final String TEST_NAME = "testName";
    private final String EXPECTED_RESULT = "expectedResult";
    private final String PARAMS = "params";

    @JsonCreator
    public Test(@JsonProperty(TEST_NAME) String testName, @JsonProperty(EXPECTED_RESULT) String expectedResult,
                @JsonProperty(PARAMS) ArrayList<Integer> params) {
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.params = params;
        this.result = false;
    }

    public Test(String testName, String expectedResult, ArrayList<Integer> params, boolean result) {
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.params = params;
        this.result = result;
    }

    public String getTestName() {
        return this.testName;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public ArrayList<Integer> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "testName = " + testName + " result = " + result;
    }
}
