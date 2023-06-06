package com.example.comicuniverse.SignInUp;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.comicuniverse.allModel.ComicModel;
import com.example.comicuniverse.allModel.FavoriteComic;
import com.example.comicuniverse.allModel.UserModel;
import com.exmple.comicuniverse.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class SingUpFragment extends Fragment {
    private EditText edFullName, edUserName, edPassWord, edCode;
    ImageView imageUser,ImageFodel;
    ProgressBar PbSignUp;
    Button btnSignUp;
    ArrayList<UserModel> listUsers=new ArrayList<>();
    Uri fileUri;
    public SingUpFragment() {
        // Required empty public constructor
    }

    public static SingUpFragment newInstance(String param1, String param2) {
        SingUpFragment fragment = new SingUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_sing_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edFullName = view.findViewById(R.id.id_FullName);
        edUserName = view.findViewById(R.id.id_UserName);
        edPassWord = view.findViewById(R.id.id_PassWord);
        edCode = view.findViewById(R.id.id_code);
        imageUser = view.findViewById(R.id.imageUser);
        ImageFodel=view.findViewById(R.id.ImageFodel);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        PbSignUp=view.findViewById(R.id.PbSignUp);
        PbSignUp.setVisibility(View.INVISIBLE);
        getUserModel();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            AddUser();
            }
        });
        ImageFodel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,123);
            }
        });

    }
    private void AddUser() {
        getUserModel();
        String FullName=edFullName.getText().toString().trim();
        String UserName=edUserName.getText().toString().trim();
        String PassWord=edPassWord.getText().toString().trim();
        int Code=Integer.parseInt(edCode.getText().toString());
        for (int i=0;i<listUsers.size();i++){
            if (UserName.toLowerCase().equals(listUsers.get(i).getUserName().toLowerCase())){
                Toast.makeText(getActivity(), "User name đã tồn tại", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        PbSignUp.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("UserModel");
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("ImageUsers/" + fileUri.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(fileUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=imageRef.getDownloadUrl();
                uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String pathObject=reference.push().getKey();
                        UserModel User=new UserModel(pathObject,FullName,UserName,PassWord,Code,uri.toString(),false,null);
                        if (pathObject!=null){
                            reference.child(pathObject).setValue(User, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    PbSignUp.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(), "Thanh Cong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });


    }
    private void getUserModel() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("UserModel");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                listUsers.add(userModel);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==123&&resultCode==RESULT_OK&&data!=null){
            fileUri=data.getData();
        }
        try {
            InputStream stream = getActivity().getContentResolver().openInputStream(fileUri);
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            imageUser.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}