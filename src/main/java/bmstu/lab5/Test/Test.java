package bmstu.lab5.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Test {
    private String testName;
    private String expectedResult;
    private ArrayList<Integer> params;

    @JsonCreator
    public Test(@JsonProperty("testName") String testName, @JsonProperty("expectedResult") String expectedResult,
                @JsonProperty("params") ArrayList<Integer> params) {
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.params = params;
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
}
