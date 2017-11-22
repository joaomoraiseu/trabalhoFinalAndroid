package com.eworld.harasfal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eworld.harasfal.Asyncs.PostRequestAsyncTask;
import com.eworld.harasfal.Classes.DadosEmpresa;
import com.eworld.harasfal.Classes.Pessoa;
import com.eworld.harasfal.Interface.OnPostExecute;
import com.eworld.harasfal.Utils.MaskUtil;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Arrays;


public class CadastroActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Type type2;
    Pessoa pessoa;
    LoginButton loginButton;
    CallbackManager callbackManager;
    PostRequestAsyncTask async2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            FacebookSdk.sdkInitialize(this.getApplicationContext());
            // AppEventsLogger.activateApp(this);
            if (BuildConfig.DEBUG) {
                FacebookSdk.setIsDebugEnabled(true);
                FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
            }

        } catch (Exception e) {

        }
        setContentView(R.layout.cadastro_activity);
        GetPessoa();
        callbackManager = CallbackManager.Factory.create();
        // Facebook
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                Profile profile = Profile.getCurrentProfile();
                                String picture = "";
                                String perfilface = "";

                                if (profile != null) {
                                    if(profile.getProfilePictureUri(100, 100).toString()!=null)
                                        picture = profile.getProfilePictureUri(100, 100).toString();
                                    if(profile.getLinkUri().toString()!=null)
                                        perfilface = profile.getLinkUri().toString();

                                }

                                pessoa.setLinkFoto(picture);
                                pessoa.setPerfilFace(perfilface);

                                try {
                                    pessoa.setNome(object.getString("name"));
                                } catch (JSONException e) {
                                    pessoa.setNome("");
                                    e.printStackTrace();
                                }
                                try {
                                    pessoa.setEmail(object.getString("email"));
                                } catch (JSONException e) {
                                    pessoa.setEmail("");
                                    e.printStackTrace();
                                }
                                if(pessoa.getLinkFoto()==null)
                                    pessoa.setLinkFoto("");
                                /*AlertDialog alerta;
                                AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);
                                builder.setTitle("Identificador "+pessoa.getIdentificador());
                                builder.setMessage("Nome "+pessoa.getNome()+"\n"+"Email "+pessoa.getEmail()+"\n"+"LinkFoto "+pessoa.getLinkFoto()+"\n"+"PerfilFace "+pessoa.getPerfilFace());
                                builder.setPositiveButton(DadosEmpresa.UnidadeID, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {

                                    }
                                });
                                alerta = builder.create();
                                alerta.show();*/
                                MakeRequest();

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();



            }

            @Override
            public void onCancel() {
                //Toast toast = Toast.makeText(getBaseContext(), "Erro", Toast.LENGTH_LONG);
               // toast.show();

            }

            @Override
            public void onError(FacebookException exception) {

                Toast toast = Toast.makeText(getBaseContext(), exception.toString(), Toast.LENGTH_LONG);
                toast.show();
                finish();
            }
        });

        Button btn_fb_login = (Button) findViewById(R.id.btn_fb_login);
        btn_fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(CadastroActivity.this, Arrays.asList("public_profile", "email"));

            }
        });
        Button btn_enviar = (Button) findViewById(R.id.btn_enviar);
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etnome = (EditText)findViewById(R.id.et_nome);
                EditText etemail = (EditText)findViewById(R.id.et_email);
                if(etnome.getText().toString().isEmpty()&& etemail.getText().toString().isEmpty()){
                    Toast.makeText(CadastroActivity.this,"Existem campos vazios.",Toast.LENGTH_SHORT).show();
                }else{

                    if (MaskUtil.isValidEmail(etemail.getText().toString())) {
                        pessoa.setNome(etnome.getText().toString());
                        pessoa.setEmail(etemail.getText().toString());
                       // pessoa.setCelular(et_cel.getText().toString());
                        pessoa.setLinkFoto("");
                        pessoa.setPerfilFace("");
                        MakeRequest();
                    }else Toast.makeText(CadastroActivity.this,"Email inválido.",Toast.LENGTH_SHORT).show();
                }
            }
        });
       /* SetupGooglePlus();
        Button btn_google_login = (Button) findViewById(R.id.btn_google_login);
        btn_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               signIn();
            }
        });*/
        /*SetupGooglePlus();

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        setGooglePlusButtonText(signInButton,"Entrar com Google Plus");
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });*/

    }
    private void MakeRequest() {
        async2 = new PostRequestAsyncTask(CadastroActivity.this, "Cliente/NovoCliente");
        async2.AddParam("UnidadeId", DadosEmpresa.UnidadeID);
        async2.AddParam("Identificador", pessoa.getIdentificador());
        async2.AddParam("Nome", pessoa.getNome());
        async2.AddParam("Email", pessoa.getEmail());
        async2.AddParam("Celular", "");
        if(pessoa.getLinkFoto()==null)
            pessoa.setLinkFoto("");
        async2.AddParam("LinkFoto", pessoa.getLinkFoto());
        async2.AddParam("PerfilFace", pessoa.getPerfilFace());

        async2.setOnPostExecute(new OnPostExecute() {
            @Override
            public void OnPostExecute() {
                if (async2.getResponse().equals("true")) {
                    pessoa.setCliente(true);
                    SetPessoa();
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Ocorreu um problema, tente novamente...", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        async2.execute();
    }
    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

    }
    // [END onActivityResult]



    @Override
    protected void onPause() {
        super.onPause();
        //OneSignal.onPaused();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //OneSignal.onResumed();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginManager.getInstance().logOut();
    }


/*
    private String GetTelephoneNumber() {

        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (tMgr != null) {
            return tMgr.getLine1Number();
        }
        return "Não encontrado";


    }*/

    private void GetPessoa() {
        preferences = getSharedPreferences(getResources().getString(R.string.sharedpreferences), 0);
        editor = preferences.edit();
        String json = preferences.getString("Pessoa", null);
        type2 = new TypeToken<Pessoa>() {
        }.getType();
        pessoa = (Pessoa) new GsonBuilder().create().fromJson(json, type2);

    }


    private void SetPessoa() {

        //pessoa = (Pessoa) new GsonBuilder().create().fromJson(async2.getResponse(), type2);
        Gson gson = new Gson();
        String p = gson.toJson(pessoa);
        editor.putString("Pessoa", p);
        editor.commit();

    }

}
