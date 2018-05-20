import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.Callable;

public class CrawlerVersion2 implements Callable<Stack<String>> {

    private static int threadCount;
    private int threadId;
    private String URL;

    /*
        Constructor
     */
    CrawlerVersion2(String URL) {
        threadCount++;
        this.threadId = threadCount;
        this.URL = URL;
    }

    /*
        Main thread execution
     */
    public Stack<String> call() {

        Stack<String> links = new Stack<String>();

        try {
            Document pageContents = getPageContents(this.URL);
            links = getLinksFromPage(pageContents);
            this.kill();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return links;

    }

    /*
        Gets the page contents
     */
    private Document getPageContents(String URL) throws IOException {
        return Jsoup.connect(URL).get();
    }

    /*
        Gets the links from the page content
     */
    private Stack<String> getLinksFromPage(Document pageContents) {
        Stack<String> linkStack = new Stack<String>();
        Elements links = pageContents.select("a");
        for (Element link : links) {
            linkStack.push(link.attr("href"));
        }
        return linkStack;
    }

    /*
        Kills the thread, kind of ;)
     */
    private void kill() {
        threadCount--;
    }

    /*
        Returns the Thread ID
     */
    public int getThreadId() {
        return this.threadId;
    }

    /*
        Gets the current thread count
     */
    public int getThreadCount() {
        return threadCount;
    }


}
