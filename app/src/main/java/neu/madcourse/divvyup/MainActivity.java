package neu.madcourse.divvyup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

import neu.madcourse.divvyup.chores_list_screen.ChoresActivity;
import neu.madcourse.divvyup.login.LoginActivity;
import neu.madcourse.divvyup.recap.RecapActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private final String CHANNEL_ID = "test_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        this.database = FirebaseDatabase.getInstance();
        createNotificationChannel();
        Button testNotificationsButton = findViewById(R.id.testButton);

        Button loginButton = findViewById(R.id.LoginButton);
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loginIntent);
            }
        });

        Button ChoresActivity = findViewById(R.id.choresActivityButton);
        Intent choresActivityIntent = new Intent(this, ChoresActivity.class);
        ChoresActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(choresActivityIntent);
            }
        });


        Button ProgressActivity = findViewById(R.id.progressActivityButton);
        Intent progressActivityIntent = new Intent(this, RecapActivity.class);
        ProgressActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(progressActivityIntent);
            }
        });


        Intent testDBActivityIntent = new Intent(this, DatabaseTestActivity.class);
        testNotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(testDBActivityIntent);
//                doSendToDB();
            }
        });


//        database.getReference().child("Users").addChildEventListener(
//                new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                            sendNotification();
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                }
//        );

    }

//    private void doSendToDB(){
//        DatabaseReference ref = this.database.getReference().child("Users");
//        DatabaseReference newPostRef = ref.push();
//        newPostRef.setValue("New User");
//    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Notification Name",
                            NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("test channel description");



            NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        String contentTitle = "Divvy Up";
        builder.setContentTitle(contentTitle);
        builder.setContentText("Welcome to Divvy Up!");
        builder.setSmallIcon(R.drawable.div);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(7, builder.build());
    }

}