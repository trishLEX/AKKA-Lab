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

import akka.pattern.PatternsCS;
import akka.routing.RoundRobinPool;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import bmstu.lab5.Store.GetMessage;
import bmstu.lab5.Store.StoreActor;
import bmstu.lab5.Test.TestActor;
import bmstu.lab5.Test.TestPackageActor;
import bmstu.lab5.Test.TestPackageMessage;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class WebServer {
    public static ActorRef storeActor;
    private static final String STORE_ACTOR = "storeActor";

    public static ActorRef testPerformerRouter;
    private static final String TEST_PERFORMER_ROUTER = "testPerformerRouter";

    private static ActorRef testPackageActor;
    private static final String TEST_PACKAGE_ACTOR = "testPackageActor";

    private static final String SERVER = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create("routes");

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        WebServer app = new WebServer(system);

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(system, materializer);
        final CompletionStage<ServerBinding> binding  = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(SERVER, PORT),
                materializer
        );

        System.out.println("Server is started at http://localhost:8080/");

        System.in.read();

        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());

        System.out.println("Server is turned off");
    }

    private WebServer(final ActorSystem system) {
        storeActor = system.actorOf(Props.create(StoreActor.class), STORE_ACTOR);
        testPackageActor = system.actorOf(Props.create(TestPackageActor.class), TEST_PACKAGE_ACTOR);
        testPerformerRouter = system.actorOf(new RoundRobinPool(5).props(Props.create(TestActor.class)), TEST_PERFORMER_ROUTER);
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
