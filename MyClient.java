import java.io.*;
import java.net.*;



public class MyClient {
	public static void main(String[] args) throws Exception{
	try{
		Socket s=new Socket("localhost",50000);
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		BufferedReader dis=new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		String S_Message;
		String C_Message = "HELO\n";
		
		dout.write(C_Message.getBytes());
		dout.flush();

		S_Message = dis.readLine();
		System.out.println("message= "+S_Message);

		String username = System.getProperty("user.name");
		C_Message = "AUTH "+username+"\n";
		dout.write(C_Message.getBytes());
		dout.flush();

		S_Message = dis.readLine();
		System.out.println("message= "+S_Message);
		
		C_Message = "REDY\n";
		dout.write(C_Message.getBytes());
		dout.flush();
		
		S_Message = dis.readLine();
		System.out.println("message= "+S_Message);
		
		
		String Server_Data;
		int job_count = 1;
		int[] Servers_Array;
		int max_Cores = 0;
		String Server_Name = "";
		int Server_Count = 0;
		
		

		C_Message = "GETS All\n";
     		dout.write(C_Message.getBytes());		
     		dout.flush();

     		S_Message = dis.readLine();
     		System.out.println("message= "+S_Message);
     		
     		Server_Data = S_Message;
     		System.out.println("Servers Data = " + Server_Data);
     		int index1 = Server_Data.indexOf("DATA") + 5;
     		int index2 = Server_Data.indexOf(' ',Server_Data.indexOf(' ') + 1);
     		String nRecs = Server_Data.substring(index1, index2);
     		System.out.println("Number of Servers = " + nRecs);
     		int nRecs_Int = Integer.parseInt(nRecs);
     		
     	
     		Servers_Array = new int[nRecs_Int];
     			
     		C_Message = "OK\n";
     		dout.write(C_Message.getBytes());
     		dout.flush();

			
     			
			
     		String[] S_Array = new String[nRecs_Int];
     		int i = 0;
     		
     		while (i < nRecs_Int) {
     			S_Message = dis.readLine();
     			S_Array[i] = S_Message;
     			System.out.println("S_Array: " + S_Array[i]);
     			i++;
     		}
     		
     		int[] coreNum = new int[nRecs_Int];
     		String[] cores;
     		
     		for (int k = 0; k < Servers_Array.length; k++) {
     			cores = S_Array[k].split(" ");
     			coreNum[k] = Integer.parseInt(cores[4]);
     			
     		}
     		
     		for (int j = 0; j < coreNum.length; j++) {
     			if (coreNum[j] > coreNum[0]) {
     				max_Cores = j;
     				
     			}
     		}
     		
     		
     		String[] temp = S_Array[max_Cores].split(" ");
     		Server_Name = temp[0];
     		
     		for (int r = 0; r < S_Array.length; r++) {
     			if (S_Array[r].contains(Server_Name)) {
     				Server_Count++;
     			}
     		}
     		
     		int Server_id = 0;
     		
     		System.out.println(Server_Name);
     		System.out.println(Server_Count);
     		
     		C_Message = "OK\n";
     		dout.write(C_Message.getBytes());
     		dout.flush();	
     		
     		S_Message = dis.readLine();
     		System.out.println(S_Message);
     		
     		
     		C_Message = "SCHD 0 " + Server_Name + " 0\n";
     		dout.write(C_Message.getBytes());
     		dout.flush();
     		S_Message = dis.readLine();
     		System.out.println(S_Message);

		while(!(S_Message.contains("NONE"))){
  
  			C_Message = "REDY\n";
  			dout.write(C_Message.getBytes());
  			dout.flush();
  
   			S_Message = dis.readLine();
   			System.out.println("message= "+S_Message);
			
			
  				if(S_Message.contains("JOBN")){    
     					C_Message = "SCHD " + job_count + " " + Server_Name + " " + Server_id + "\n";
     					dout.write(C_Message.getBytes());
     					dout.flush();
     					S_Message = dis.readLine();
     					System.out.println(S_Message);
     					job_count++;
     					Server_id++;
     					if (Server_id == Server_Count) {
     						Server_id = 0;
     					}
    
  				}
  			
		}
		
		C_Message = "QUIT\n";
		dout.write(C_Message.getBytes());
		dout.flush();
		
		S_Message = dis.readLine();
   		System.out.println("message= "+S_Message);
		
		dout.close();
		s.close();
		
		}
		catch(Exception e){System.out.println(e);}
	}
}
