package com.frank.multithread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 8099);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		
		while(true){
			String msg = read.readLine();
			out.println(msg);
			out.flush();
			if(msg.equals("bye")){
				break;
			}
			System.out.println(in.readLine());
		}
		socket.close();
	}
}
