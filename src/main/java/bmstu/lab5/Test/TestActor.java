package bmstu.lab5.Test;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import static bmstu.lab5.WebServer.storeActor;

import bmstu.lab5.Store.StoreMessage;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestActor extends AbstractActor{
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestMessage.class, msg -> storeActor.tell(new StoreMessage(msg.packageID, runTest(msg.jsScript, msg.test.getTestName(), msg.test.getParams(),
                        msg.functionName, msg.test.getExpectedResult())),
                self()))
                .build();
    }

    private ArrayList<HashMap<String, Boolean>> runTest(String program, String testName, ArrayList<Integer> params, String functionName, String expectedResult) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");

        engine.eval(program);

        Invocable invocable = (Invocable) engine;

        String results = invocable.invokeFunction(functionName, params.toArray()).toString();

        ArrayList<HashMap<String, Boolean>> res = new ArrayList<>();

        HashMap<String, Boolean> map = new HashMap<>();

        System.out.println("RESULTS = " + results + " expected = " + expectedResult + " params = " + params);

        map.put(testName, results.equals(expectedResult));

        res.add(map);

        return res;
    }
}
