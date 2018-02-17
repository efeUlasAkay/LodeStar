package com.lodestarapp.cs491.lodestar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.lodestarapp.cs491.lodestar.Models.WeatherInformation;

import static android.widget.Toast.LENGTH_LONG;


public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "consoleMessage";
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient googleSignInClient;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;

    private SignInButton signInButton;

    private Button signInWithEmail;

    // private TextView alreadySignedInQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        signInWithEmail = (Button) findViewById(R.id.button_email);

        //  alreadySignedInQuestion = (TextView) findViewById(R.id.login_text);
        // alreadySignedInQuestion.setOnClickListener(this);

//        signInWithEmail.setOnClickListener(
//                new Button.OnClickListener(){
//                    public void onClick(View v){
//
//                        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//                        startActivity(intent);
//                    }
//                }
//        );

        signInButton = (SignInButton) findViewById(R.id.GoogleSignInButton);
        signInButton.setOnClickListener(this);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener(){
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Error", LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Log.w(TAG, "Kobe0 ");
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null)
//            updateUI(currentUser);
//    }

    private void updateUI(FirebaseUser currentUser) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                Log.w(TAG, "Kobe1 ");
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.w(TAG, "Kobe2");
                firebaseAuthWithGoogle(account);
                Log.w(TAG, "Kobe3");
            }catch (ApiException e){
                Log.w(TAG, "Google sign in failed: " + e.getStatusCode());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    updateUI(firebaseUser);
                }
                else {
                    Log.w(TAG,"sign in failure" , task.getException());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
//        Toast.makeText(this,".(",Toast.LENGTH_LONG).show();
//        if(view == alreadySignedInQuestion) {
//            Toast.makeText(this,".(",Toast.LENGTH_LONG).show();
//            startActivity(new Intent(this,WeatherInformation.class));
//        }


        switch (view.getId()){
            case R.id.GoogleSignInButton:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /*private void signOut() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(false);
                    }
                }
        );
    }*/

    /*private void revokeAccess() {

    }*/

    //@Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    /**
     * Unity Try
     */
    public void unityGameStart(View view){
        Intent intent = new Intent(this, BrickBreakerActivity.class);
        startActivity(intent);
    }


    /**
     * Weather
     */
    public void weatherStart(View view){
        Intent intent = new Intent(this, WeatherInformationActivity.class);
        startActivity(intent);
    }

    public void tabStart(View view){
        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);
    }

    public void signUpStart(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void signUpPlease(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void meStart(View view){
        Intent intent = new Intent(this, UserPage.class);
        startActivity(intent);
    }



}
