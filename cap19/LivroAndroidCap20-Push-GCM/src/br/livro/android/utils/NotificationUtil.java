package br.livro.android.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import br.livro.android.cap20.push.R;

/**
 * @author Ricardo Lecheta
 * 
 */
public class NotificationUtil {
	static int ID = R.drawable.ic_launcher;

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 * 
	 * @param notificationIntent
	 */
	@SuppressLint("NewApi")
	public static void generateNotification(Context context, String message,
			Intent notificationIntent) {

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		String title = context.getString(R.string.app_name);
		Notification.Builder builder = new Notification.Builder(context)
				.setContentTitle(message).setContentText(title)
				.setContentIntent(intent).setSmallIcon(R.drawable.ic_launcher);

		Notification notification = builder.build();

		// Configura a intent para abrir a activity no topo, somente se não
		// estiver aberta
		// notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
		// Intent.FLAG_ACTIVITY_SINGLE_TOP);

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);
	}
}
