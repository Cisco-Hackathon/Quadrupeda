// Importing jsoup
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Stack;

public class Crawler implements Runnable {

    // Holds the current threadCount and the thread ID for the crawler
    private static int threadCount = 0;
    private int threadId = threadCount;
    private int maxThreadCount = 10;

    // Starting URL for the crawler
    private String crawlerUrl;

    // Stack of new crawlers ready to go
    private Stack<Crawler> newCrawlers = new Stack<Crawler>();


    // Main thread process
    public void run() {



    }

    // Constructor
    Crawler(String urlToCrawl) {
        // Upon creation, update the global thread count
        this.threadCount++;
        // Setting the URL for this crawler
        this.crawlerUrl = urlToCrawl;
    }

    // Gets the contents of a site
    private Document getSiteContents(String URL) throws IOException {
        return Jsoup.connect(URL).get();
    }

    // @Return Stack<String> | Gets a stack of links from a page's contents
    private Stack<String> getLinksFromSite(Document siteContents) {
        // Stack to hold the links
        Stack<String> links = new Stack<String>();
        // Getting all <a> tags from the site
        Elements siteAnchorTags = siteContents.select("a");
        // Getting the link values from each of the tags
        for (Element anchorTag : siteAnchorTags) {
            links.push(anchorTag.attr("href"));
        }
        // Returning the stack of links
        return links;
    }

    private Stack<Crawler> createCrawlerSwarm(Stack<String> links) {

        Stack<Crawler> crawlerSwarm = new Stack<Crawler>();

        while (!links.empty()) {
            Crawler newCrawler = new Crawler(links.pop());
            crawlerSwarm.push(newCrawler);
        }

        return crawlerSwarm;

    }

    //////////////////////////////////////////////////////
    /// GETTERS

    // Returns the Thread usage of the crawler
    public static int getThreadUsage() {
        return threadCount;
    }

    // Returns the thread ID of the crawler
    public int getThreadId() {
        return this.threadId;
    }

    // Returns a stack with new instance of crawlers
    public Stack<Crawler> getNewCrawlers() {
        return this.newCrawlers;
    }

    public void kill() {
        threadCount--;
    }

}
