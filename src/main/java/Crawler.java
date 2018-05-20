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

        try {

            // Getting page content
            Document pageContent = getSiteContents(this.crawlerUrl);

            // Getting the links from the page content
            Stack<String> pageLinks = getLinksFromSite(pageContent);

            // Creating a crawler swarm for the links
            Stack<Crawler> swarm = createCrawlerSwarm(pageLinks);

            while (!swarm.empty()) {
                swarm.pop().run();
            }

            this.kill();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Constructor
    Crawler(String urlToCrawl) {
        if (threadCount < maxThreadCount) {
            threadCount++;
            this.crawlerUrl = urlToCrawl;
            System.out.println("Current threads: " + threadCount + " \' This thread ID: " + this.threadId);
        } else {
            System.out.println("Max thread limit reached!");
        }
    }

    // Gets the contents of a site
    private Document getSiteContents(String URL) throws IOException {
        System.out.println(URL + ": ");
        return Jsoup.connect(URL).get();
    }

    // @Return Stack<String> | Gets a stack of links from a page's contents
    private Stack<String> getLinksFromSite(Document siteContents) {

        Stack<String> links = new Stack<String>();
        Elements siteAnchorTags = siteContents.select("a");

        for (Element anchorTag : siteAnchorTags) {
            links.push(anchorTag.attr("href"));
            System.out.println("\t" + links.peek());
        }

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

    private void kill() {
        threadCount--;
    }

}
