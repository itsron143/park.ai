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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by root on 11/3/19.
 */

public class FragmentProfile extends Fragment {

    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<News, NewsActivity.NewsViewHolder> mPeopleRVAdapter;
    private GoogleSignInClient mGoogleSignInClient;

    public static FragmentProfile newInstance() {

        return new FragmentProfile();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_new, container, false);
        getActivity().setTitle("Profile");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = "Hi! " + user.getDisplayName();
        ImageView mCircularProfile = (ImageView) v.findViewById(R.id.profile_image);
        final TextView mTextView = (TextView) v.findViewById(R.id.name);
        mTextView.setText(name);



        Glide.with(this)
                .load(user.getPhotoUrl())
                // .crossFade()
                .into(mCircularProfile);
        Button team=(Button) v.findViewById(R.id.signout);

        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


         /*       Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();

                            }
                        }); */

                FirebaseAuth.getInstance().signOut();
                //mGoogleSignInClient.signOut();
                Intent i=new Intent(getContext(),LoginActivity.class);
                //i.putExtra("loginstatus","false");
                startActivity(i);
            }
        });

        return v;
    }
}