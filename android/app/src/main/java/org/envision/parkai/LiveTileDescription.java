package org.envision.parkai;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.google.common.base.Charsets;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.aviran.cookiebar2.CookieBar;

import java.util.Locale;

import static com.google.common.hash.Hashing.sha1;

/**
 * Created by root on 11/3/19.
 */

public class LiveTileDescription extends AppCompatActivity {


    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tile_description);
        setTitle("Details");

        final TextView title1 = (TextView) findViewById(R.id.title);
        final TextView desc1 = (TextView) findViewById(R.id.desc);
        final TextView time1 = (TextView) findViewById(R.id.time);
        final TextView url1 = (TextView) findViewById(R.id.urldetails);

        Intent intent = getIntent();

        String details = intent.getStringExtra("id");
        String context = intent.getStringExtra("context");
        final String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String time = intent.getStringExtra("time");

        details=details.replace("\\n","\n");


        title1.setText(title);
        desc1.setText(desc);
        time1.setText(time);
        url1.setText(details);

        ImageView img=(ImageView) findViewById(R.id.img);

        Picasso.with(this).load(context).placeholder(R.drawable.logo).into(img);

       /* GlideApp
                .with(this)
                .load(context)

                .placeholder(R.drawable.logo)
                .into(img);*/


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final String a=user.getUid().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        Button pc=(Button) findViewById(R.id.password_change);


        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                mDatabase.child("currentbooking").child(title).child("userID").setValue(a);
                mDatabase.child("currentbooking").child(title).child("timestart").setValue(0);
                mDatabase.child("currentbooking").child(title).child("timeend").setValue(0);



                CookieBar.build(LiveTileDescription.this)
                        .setTitle("Parking Space Booked Successfully!")
                        .setMessage("Contact Developer in case of Error")
                        .setDuration(5000)
                        .setBackgroundColor(R.color.green)
                        .show();

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=12.824779,80.046642&daddr=12.8212766,80.0385479"));
                startActivity(intent);




            }
        });


    }
}
