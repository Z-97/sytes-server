package org.sytes.gate.server;
import org.sytes.gate.server.http.HttpHandler;
import org.sytes.gate.server.http.HttpServer;
public class FengyunApp 
{
    public static void main( String[] args )
    {
    	
    	ApplicationContext.getInstance();
    	HttpHandler httpHandler=new HttpHandler();
        new HttpServer(8848,httpHandler).start();
       
    }
}
