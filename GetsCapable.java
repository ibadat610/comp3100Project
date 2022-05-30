import java.io.*;
import java.net.*;

public class GetsCapable {
	public static void main(String[] args) throws Exception{
		Socket s=new Socket("localhost",50000);
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		BufferedReader dis=new BufferedReader(new
		InputStreamReader(s.getInputStream()));
		
		int count = 0;
		
		String strServer;
		String client = "HELO\n";
		
		dout.write(client.getBytes());
		dout.flush();
		
		strServer = dis.readLine();
		System.out.println("Message = " + strServer);	
		
		String username = System.getProperty("user.name");
		client = "AUTH "+username+"\n";
		
		dout.write(client.getBytes());
		dout.flush();

		strServer = dis.readLine();
		System.out.println("Message = " + strServer);
		
		while (!(strServer.contains("NONE"))) {
		
			client = "REDY\n";
		
			dout.write(client.getBytes());
			dout.flush();
		
			strServer = dis.readLine(); //JOB
			System.out.println("Message = " + strServer);

			
			if (strServer.contains("JOBN")) {
		
				String[] core = strServer.split(" ");
			
				client = "GETS Avail " + core[4] + " " + core[5] + " " + core[6] + "\n";
		
				dout.write(client.getBytes());
				dout.flush();

		
				strServer = dis.readLine(); //DATA
				System.out.println("Message = " + strServer);
				client = "OK\n";
		
				dout.write(client.getBytes());
				dout.flush();
		
				String[] data = strServer.split(" ");
				
			
				if (Integer.parseInt(data[1]) != 0) {
			
				int servers = Integer.parseInt(data[1]);
				System.out.println(servers);
			
				String[] nRec = new String[servers]; //SERVERS
		
				for (int i = 0; i < servers; i++) {
					strServer = dis.readLine();
					nRec[i] = strServer;
					//System.out.println("nRec: " + nRec[i]);
				}
		
				client = "OK\n";
		
				dout.write(client.getBytes());
				dout.flush();
		
				strServer = dis.readLine();
		
				String[] first = nRec[0].split(" "); //1st
		
				client = "SCHD " + count + " " + first[0] + " " + first[1] + "\n";
				
				dout.write(client.getBytes());
				dout.flush();
		
				strServer = dis.readLine();
				System.out.println("Message = " + strServer); 
				count++;
				}
			
				else { 
				
				client = "GETS Capable " + core[4] + " " + core[5] + " " + core[6] + "\n";
				dout.write(client.getBytes());
				dout.flush();
		
				strServer = dis.readLine(); //DATA
				System.out.println("Message = " + strServer);

				client = "OK\n";
		
				dout.write(client.getBytes());
				dout.flush();

				strServer = dis.readLine(); //DAT



				String[] data1 = strServer.split(" ");
				
				int servers1 = Integer.parseInt(data1[1]);
				System.out.println(servers1);
		
			
				String[] nRec = new String[servers1]; //SERVERS
		
				for (int i = 0; i < servers1; i++) {
					strServer = dis.readLine();
					nRec[i] = strServer;
					System.out.println("nRec: " + nRec[i]);
				}
		
				client = "OK\n";
		
				dout.write(client.getBytes());
				dout.flush();
		
				strServer = dis.readLine();
		
				String[] first1 = nRec[0].split(" "); //1st
		
				client = "SCHD " + count + " " + first1[0] + " " + first1[1] + "\n";
				
				dout.write(client.getBytes());
				dout.flush();
		
				strServer = dis.readLine();
				System.out.println("Message = " + strServer); 
				count++;
		
				
			}
			}
		}
		
	
		String quit = "QUIT\n";
		
		dout.write(quit.getBytes());
		dout.flush();
		
		strServer = dis.readLine();
		System.out.println("Message = " + strServer);
		
		dout.close();
		s.close();
	}
}

