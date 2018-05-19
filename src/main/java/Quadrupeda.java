import java.util.Stack;

public class Quadrupeda {

    public static void main(String args[]) {

        Stack<Crawler> siteLink = new Stack<Crawler>();

        // Creating a new Crawler
        Crawler crawlerOne = new Crawler();
        Crawler crawlerTwo = new Crawler();
        Crawler crawlerThree = new Crawler();

        System.out.println(crawlerOne.getThreadId());
        System.out.println(crawlerTwo.getThreadId());
        System.out.println(crawlerThree.getThreadId());

    }

}
