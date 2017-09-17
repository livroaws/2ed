package br.livro.android.cap20.push;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import br.livro.android.utils.ActivityStackUtils;
import br.livro.android.utils.NotificationUtil;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * IntentService respons�vel por tratar as mensagens do GCM (Google Cloud Messaging).
 * 
 * O m�todo onRegistered � chamado quando o registro no GCM � feito com sucesso.
 * Neste momento temos o registrationId �nico do device.
 * 
 * O m�todo onMessage � chamado quando uma mensagem por push � recebida.
 * 
 * @author Ricardo Lecheta
 * 
 */
public class CopyOfGcmIntentService extends IntentService  {

	private static final String TAG = CopyOfGcmIntentService.class.getName();

	public CopyOfGcmIntentService() {
        super(Constants.PROJECT_NUMBER);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        
        Log.i(TAG,"GcmIntentService.onHandleIntent: " + extras);
        
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        
        // Verifica o tipo da mensagem
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  
        	// O extras.isEmpty() precisa ser chamado para ler o bundle
            
        	/*
             * Verifica o tipo da mensagem, no futuro podemos ter mais tipos
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
            	// Erro
                sendNotification("GCM MESSAGE_TYPE_SEND_ERROR: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
            	// Servidor deletou alguma mensagem que n�o precisa ser mais enviada
                sendNotification("GCM erro MESSAGE_TYPE_DELETED: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // Mensagem do tipo normal. Faz a leitura do par�metro "msg" enviado pelo servidor
            	String msg = extras.getString("msg");
            	sendNotification("onMessage: " + msg);
            }
        }

        // Libera o wake lock do WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Cria uma notifica��o com a mensagem recebida do GCM
	private void sendNotification(String msg) {
		Log.d(TAG, msg);

		// Se a aplica��o est� aberta
		if (ActivityStackUtils.isMyApplicationTaskOnTop(this)) {
			// Dispara uma Intent para o receiver configurado na Activity
			Intent intent = new Intent("RECEIVER_QUE_VAI_RECEBER_ESTA_MSG");
			intent.putExtra("msg", msg);
			sendBroadcast(intent);

		} else {
			// Cria a notifica��o, e informa para abrir a activity de entrada
			Intent intent2 = new Intent(this, ExemploPushActivity.class);
			intent2.putExtra("msg", msg);
			NotificationUtil.generateNotification(this, "Nova mensagem",intent2);
		}
			
	}
}
