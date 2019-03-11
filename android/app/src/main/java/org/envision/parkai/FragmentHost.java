package org.envision.parkai;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.common.base.Charsets;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.aviran.cookiebar2.CookieBar;

import static com.google.common.hash.Hashing.sha1;

/**
 * Created by root on 11/3/19.
 */

public class FragmentHost extends Fragment {

    private RecyclerView mPeopleRV;
    //private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<News, NewsActivity.NewsViewHolder> mPeopleRVAdapter;
    private EditText collegename,collegeid,contact,password;
    private DatabaseReference mDatabase;

    public static FragmentHost newInstance() {

        return new FragmentHost();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_login_contact, container, false);
        getActivity().setTitle("Host a Space!");


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String a=user.getUid().toString();
        final String name=user.getDisplayName().toString();
        final String email=user.getEmail().toString();


        final String picURL=user.getPhotoUrl().toString();




        Button signup = (Button) v.findViewById(R.id.btn_sign_up);
        collegename = (EditText) v.findViewById(R.id.collegename);
        collegeid = (EditText) v.findViewById(R.id.collegeid);
        contact = (EditText) v.findViewById(R.id.contact);
        password = (EditText) v.findViewById(R.id.password);



        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((collegename.getText().toString().trim().equals("")) || (password.getText().toString().trim().equals("")) || (collegeid.getText().toString().trim().equals("")) || (contact.getText().toString().trim().equals("")) || (password.getText().toString().trim().equals(""))) {
                    CookieBar.build(getActivity())
                            .setTitle("Please Enter All Details")
                            .setMessage("Contact Developers in case of any error!")
                            .setDuration(5000)
                            .setBackgroundColor(R.color.red)
                            .show();
                } else {


                    HostDetails details = new HostDetails(collegename.getText().toString(),collegeid.getText().toString(),contact.getText().toString(), password.getText().toString(),"lol");
                    mDatabase.child("host").child(a).setValue(details);
                    getActivity().finish();
                    startActivity(new Intent(getContext(), MainActivity.class));

                }
            }
        });


        return v;
    }
}