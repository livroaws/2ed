package br.livro.android.cap20.push;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * @author Ricardo Lecheta
 * 
 */
public class CopyOfExemploPushActivity extends Activity {
	private static final String TAG = "push";

	// Receiver para receber a menagem do Service por Intent
	private final BroadcastReceiver mensagemReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String msg = intent.getExtras().getString("msg");
			if(msg != null) {
				exibirMensagem(msg);
			}
			boolean refresh = intent.getExtras().getBoolean("refresh");
			if(refresh) {
				configurarTela();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_exemplo_push);

		 // Verifica se o Play Services está instalado.
	    if (checkPlayServices()) {
	    	// Botões da tela
			findViewById(R.id.btnRegistrar).setOnClickListener(onClickRegistrar());
			findViewById(R.id.btnCancelar).setOnClickListener(onClickCancelar());

			// Configura a tela
			configurarTela();

	    	// Exibe o status da ativação
			boolean ativado = GCM.isAtivo(getContext());
			if (ativado) {
				String regId = GCM.getRegistrationId(getContext());
				exibirMensagem("Device já registrado: " + regId);
			} else {
				exibirMensagem("Device não registrado, clique no botão Registrar.");
			}

			// Configura o BroadcastReceiver para receber mensagens
			registerReceiver(mensagemReceiver, new IntentFilter("RECEIVER_QUE_VAI_RECEBER_ESTA_MSG"));

			// Se existe alguma mensagem enviada pela Notification, recebe aqui
			String msg = getIntent().getStringExtra("msg");
			if (msg != null) {
				exibirMensagem(msg);
			}
	    } else {
	    	// Exiba uma msg pro usuário
	    	Toast.makeText(this, "Você precisa do Google Play Services!", Toast.LENGTH_LONG).show();
	    }
	}

	/**
	 * Verifica se o Google Play Services está instalado
	 */
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	        	int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.e(TAG, "Este aparelho não suporta o Google Play Services.");
	            finish();
	        }
	        return false;
	    }
	    Log.d(TAG, "CheckPlayServices OK");
	    return true;
	}

	private void configurarTela() {
		boolean ativado = GCM.isAtivo(getContext());
		if (ativado) {
			// Já está registrado
			findViewById(R.id.btnRegistrar).setEnabled(false);
			findViewById(R.id.btnCancelar).setEnabled(true);
		} else {
			// Precisa registrar
			findViewById(R.id.btnRegistrar).setEnabled(true);
			findViewById(R.id.btnCancelar).setEnabled(false);
		}
	}

	private Context getContext() {
		return getApplicationContext();
	}

	private void exibirMensagem(final String msg) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				TextView text = (TextView) findViewById(R.id.tMsgRecebida);
				text.append(msg + "\n------------------------\n");
				Log.i(TAG,msg);				
			}
		});
	}


	private OnClickListener onClickRegistrar() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Thread ou AsyncTask aqui
				new Thread(){
					public void run() {
						// Registrar
						String regId = GCM.registrar(getContext(), Constants.PROJECT_NUMBER);
						if(regId != null && regId.trim().length() > 0) {
							// Envie o registration id para o servidor (asynctask)
							exibirMensagem("RegId: " + regId);
							// Salve-o nas preferências
							GCM.saveRegistrationId(getContext(), regId);
						} else {
							exibirMensagem("Erro ao regitrar");
						}
					}
				}.start();
			}
		};
	}

	private OnClickListener onClickCancelar() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				GCM.cancelar(getContext());
				exibirMensagem("Solicitação de cancelamento disparada.");
			}
		};
	}

	@Override
	protected void onDestroy() {
		// Cancela o receiver e encerra o serviço do GCM
		unregisterReceiver(mensagemReceiver);
		super.onDestroy();
	}
}
