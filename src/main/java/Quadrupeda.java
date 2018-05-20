import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Quadrupeda {

    public static void main(String args[]) throws Exception {

        ExecutorService service = Executors.newFixedThreadPool(3);

        Future<Stack<String>> linkThread = service.submit(new CrawlerVersion2("http://frooplexp.com"));

        Stack<String> links = linkThread.get();

        while (!links.isEmpty()) {
            System.out.println(links.pop());
        }


    }

}
