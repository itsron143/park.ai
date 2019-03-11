package org.envision.parkai;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.aviran.cookiebar2.CookieBar;

/**
 * Created by root on 10/3/19.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 0;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private com.google.android.gms.common.SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private GoogleApiClient mGoogleApiClient;
    private Button button;
    private FirebaseAuth.AuthStateListener mAuthListener;
    CoordinatorLayout coordinatorLayout;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        final Intent intent = getIntent();








        // getSupportActionBar().hide();
        // button=(Button)findViewById(R.id.button);
        signInButton = (com.google.android.gms.common.SignInButton) findViewById(R.id.sign_in_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // signInButton.setVisibility(View.GONE);
                //  signOutButton.setVisibility(View.VISIBLE);
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if(user.getDisplayName() != null)
                        // nameTextView.setText("Hi " + user.getDisplayName().toString());
                        //emailTextView.setText(user.getEmail().toString());

                        //  Snackbar.make(relativeLayout, "snackbarText", Snackbar.LENGTH_LONG).show();
                        //signOut();
                        CookieBar.build(LoginActivity.this)
                                .setTitle("Welcome "+user.getDisplayName().toString()+"!")
                                .setMessage("Enjoy Aaruush! :D")
                                .setDuration(5000)
                                .setBackgroundColor(R.color.green)
                                .show();



                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                 /*   String a=user.getUid().toString();
                    a="a18"+a.substring(0,6);
                    final String aid=a;
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference().child("aaruushid");
                    //final TextView tw=(TextView) findViewById(R.id.t);
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(aid)) {
                                // run some code
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();

                            }
                            else
                            {
                                startActivity(new Intent(LoginActivity.this, LoginContact.class));
                                finish();
                            }*



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            databaseError.toException();
                        }
                    }); */

                    String login;
                    Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                            .getBoolean("isFirstRun", true);

                   /* if (isFirstRun) {
                        //show sign up activity
                        // startActivity(new Intent(MainActivity.this, LoginAuthCheck.class));
                        // Toast.makeText(LoginActivity.this, "Run only once", Toast.LENGTH_LONG)
                        //       .show();
                        login="false";

                    }
                    else
                    {
                        login = intent.getStringExtra("loginstatus");
                        if(login==null)
                        {
                            login="true";
                        }

                    }
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                            .putBoolean("isFirstRun", false).commit();






                    //   Toast.makeText(LoginActivity.this, login, Toast.LENGTH_LONG).show();


                    if(login.equals("false"))
                    {
                        startActivity(new Intent(LoginActivity.this, LoginAuthCheck.class));

                        finish();
                    }
                    else
                    {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();

                    }*/







                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        //finish();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {


                        Toast.makeText(LoginActivity.this, "Logout Successfully", Toast.LENGTH_LONG).show();

                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(LoginActivity.this, "2 Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "1Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}