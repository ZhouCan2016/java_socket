package com.frank.objectzip;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Client {

	private final static Logger logger = Logger.getLogger(Client.class.getName());
	
	public static void main(String[] args) throws Exception {
		for(int i = 0;i<100;i++){
			Socket socket = null;  
            GZIPOutputStream gzipos = null;  
            ObjectOutputStream os = null;  
            GZIPInputStream gzipis = null;  
            ObjectInputStream is = null;  
			
			try{
				socket = new Socket();  
                SocketAddress socketAddress = new InetSocketAddress("localhost", 8099);   
                socket.connect(socketAddress, 10 * 1000);  
                socket.setSoTimeout(10 * 1000);
                
				gzipos = new GZIPOutputStream(socket.getOutputStream());
				os = new ObjectOutputStream(gzipos);  
				User user = new User("name_"+i,"password_"+i);
				os.writeObject(user);
				os.flush();
				gzipos.finish();
				
				gzipis = new GZIPInputStream(socket.getInputStream());
				is = new ObjectInputStream(gzipis);
				Object obj = is.readObject();
				if(obj != null){
					User us = (User)obj;
					System.out.println("user: "+us.getName()+"/"+us.getPassword());
				}
			}catch(IOException ex){
				logger.log(Level.SEVERE, null, ex);
			}finally{
				try{
					is.close();
				}catch(Exception e){}
				
				try{
					os.close();
				}catch(Exception e){}
				
				try{
					socket.close();
				}catch(Exception e){}
				
			}
			
		}
	}
}
