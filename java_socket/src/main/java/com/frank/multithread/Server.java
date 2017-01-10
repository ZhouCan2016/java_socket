package com.frank.multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 多线程socket
 * @author frank
 *
 */
public class Server {
	
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(8099);
		
		//循环监听客户端
		while(true){
			Socket socket = server.accept();
			invoke(socket);
		}
	}
	
	
	private static void invoke(final Socket client) throws IOException{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				BufferedReader in = null;
				PrintWriter out = null;
				try{
					in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					out = new PrintWriter(client.getOutputStream());
					while(true){
						String msg = in.readLine();
						System.out.println(msg);
						out.println("Server recevied"+msg);
						out.flush();
						if(msg.equals("bye")){
							return;
						}
					}
				}catch(IOException ex){
					ex.printStackTrace();
				}finally{
					try{
						in.close();
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						try{
							out.close();
						}catch(Exception e){
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
