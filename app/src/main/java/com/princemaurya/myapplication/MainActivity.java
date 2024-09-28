package com.princemaurya.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView chatsRV;
    private EditText userMsgEdt;
    private FloatingActionButton sendMsgFAB;

    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";

    private ArrayList<ChatsModal> chatsModalArrayList;
    private ChatRVAdapter chatRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                addResponse(userMsgEdt.getText().toString());
                userMsgEdt.setText("");

            }
        });
    }
    private void addResponse(String response){
        chatsModalArrayList.add(new ChatsModal(response,USER_KEY));
        chatRVAdapter.notifyDataSetChanged();

        String url="";
        String BASE_URL="";

        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI=retrofit.create(RetrofitAPI.class);
        Call<MessageModal> call=retrofitAPI.getMessage(url);
        call.enqueue(new Callback<MessageModal>() {
            @Override
            public void onResponse(Call<MessageModal> call, Response<MessageModal> response) {
                if(response.isSuccessful()){
                    MessageModal modal=response.body();
                    chatsModalArrayList.add(new ChatsModal(modal.getResponse(),BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MessageModal> call, Throwable t) {
                chatsModalArrayList.add(new ChatsModal("Please revert your question",BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();
            }
        });
    }

}