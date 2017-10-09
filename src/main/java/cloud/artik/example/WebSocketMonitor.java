package cloud.artik.example;

import cloud.artik.client.ApiException;
import cloud.artik.model.Acknowledgement;
import cloud.artik.model.ActionOut;
import cloud.artik.model.MessageOut;
import cloud.artik.model.WebSocketError;
import cloud.artik.websocket.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;


/**
* WebSocket starter code to connect to ARTIK Cloud Firehose (/live) endpoint.
* 
* ARTIK Cloud Java SDK:
* https://github.com/artikcloud/artikcloud-java
* 
* Additional information available in our documentation:
* https://developer.artik.cloud/documentation/api-reference/websockets-api.html
* 
*/


public class WebSocketMonitor {
	
	private static final int MIN_EXPECTED_ARGUMENT_NUMBER = 4;
	
	static ArrayList<String> queryParams = new ArrayList<String>();

	// comma delimited string of `device ID` of interest
	static String sdids = null;     
		
	// here we are using the `user token` with sdids.  Or use `device token` associated device ID.
	static String accessToken = null;
		
	// user ID associated with the access token.
	static String uid = null;	
	
	// single `device ID` of interest wen
	static String deviceId =  null;
	
	// parameters below not used in this sample and set to null
	// for use when monitoring by device type
	static String sdtids = null;    //comma delimited string of `device type ID` of interest
	
	
	public static void main(String args[]) throws URISyntaxException, IOException, ApiException {
		
		if (!succeedParseCommand(args)) {
            printUsage();
             return;
        }
		
		// sets the parameters and callback function for the WebSocket connection
		// must pass one of "one of deviceId, sdids, or uid" with user token"
		FirehoseWebSocket ws = 
				new FirehoseWebSocket(accessToken, deviceId, sdids, sdtids, uid, new ArtikCloudWebSocketCallback() {
			

			@Override
			public void onOpen(int code, String message) {
				System.out.println(String.format("Connection successful with code:[%d]", code));
			}
			
			@Override
			public void onClose(int arg0, String arg1, boolean arg2) {
				System.out.println(String.format("Connction closed with [%d][%s][%b]", arg0, arg1, arg2));
			}
		
			@Override
			public void onMessage(MessageOut message) {
				System.out.println(String.format("Received message:[%s]", message));
			}
			
			@Override
			public void onAction(ActionOut actionOut) {
				System.out.println(String.format("Received action:[%s]", actionOut));
			}
			
			@Override
			public void onPing(long ts) {
				System.out.println(String.format("Received ping with ts:[%d]", ts));
			}
			
			@Override
			public void onError(WebSocketError error) {
				System.out.println(String.format("Received error:[%s]", error));
			}
			
			@Override
			public void onAck(Acknowledgement acknowledgement) {
				System.out.println(String.format("Received Ack [%s]", acknowledgement));
			}
			
		});
		
		
		System.out.println(String.format("Connecting to: wss://api.artik.cloud/v1.1/live?authorization=bearer+%s", accessToken));
		
		System.out.println("With query parameters:");
		
		for(String params: queryParams ) {
			 System.out.println("  "  + params);
		}
		
		// fires the request to make WebSocket connection
		ws.connect();
				
		System.out.println("Status: " + ws.getConnectionStatus());
		
		
	}
	
	private static boolean succeedParseCommand(String args[]) {
	       if (args.length < MIN_EXPECTED_ARGUMENT_NUMBER) {
	           return false; 
	       }
	       
	       int index = 0;
	       while (index < args.length) {
	           String arg = args[index];
	           
	           if ("-sdids".equals(arg)) {
	               ++index; // Move to the next argument, value for parameter
	               sdids = args[index];
	           } else if ("-token".equals(arg)) {
	               ++index; // Move to the next argument, value for parameter
	               accessToken = args[index];
	           } else if ("-device".equals(arg)) {
	        	   ++index; // Move to the next argument, value for parameter 
	        	   deviceId = args[index];
	           } else if ("-uid".equals(arg)) {
	        	   ++index; // Move to the next argument, value for parameter 
	        	   uid = args[index];
	           } else {
	        	   	        	   
	        	   return false;
	           }
	           
	           //store as query param format for printing without the '-' flag prefix
	           queryParams.add(arg.substring(1) + "=" + args[index]);
	           
	           ++index;
	       }
	       
	       // must provide a token
	       if (accessToken == null ) {
	    	   System.out.println("access token null");
	           return false;
	       }
	       
	       // one of device (device id), sdids (list of device ids), uid (user id) must be set
	       if (deviceId == null && sdids == null && uid == null) {
	    	   
	    	   System.out.println("device id or sdids null");
	    	   return false;
	       }
	       
	       return true;
	   }
	
	private static void printUsage() {
	       System.out.println("Usage: java -jar websocket-monitor-x.x.jar" + " -sdids YOUR_DEVICE_ID -token YOUR_USER_TOKEN");
	      
	       System.out.println("One of following flag:");
	       System.out.println("       -device to filter by single DEVICE_ID");
	       System.out.println("       -sdids  to filter by multiple DEVICE_IDs, comma delimited (ie:  id1, id2, id3)");
	       System.out.println("       -uid    to filter all devices of USER_ID");
	       
	       System.out.println("With token");
	       System.out.println("       -token USER_TOKEN (required), allows also DEVICE_TOKEN when used with -device flag");
	       
	}

}

