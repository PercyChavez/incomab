/*
 * Developed by: Alexis Peralta Holyoak.
 */
package com.incomab.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author peral
 */
public class EthernetUtil {
    public boolean IsConnected(){
            String site="google.com";
            Socket sock=new Socket();
            InetSocketAddress addr=new InetSocketAddress(site,80);
            try {
            sock.connect(addr,3000);
            return true;
        } catch (IOException e) {
            return false;
        }finally{
                try {
                    sock.close();
                } catch (IOException e) {                    
                }
            }
    }
}
