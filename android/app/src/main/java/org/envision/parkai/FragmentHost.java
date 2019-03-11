package org.envision.parkai;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.Charsets;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.aviran.cookiebar2.CookieBar;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
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
    private static final int PICK_IMAGE_REQUEST = 234;
    private Button buttonChoose;
    private Button buttonUpload;

    FirebaseStorage storage;
    StorageReference storageReference;

    //ImageView
    private ImageView imageView;

    //a Uri object to store file path
    private Uri filePath,downloadurl;

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



        buttonChoose = (Button) v.findViewById(R.id.selectbtn);
        buttonUpload = (Button) v.findViewById(R.id.uploadbtn);

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(filePath != null)
                {
                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                     final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                    ref.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            downloadurl = uri;
                                            //Do what you want with the url
                                        }});
                                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                            .getTotalByteCount());
                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });
                }
                else
                {
                    Toast.makeText(getContext(), "ILLE IMAGE", Toast.LENGTH_SHORT).show();

                }



                }
        });







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


                    HostDetails details = new HostDetails(collegename.getText().toString(),collegeid.getText().toString(),contact.getText().toString(), password.getText().toString(),downloadurl.toString());
                    mDatabase.child("host").child(a).setValue(details);
                    getActivity().finish();
                    startActivity(new Intent(getContext(), MainActivity.class));

                }
            }
        });


        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

           /* try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                imgView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    }


}