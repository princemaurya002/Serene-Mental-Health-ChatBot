package com.princemaurya.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView chatsRV;
    private EditText userMsgEdt;
    private WebSocketClient webSocketClient;
    private FloatingActionButton sendMsgFAB;

    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";

    private ArrayList<ChatsModal> chatsModalArrayList;
    private ChatRVAdapter chatRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webSocketClient = new WebSocketClient();
        chatsRV=findViewById(R.id.idRVChats);
        userMsgEdt=findViewById(R.id.idEdtMessage);
        sendMsgFAB=findViewById(R.id.idFABSend);

        chatsModalArrayList=new ArrayList<>();
        chatRVAdapter=new ChatRVAdapter(chatsModalArrayList,this);
        LinearLayoutManager manager =new LinearLayoutManager(this);
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatRVAdapter);



        sendMsgFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userMsgEdt.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter your message", Toast.LENGTH_SHORT).show();
                    return;
                }
                webSocketClient = new WebSocketClient();
                webSocketClient.sendMessage(userMsgEdt.getText().toString());
//                webSocketClient.onMessage();
                addResponse(userMsgEdt.getText().toString());
                userMsgEdt.setText("");

            }
        });
    }

    private void addResponse(String response){
        chatsModalArrayList.add(new ChatsModal(USER_KEY,response));
        chatRVAdapter.notifyDataSetChanged();
    }

}