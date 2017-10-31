package bmstu.lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import static akka.http.javadsl.server.Directives.*;
import static java.security.AccessController.getContext;

import akka.pattern.PatternsCS;
import akka.routing.RoundRobinPool;
import akka.routing.Routee;
import akka.routing.Router;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import bmstu.lab5.Store.GetMessage;
import bmstu.lab5.Store.StoreActor;
import bmstu.lab5.Test.TestActor;
import bmstu.lab5.Test.TestPackageActor;
import bmstu.lab5.Test.TestPackageMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class WebServer {
    public static ActorRef storeActor;
    //public static ActorRef testActor;
    public static ActorRef testPerformerRouter;
    public static ActorRef testPackageActor;
//
//    private WebServer(final ActorSystem system) {
//        server = system.actorOf(Auction.props())
//    }
//
    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create("routes");

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        WebServer app = new WebServer(system);

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(system, materializer);
        final CompletionStage<ServerBinding> binding  = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost("localhost", 8080),
                materializer
        );

        System.out.println("Server is started at http://localhost:8080/");

        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());

        System.out.println("Server is turned off");
    }

    WebServer(final ActorSystem system) {
        storeActor = system.actorOf(Props.create(StoreActor.class), "storeActor");
        //testActor = system.actorOf(Props.create(TestActor.class), "testActor");
        testPackageActor = system.actorOf(Props.create(TestPackageActor.class), "testPackageActor");
        testPerformerRouter = system.actorOf(new RoundRobinPool(5).props(Props.create(TestActor.class)), "routerForTests");
//        Router router;
//        {
//            List<Routee> routees = new ArrayList<>();
//            for (int i = 0; i < 5; i++) {
//                ActorRef r = system.actorOf
//            }
//        }
    }

    private Route createRoute() {
        return route(
                get(() ->
                        parameter("packageID", (packageID) ->
                        {
                            CompletionStage<Object> result = PatternsCS.ask(storeActor, new GetMessage(Integer.parseInt(packageID)), 5000);
                            return completeOKWithFuture(result, Jackson.marshaller());
                        })),
                post(() -> entity(Jackson.unmarshaller(TestPackageMessage.class), msg -> {
                    testPackageActor.tell(msg, ActorRef.noSender());
                    return complete("Test started!");
                })));
    }
}
