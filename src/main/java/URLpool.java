import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;


public class URLpool {
    private ConcurrentLinkedQueue<Address> addressList;
    public ConcurrentLinkedQueue<Address> processedList = new ConcurrentLinkedQueue<Address>();
    public  ConcurrentLinkedQueue<Integer> threadsRun = new ConcurrentLinkedQueue<Integer>();

    public int size() {
        return addressList.size();
    }

    public URLpool( Address adr) {
        this.addressList = new ConcurrentLinkedQueue<Address>();
        this.processedList.add(adr);
        this.addressList.add(adr);
    }

    public Address getAddress() {
        Address response  = addressList.poll();
        processedList.add(response);
        return response;
    }

    public void addAddress(Address adr) {
        this.addressList.add(adr);
    }

    public boolean processed(Address adr) {
        boolean status = true;
        for( Address address: processedList ) {
            if (address.get_url().getHost().equals(adr.get_url().getHost()) && address.get_url().getPath().equals(adr.get_url().getPath())) {
                status = false;
            }
        }
        for( Address address: addressList ) {
            if (address.get_url().getHost().equals(adr.get_url().getHost()) && address.get_url().getPath().equals(adr.get_url().getPath())) {
                status = false;
            }
        }
        return status;
    }
}
