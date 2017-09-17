package br.livro.android.cap20.push;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * A API do Google Clould Messaging está deprecated, e agora o Google Play
 * Services deve ser utilizado.
 * 
 * A antiga classe helper "GCMRegistrar" está deprecated, deve-se utilizar a
 * classe "GoogleCloudMessaging".
 * 
 * Essa classe GCM estava no exemplo do cap sobre GCM do Livro Android 3ª
 * edição.
 * 
 * Ela contém exatamente os mesmos métodos publicos e não precisa alterar as
 * activities que a utilizam.
 * 
 * Somente internamente as chamadas dos métodos foram atualizadas para a nova
 * classe "GoogleCloudMessaging".
 * 
 * Com apenas uma excessão: A antiga classe "GCMRegistrar" armazenava
 * automaticamente o registrionId nas prefs, mas agora você deve fazer isso
 * manualmente. Depois de chamar o método GCM.registrar(context, projectId) você
 * deve pegar o registrationId enviar para seu servidor e depois salvar o
 * registrationId nas prefs com o método GCM.setRegistrationId(context,
 * registrationId). Assim da próxima vez você saberá que o GCM está ativado
 * utilizando o método GCM.isAtivo();
 * 
 * Na activity <b>ExemploPushActivity</b> demonstrada no livro o único método
 * que precisa alterar é este:
 * 
 * <pre>
 * private OnClickListener onClickRegistrar() {
 * 	return new OnClickListener() {
 * 		&#064;Override
 * 		public void onClick(View v) {
 * 			// Registrar
 * 			exibirMensagem(&quot;Solicitação de registro disparada.&quot;);
 * 			String regId = GCM.registrar(getContext(), Constants.PROJECT_NUMBER);
 * 			// Envie o registration id para o servidor (asynctask)
 * 			exibirMensagem(&quot;RegId: &quot; + regId);
 * 			// Salve-o nas preferências
 * 			GCM.setRegistrationId(getContext(), regId);
 * 		}
 * 	};
 * }
 * </pre>
 * 
 * @author Ricardo Lecheta
 * 
 */
public class GCM {
	private static final String TAG = "gcm";
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";

	// Preferências para salvar o registration id
	private static SharedPreferences getGCMPreferences(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("livroandroid_gcm", Context.MODE_PRIVATE);
		return sharedPreferences;
	}

	// Retorna o registration id salvo nas preferências
	public static String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId == null || registrationId.trim().length() == 0) {
			// Não registrado ainda
			return "";
		}
		return registrationId;
	}

	// Salva o registration id nas preferências
	public static void saveRegistrationId(Context context, String registrationId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, registrationId);
		editor.commit();
	}

	// Utiliza a classe GoogleCloudMessaging para registrar no serviço do GCM
	public static String registrar(Context context, String projectNumber) {
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
		try {
			Log.d(TAG, ">> GCM.registrar(): " + projectNumber);
			String registrationId = gcm.register(projectNumber);
			Log.d(TAG, "<< GCM.registrar() OK, registration id: " + registrationId);
			return registrationId;
		} catch (IOException e) {
			Log.e(TAG, "<< GCM.registrar() ERRO: " + e.getMessage(), e);
		}
		return null;
	}

	// Utiliza a classe GoogleCloudMessaging para cancelar o registro no serviço do GCM
	public static void cancelar(Context context) {
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
		try {
			gcm.unregister();
			saveRegistrationId(context, null);
			Log.d(TAG, "GCM cancelado com sucesso.");
		} catch (IOException e) {
			Log.e(TAG, "GCM erro ao desregistrar: " + e.getMessage(), e);
		}
	}

	// Verifica se está registrado no serviço do GCM
	public static boolean isAtivo(Context context) {
		String registrationId = getRegistrationId(context);
		if (registrationId == null || registrationId.trim().length() == 0) {
			return false;
		}
		return true;
	}
}