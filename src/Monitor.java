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


public class Monitor {

	public static void main(String args[]) throws URISyntaxException, IOException, ApiException {
		
		// comma delimited string of `device ID` of interest
		String sdids = Config.DEVICE_ID;     
				
		// here we are using the `user token`.  Or use `device token` associated with above device ID.
		String accessToken = Config.USER_TOKEN;
		
		
		// parameters below not used in this sample and set to null
		// used when monitoring by device type
		String sdtids = null;    //comma delimited string of `device type ID` of interest
		
		// your `device ID` of interest
		String deviceId =  null;
		
		// user ID associated with the access token.
		String uid = null;		 

		// sets the parameters and callback function for the WebSocket connection
		FirehoseWebSocket ws = 
				new FirehoseWebSocket(accessToken, deviceId, sdids, sdtids, uid, new ArtikCloudWebSocketCallback() {
			

			@Override
			public void onOpen(int code, String message) {
				// TODO Auto-generated method stub
				
				System.out.println("< Connected!");
				
			}
			
			@Override
			public void onPing(long ts) {
				// TODO Auto-generated method stub
				
				System.out.println("< Received Ping: " + ts);
				
			}
			
			@Override
			public void onMessage(MessageOut message) {
				// TODO Auto-generated method stub
				
				System.out.println("< Received message: " + message);
				System.out.println("< message.getData(): " + message.getData());
			}
			
			@Override
			public void onError(WebSocketError error) {
				// TODO Auto-generated method stub
				
				System.out.println("< Error: " + error);
				
			}
			
			@Override
			public void onClose(int arg0, String arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
				System.out.println("Connction Closed.");
				
			}
			
			@Override
			public void onAction(ActionOut actionOut) {
				// TODO Auto-generated method stub
				
				System.out.println("< Received Action: " + actionOut);
				
			}
			
			@Override
			public void onAck(Acknowledgement acknowledgement) {
				// TODO Auto-generated method stub
				
				System.out.println("Received Ack: " + acknowledgement);
				
			}
			
		});
		
		// fires the request to make WebSocket connection
		ws.connect();
		System.out.println("> Status: " +  ws.getConnectionStatus() + " using " + Config.DEVICE_ID + "," + Config.USER_TOKEN + " ...");
		
	}
}

