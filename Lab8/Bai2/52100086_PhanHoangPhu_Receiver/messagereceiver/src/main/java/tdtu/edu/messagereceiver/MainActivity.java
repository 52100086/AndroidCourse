package tdtu.edu.messagereceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;

import com.phule.messagereceiver.R;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver messageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, new IntentFilter("com.phule.SENDER_ACTION"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
    }
}