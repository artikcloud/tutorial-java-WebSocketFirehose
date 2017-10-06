package cloud.artik.example;

import cloud.artik.client.ApiException;
import cloud.artik.model.Acknowledgement;
import cloud.artik.model.ActionOut;
import cloud.artik.model.MessageOut;
import cloud.artik.model.WebSocketError;
import cloud.artik.websocket.*;

import java.io.IOException;
import java.net.URISyntaxException;


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
	
	private static final int EXPECTED_ARGUMENT_NUMBER = 4;

	// comma delimited string of `device ID` of interest
	static String sdids = null;     
		
	// here we are using the `user token` with sdids.  Or use `device token` associated device ID.
	static String accessToken = null;
	
	// parameters below not used in this sample and set to null
	// used when monitoring by device type
	static String sdtids = null;    //comma delimited string of `device type ID` of interest
			
	// user ID associated with the access token.
	static String uid = null;	
	
	// single `device ID` of interest wen
	static String deviceId =  null;
	
	
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
		
		
		System.out.println(String.format("Connecting to: wss://api.artik.cloud/v1.1/live?authorization=bearer+%s&sdids=%s", accessToken, sdids));
		
		// fires the request to make WebSocket connection
		ws.connect();	
		
		System.out.println("Status: " + ws.getConnectionStatus());
		
		
	}
	
	private static boolean succeedParseCommand(String args[]) {
	       if (args.length != EXPECTED_ARGUMENT_NUMBER) {
	           return false; 
	       }
	       int index = 0;
	       while (index < args.length) {
	           String arg = args[index];
	           System.out.println ("arg is: " + arg);
	           
	           if ("-sdids".equals(arg)) {
	               ++index; // Move to the next argument the value of device id
	               
	               System.out.println("sdid set");
	               sdids = args[index];
	           } else if ("-token".equals(arg)) {
	               ++index; // Move to the next argument the value of device token
	               
	               System.out.println("token set");
	               accessToken = args[index];
	           } else if ("-sdid".equals(arg)) {
	        	   
	        	   System.out.println("did set");
	        	   ++index; // Move to the next argument 
	        	   deviceId = args[index];
	           } else if ("-uid".equals(arg)) {
	        	   
	        	   ++index; // Move to the next argument 
	        	   uid = args[index];
	           }
	           
	           ++index;
	       }
	       
	       // must provide a token
	       if (accessToken == null ) {
	    	   System.out.println("access token null");
	           return false;
	       }
	       
	       // must provide one of sdid (device id), sdids, uid must be set
	       if (deviceId == null && sdids == null && uid == null) {
	    	   
	    	   System.out.println("device id or sdids null");
	    	   return false;
	       }
	       
	       return true;
	   }
	
	private static void printUsage() {
	       System.out.println("Usage: websocket-monitor" + " -sdids YOUR_DEVICE_IDS -token USER_TOKEN");
	      
	       System.out.println("       -sdids comma delimited containing 1 or more device ids (ie:  id1, id2, id3)");
	       System.out.println("       -token USER_TOKEN");
	       
	   }

}

