package bmstu.lab5.Store;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreActor extends AbstractActor{
    //STORE: <PackageID, List(<testName, Result>)>
    private HashMap<Integer, ArrayList<HashMap<String, Boolean>>> store = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(StoreMessage.class, msg -> {
                    if (!store.containsKey(msg.getPackageID()))
                        store.put(msg.getPackageID(), msg.getTests());
                    else {
                        ArrayList<HashMap<String, Boolean>> result = store.get(msg.getPackageID());
                        result.addAll(msg.getTests());
                        store.replace(msg.getPackageID(), result);
                    }
                    System.out.println("received test: " + msg);
                })
                .match(GetMessage.class, req -> {
                    sender().tell(
                        new StoreMessage(req.packageID, store.get(req.packageID)),
                        self());
                })
                .build();
    }
}
