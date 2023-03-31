import java.net.*;

public class Address {
    private int depth;
    private URL url;

    public Address(int depth, String url) throws MalformedURLException {
        this.depth = depth;
        this.url = new URL(url);
    }

    public int get_depth() {
        return this.depth;
    }

    public URL get_url() {
        return this.url;
    }
}
