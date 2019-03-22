package integracionip.impresoras;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import integracionip.impresoras.MainActivity;
import integracionip.impresoras.R;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class MessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "recaudos";
    private static final int NOTIFICATION_ID = 999;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    public void showNotification(String title, String message) {
        //inicia app
        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //sync
        Intent syncIntent = new Intent(this, BroadcastReceiver.class);
        syncIntent.putExtra("action", "sync");
        PendingIntent syncPendingIntent = PendingIntent.getBroadcast(this, 0, syncIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setContentIntent(resultPendingIntent)
                .addAction(R.drawable.ic_baseline_autorenew_24px, "Sincronizar", syncPendingIntent)
                .setOngoing(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}