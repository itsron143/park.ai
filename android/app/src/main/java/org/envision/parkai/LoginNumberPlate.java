package org.envision.parkai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.base.Charsets;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.aviran.cookiebar2.CookieBar;

import static com.google.common.hash.Hashing.sha1;

/**
 * Created by root on 12/3/19.
 */

public class LoginNumberPlate extends AppCompatActivity {

    private EditText collegename,collegeid,contact,password;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_numberplate);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final String a=user.getUid().toString();
        final String aid="A18"+a.substring(0,6);
        // final String aid=a;
        final String name=user.getDisplayName().toString();
        final String email=user.getEmail().toString();


        final String picURL=user.getPhotoUrl().toString();




        Button signup = (Button) findViewById(R.id.btn_sign_up);
        collegename = (EditText) findViewById(R.id.collegename);
        collegeid = (EditText) findViewById(R.id.collegeid);
        contact = (EditText) findViewById(R.id.contact);
        password = (EditText) findViewById(R.id.password);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((collegename.getText().toString().trim().equals("")) || (password.getText().toString().trim().equals("")) ||  (contact.getText().toString().trim().equals(""))) {
                    CookieBar.build(LoginNumberPlate.this)
                            .setTitle("Please Enter All Details")
                            .setMessage("Contact Developers in case of any error!")
                            .setDuration(5000)
                            .setBackgroundColor(R.color.red)
                            .show();
                } else {


                    LoginDetails2 details = new LoginDetails2(a,name,email,contact.getText().toString(), picURL,password.getText().toString(),collegename.getText().toString());
                    mDatabase.child("users").child(a).setValue(details);
                    finish();
                    startActivity(new Intent(LoginNumberPlate.this, MainActivity.class));

                }
            }
        });




    }

}
