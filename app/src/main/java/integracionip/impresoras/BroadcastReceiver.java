package integracionip.impresoras;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra("action");
        Toast.makeText(context, action,
                Toast.LENGTH_LONG).show();
    }
}
