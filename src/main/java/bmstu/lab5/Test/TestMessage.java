package bmstu.lab5.Test;

public class TestMessage {
    int packageID;
    String jsScript;
    String functionName;
    Test test;
    //Boolean result;

    TestMessage(int packageID, String jsScript, String functionName, Test test) {
        this.packageID = packageID;
        this.jsScript = jsScript;
        this.functionName = functionName;
        this.test = test;
        //this.result = false;
    }
}
