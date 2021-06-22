package com.gevi.pinger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import gpjl.network.Client;

public class MainActivity extends AppCompatActivity {

    private Client client;

    private TextView messageTextView;
    private Button connectButton;
    private EditText messageEditText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListeners();

        client = new AppClient(getString(R.string.hostname), new IClient() {
            @Override
            public void readString(String string) {
                messageTextView.setText(string);
            }

            @Override
            public void readObject(Object obj) {}

            @Override
            public void onDisconnect() {
                disconnect();
            }
        });
    }

    private void initViews() {
        messageTextView = findViewById(R.id.messageTextView);
        connectButton = findViewById(R.id.connectButton);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
    }

    private void initListeners() {
        connectButton.setOnClickListener(view -> onConnectClick());
        sendButton.setOnClickListener(view -> onSendClick());
    }

    private void runOnNewThread(Runnable action) {
        new Thread(action).start();
    }

    private void onConnectClick() {
        runOnNewThread(() -> {
            if(client.isConnected()) {
                disconnect();
                return;
            }
            if(client.connect(getResources().getInteger(R.integer.port)))
                runOnUiThread(() -> connectButton.setText(R.string.disconnect));
        });
    }

    private void onSendClick() {
        runOnNewThread(() -> {
            if(!client.isConnected())
                return;
            client.sendString(messageEditText.getText().toString());
            messageEditText.setText(null);
        });
    }

    private void disconnect() {
        runOnNewThread(() -> {
            if(client.isConnected())
                client.sendEnd();
            runOnUiThread(() -> connectButton.setText(R.string.connect));
        });
    }

}