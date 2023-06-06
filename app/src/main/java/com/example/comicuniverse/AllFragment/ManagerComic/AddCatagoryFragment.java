package com.example.comicuniverse.AllFragment.ManagerComic;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.comicuniverse.allModel.CategoryModel;
import com.exmple.comicuniverse.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCatagoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCatagoryFragment extends Fragment {
    Button btnAddcategory;
    private EditText ednameCategory;
    Uri fileUri;
    ImageButton id_imageCategory;
    public AddCatagoryFragment() {
        // Required empty public constructor
    }
    public static AddCatagoryFragment newInstance() {
        return new AddCatagoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_catagory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAddcategory=view.findViewById(R.id.id_addCategory);
        ednameCategory=view.findViewById(R.id.id_nameCategory);
        id_imageCategory=view.findViewById(R.id.id_imageCategory);
        id_imageCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 123);
            }
        });
        btnAddcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCategory();
            }
        });
    }

    private void AddCategory() {
        String nameCate=ednameCategory.getText().toString().trim();
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/" + fileUri.getLastPathSegment());
        UploadTask uploadTask=imageRef.putFile(fileUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> task=imageRef.getDownloadUrl();
                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                        DatabaseReference reference=database.getReference("CategoryModel");
                        String idCategory=reference.push().getKey();
                        CategoryModel model=new CategoryModel(idCategory,nameCate,uri.toString());
                        if (idCategory!=null){
                            reference.child(idCategory).setValue(model, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    Toast.makeText(getActivity(), "Thanh Cong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==123&&resultCode==RESULT_OK&&data!=null){
            fileUri=data.getData();
        }
    }
}