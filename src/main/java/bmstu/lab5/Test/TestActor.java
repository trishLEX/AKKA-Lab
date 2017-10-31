package bmstu.lab5.Test;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import static bmstu.lab5.WebServer.storeActor;

import bmstu.lab5.Store.StoreMessage;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;

public class TestActor extends AbstractActor{
    private final String LANGUAGE = "js";

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestMessage.class, msg -> storeActor.tell(new StoreMessage(msg.packageID,
                                runTest(msg.jsScript, msg.test.getTestName(), msg.test.getParams(), msg.functionName, msg.test.getExpectedResult())),
                self()))
                .build();
    }

    private ArrayList<Test> runTest(String program, String testName, ArrayList<Integer> params, String functionName, String expectedResult) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(LANGUAGE);

        engine.eval(program);

        Invocable invocable = (Invocable) engine;

        String results = invocable.invokeFunction(functionName, params.toArray()).toString();

        ArrayList<Test> res = new ArrayList<>();

        Test test = new Test(testName, expectedResult, params, results.equals(expectedResult));

        res.add(test);

        return res;
    }
}
