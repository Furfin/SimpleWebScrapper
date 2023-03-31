import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class WebScrapper {

    public static void main(String args[]) {
        WebScrapper scrapper;
        Scanner scan = new Scanner(System.in);
        System.out.println("Input:<Depth> <URL> <ThreadNum>");
        try {
            int maxDepth = 2;//scan.nextInt();
            Address adr = new Address(0,"https://example.com/");//scan.next());
            int maxT = 4;//scan.nextInt();
            URLpool pool = new URLpool(adr);
            for ( int i = 0; i < maxT; i++ ) {
                Scrapper runner = new Scrapper(pool, maxDepth, i);
                Thread childTread = new Thread(runner);
                childTread.start();
            }
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL");
        }
    }
}
