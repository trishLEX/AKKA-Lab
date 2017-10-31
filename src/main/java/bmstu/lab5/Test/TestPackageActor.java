package bmstu.lab5.Test;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import static bmstu.lab5.WebServer.testPerformerRouter;

public class TestPackageActor extends AbstractActor{
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestPackageMessage.class, msg -> {
                    for (Test test: msg.getTests()) {
                        testPerformerRouter.tell(new TestMessage(msg.getPackageID(), msg.getJsScript(), msg.getFunctionName(), test), self());
                    }
                })
                .build();
    }
}
