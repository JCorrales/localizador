package py.una.sgf.localizador.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import py.una.sgf.localizador.R;
import py.una.sgf.localizador.service.LocalizadorService;
import py.una.sgf.localizador.service.ServiceResponse;
import py.una.sgf.localizador.service.SgfService;
import py.una.sgf.localizador.util.AsyncCallback;

public class RegistroActivity extends AppCompatActivity{
    private SgfService sgfService = new SgfService();
    ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
    }

    private void iniciarServicio(String cedula, String serverIp){
        Intent intent = new Intent(this, LocalizadorService.class);
        intent.putExtra("cedula", cedula);
        intent.putExtra("serverIp", serverIp);

        startService(intent);
        RegistroActivity.this.finish();
    }

    public void sendData(View view){
        EditText cedulaEditText = (EditText)findViewById(R.id.cedulaEditText);
        EditText serverIpEditText = (EditText) findViewById(R.id.serverIpEditText);

        final String cedula = cedulaEditText.getText().toString();
        final String serverIp = serverIpEditText.getText().toString();

        AsyncCallback<ServiceResponse<String>> sendDataCalllBack = new AsyncCallback<ServiceResponse<String>>() {
            @Override
            public void onComplete(boolean success, ServiceResponse<String> result, Throwable caught) {
                if(success){
                    try{
                        iniciarServicio(cedula, serverIp);
                    }catch(Exception ex){
                        Toast.makeText(getApplicationContext(), "Error en localizador: "+ex.getMessage() , Toast.LENGTH_LONG);
                    }
                }else{
                    if(result != null){
                        Toast.makeText(getApplicationContext(),
                                "Error: "+result.getMessage(), Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),
                                "Error: No se pudo conectar con el servidor", Toast.LENGTH_LONG).show();
                    }
                }
                progressDialog.dismiss();
            }
        };


        progressDialog.setMessage("Espere");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        sgfService.sendData(sendDataCalllBack, cedula, serverIp);
    }

}
