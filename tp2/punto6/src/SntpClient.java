import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * NtpClient - an NTP client for Java.  This program connects to an NTP server
 * and prints the response to the console.
 * 
 * The local clock offset calculation is implemented according to the SNTP
 * algorithm specified in RFC 2030.  
 * 
 * Note that on windows platforms, the curent time-of-day timestamp is limited
 * to an resolution of 10ms and adversely affects the accuracy of the results.
 * 
 * 
 * This code is copyright (c) Adam Buckley 2004
 *
 * This program is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free 
 * Software Foundation; either version 2 of the License, or (at your option) 
 * any later version.  A HTML version of the GNU General Public License can be
 * seen at http://www.gnu.org/licenses/gpl.html
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
 * more details.
 *  
 * @author Adam Buckley
 */
public class SntpClient
{
	public static void main(String[] args) throws IOException
	{
		String serverName = "2.ar.pool.ntp.org";
		
		/*
		// Process command-line args
		if(args.length==1)
		{
			serverName = args[0];
		}
		else
		{
			printUsage();
			return;
		}
		*/
		
		// Send request
		DatagramSocket socket = new DatagramSocket();
		InetAddress address = InetAddress.getByName(serverName);
		byte[] buf = new NtpMessage().toByteArray();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 123);
		
		// Set the transmit timestamp *just* before sending the packet
		// ToDo: Does this actually improve performance or not?
		NtpMessage.encodeTimestamp(packet.getData(), 40,(System.currentTimeMillis()/1000.0) + 2208988800.0);
		
		socket.send(packet);
		
		
		// Get response
		System.out.println("NTP request sent, waiting for response...\n");
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);
		
		// Immediately record the incoming timestamp
		double destinationTimestamp = (System.currentTimeMillis()/1000.0) + 2208988800.0;
		
		
		// Process response
		NtpMessage msg = new NtpMessage(packet.getData());
		
		// Corrected, according to RFC2030 errata
		double roundTripDelay = (destinationTimestamp-msg.originateTimestamp) -
			(msg.transmitTimestamp-msg.receiveTimestamp);
			
		double localClockOffset =
			((msg.receiveTimestamp - msg.originateTimestamp) +
			(msg.transmitTimestamp - destinationTimestamp)) / 2;
		
		
		// Display response
		System.out.println("NTP server: " + serverName);
		System.out.println(msg.toString());
		
		System.out.println("Dest. timestamp:     " +
			NtpMessage.timestampToString(destinationTimestamp));
		
		System.out.println("Round-trip delay: " +
			new DecimalFormat("0.00").format(roundTripDelay*1000) + " ms");
		
		System.out.println("Local clock offset: " +
			new DecimalFormat("0.00").format(localClockOffset*1000) + " ms");
		
		ArrayList<String> list = new ArrayList<String>();
		String column[] = { "NTP server: ",
				
							"Leap indicator: ",
							"Mode: ",
							"Stratum: ",
							"Poll: ",
							"Precision: ",
							"Root delay: ",
							"Root dispersion: ",
							"Reference identifier: ",
							"Reference timestamp: ",
							"Originate timestamp: ",
							"Receive timestamp: ",
							"Transmit timestamp: ",
							
							"Dest. timestamp:",
							"Round-trip delay: ",
							"Local clock offset: "};
		
		

		
		String mensaje = msg.toString();
		String[] arrOfStr = mensaje.split("\n");
		
		list.add(serverName);
	    for (String string : arrOfStr) {
	    	list.add(string.split(":",2)[1]);
		}
	    list.add(NtpMessage.timestampToString(destinationTimestamp));
	    list.add(new DecimalFormat("0.00").format(roundTripDelay*1000) + " ms");
	    list.add(new DecimalFormat("0.00").format(localClockOffset*1000) + " ms");
		
	    
	    
        String str[] = new String[list.size()]; 
        for (int j = 0; j < list.size(); j++) { 
             str[j] = list.get(j); 
        } 
	    
	    String[][] ultimo = new String[1][str.length];
	    ultimo[0] = str ;
	    
	    
		Tabla t = new Tabla(column, ultimo ) ;
		
		
		
		
		
		socket.close();
	}
	
	
	
	/**
	 * Prints usage
	 */
	static void printUsage()
	{
		System.out.println(
			"NtpClient - an NTP client for Java.\n" +
			"\n" +
			"This program connects to an NTP server and prints the response to the console.\n" +
			"\n" +
			"\n" +
			"Usage: java NtpClient server\n" +
			"\n" +
			"\n" +
			"This program is copyright (c) Adam Buckley 2004 and distributed under the terms\n" +
			"of the GNU General Public License.  This program is distributed in the hope\n" +
			"that it will be useful, but WITHOUT ANY WARRANTY; without even the implied\n" +
			"warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU\n" +
			"General Public License available at http://www.gnu.org/licenses/gpl.html for\n" +
			"more details.");
					
	}
}
