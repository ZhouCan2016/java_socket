package com.frank.encryption;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class Client {

	private final static Logger logger = Logger.getLogger(Client.class.getName());
	
	public static void main(String[] args) throws Exception {
		for(int i = 0;i<100;i++){
			Socket socket = null;
			ObjectInputStream is = null;
			ObjectOutputStream os = null;
			
			try{
				SocketFactory factory = SSLSocketFactory.getDefault();
				socket = factory.createSocket("localhost",8099);
				
				os = new ObjectOutputStream(socket.getOutputStream());  
				User user = new User("name_"+i,"password_"+i);
				os.writeObject(user);
				os.flush();
				
				is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
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
