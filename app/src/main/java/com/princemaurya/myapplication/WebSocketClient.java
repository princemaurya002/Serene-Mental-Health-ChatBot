package com.princemaurya.myapplication;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketClient extends WebSocketListener {
    private WebSocket webSocket;

    public WebSocketClient() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://10.0.2.2:5001").build();  // Replace with your server IP
        webSocket = client.newWebSocket(request, this);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        System.out.println("Received from server: " + text);  // Handle the message received from the server
    }

    public void sendMessage(String message) {
        webSocket.send(message);
//        Toast.makeText(this, ""+message+" Sent ", Toast.LENGTH_SHORT).show();// Send message to the server
        Log.d("message", "Sent "+ message);
    }

    @Override
    public void onOpen(WebSocket webSocket, okhttp3.Response response) {
        super.onOpen(webSocket, response);
        System.out.println("Connection opened");

    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
        System.out.println("Connection closing: " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
        Log.e("Error","${t.localizedMessage}");
        t.printStackTrace();
    }
}


