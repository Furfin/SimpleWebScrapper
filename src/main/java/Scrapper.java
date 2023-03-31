import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.net.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Scrapper implements Runnable {
    
    public Address adr;
    public ArrayList<String> URLs = new ArrayList<String>();
    public StringBuilder html = new StringBuilder();
    private Socket client;
    static URLpool pool;
    public int depth = 0;
    public int maxDepth;
    public int tId;
    

    public Scrapper(URLpool pool, int D, int id) {
        this.pool = pool;
        this.maxDepth = D;
        this.tId = id;
    }

    @Override
    public void run() {
        while ( depth <= maxDepth ) {
            if ( pool.size() > 0 || pool.threadsRun.size() > 0 ) {
                try {
                    Address adr = pool.getAddress();
                    pool.threadsRun.add(1);
                    this.adr = adr;
                    depth++;
                    loadHTML();
                    for ( int j = 0; j < URLs.size(); j++ ) {
                        try {
                            Address adrs = new Address(this.depth, URLs.get(j));
                            if ( depth <= maxDepth && pool.processed(adrs) ) {
                                System.out.println("Added "+adrs.get_url().toString() + " at "+String.valueOf(this.tId) );
                                pool.addAddress(adrs);
                            }
                        } catch (MalformedURLException e) {
                        }
                    }
                    pool.threadsRun.remove(1);
                } catch (UnknownHostException e) {
                    System.out.println("Invalid hostname " + this.adr.get_url().getHost());
                } catch (IOException e) {
                    //System.out.println("Something went wrong... With IO!");
                } catch ( Exception e) {
                }
               
            } else {
                break;
            }
        }
        System.out.println(pool.size());
    }

    public void loadHTML() throws UnknownHostException, IOException {
        try {
           
            Document doc = Jsoup.connect(this.adr.get_url().toString()).get();
            Elements elements = doc.select("a[href]");
            for (Element element : elements) {
                this.URLs.add(element.attr("href"));
            }
        } catch ( UnknownHostException e) {
            System.out.println("Something went wrong...");
        }
        
    }

    
}
