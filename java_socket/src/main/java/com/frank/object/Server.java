package com.frank.object;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 对象传输Socket
 * @author frank
 *
 */
public class Server {

	private final static Logger logger = Logger.getLogger(Server.class.getName());
	
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(8099);
		
		while(true){
			Socket socket = server.accept();
			invoke(socket);
		}
	}
	
	private static void invoke(final Socket client) throws IOException{
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {

				ObjectInputStream is = null;
				ObjectOutputStream os = null;
				try{
					is = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));  
                    os = new ObjectOutputStream(client.getOutputStream());
					
					Object obj = is.readObject();
					User user = (User)obj;
					System.out.println("user: "+user.getName()+"/"+user.getPassword());
					
					user.setName(user.getName()+"_new");
					user.setPassword(user.getPassword()+"_new");
					os.writeObject(user);
					os.flush();
				}catch(IOException ex){
					logger.log(Level.SEVERE, null, ex);
				}catch(ClassNotFoundException ex){
					logger.log(Level.SEVERE, null, ex);
				}finally{
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						try {
							os.close();
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							try {
								client.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}).start();
	}
}
