package com.frank.singlethread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 单线程socket
 * @author frank
 *
 */
public class Server {

	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(8099);
		Socket socket = server.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		
		while(true){
			String msg = in.readLine();
			System.out.println("服务端收到到信息："+msg);
			out.println("server revice:"+msg);
			out.flush();
			if(msg.equals("bye")){
				break;
			}
		}
		socket.close();
	}
}
