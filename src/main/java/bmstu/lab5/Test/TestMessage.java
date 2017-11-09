package bmstu.lab5.Test;

class TestMessage {
    int packageID;
    String jsScript;
    String functionName;
    Test test;

    TestMessage(int packageID, String jsScript, String functionName, Test test) {
        this.packageID = packageID;
        this.jsScript = jsScript;
        this.functionName = functionName;
        this.test = test;
    }
}
