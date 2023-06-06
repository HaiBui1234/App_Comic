package com.example.comicuniverse.AllFragment.ManagerComic;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.comicuniverse.allModel.CategoryModel;
import com.example.comicuniverse.allModel.ComicModel;
import com.exmple.comicuniverse.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddComicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddComicFragment extends Fragment implements View.OnClickListener {
    private EditText edTitle, edDate, edNameAuthor, edNameComic,eddescription;
    SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/YYYY");
    Date date=new Date();
    private Spinner SpIdCategory;
    final int REQUEST_CODE_FODEL = 123;
    private ImageView edImg, img_folel, imgtest;
    ArrayAdapter<CategoryModel> adapter;
    private ArrayList<CategoryModel> categoryModelArrayList;
    ProgressBar loading;
    private Button btnAdd;
    Uri fileUri;

    public AddComicFragment() {
        // Required empty public constructor
    }

    public static AddComicFragment newInstance() {
        AddComicFragment fragment = new AddComicFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_comic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgtest = new ImageView(getActivity());
        imgtest.setImageResource(R.drawable.backgroplogin);
        SpIdCategory = view.findViewById(R.id.id_idCategory);
        edTitle = view.findViewById(R.id.id_idTitle);
        edDate = view.findViewById(R.id.id_idDate);
        edImg = view.findViewById(R.id.id_imgcomic);
        img_folel = view.findViewById(R.id.img_folel);
        edNameAuthor = view.findViewById(R.id.id_nameAuthor);
        edNameComic = view.findViewById(R.id.id_nameComic);
        btnAdd = view.findViewById(R.id.btnAddComic);
        loading = view.findViewById(R.id.loading);
        eddescription=view.findViewById(R.id.id_description);
        edDate.setText(dateFormat.format(date));
        edDate.setEnabled(false);
        loading.setVisibility(View.INVISIBLE);
        img_folel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        getDataCategory();
        adapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,categoryModelArrayList);
        SpIdCategory.setAdapter(adapter);
    }

    private void getDataCategory() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("CategoryModel");
        categoryModelArrayList = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (categoryModelArrayList!=null){
                    categoryModelArrayList.clear();
                }
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                CategoryModel model=dataSnapshot.getValue(CategoryModel.class);
                categoryModelArrayList.add(model);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_folel:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_FODEL);
                break;
            case R.id.btnAddComic:
                loading.setVisibility(View.VISIBLE);
                AddComicModel();
                break;
        }
    }

    private void AddComicModel() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/" + fileUri.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(fileUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> downloadUrlTask = imageRef.getDownloadUrl();
                        downloadUrlTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                CategoryModel categoryModel= (CategoryModel) SpIdCategory.getSelectedItem();
                                String idCatagory=categoryModel.getId_Catagory();
                                String title = edTitle.getText().toString();
                                String nameAuthor = edNameAuthor.getText().toString();
                                String nameComic = edNameComic.getText().toString();
                                String description=eddescription.getText().toString();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("ComicModel");
                                String id_Comic = reference.push().getKey();
                                ComicModel comicModel = new ComicModel(id_Comic, idCatagory, title, dateFormat.format(date), downloadUrl.toString(), nameAuthor, nameComic,0,null,description, false, false);
                                if (id_Comic != null) {
                                    reference.child(id_Comic).setValue(comicModel, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                            loading.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getActivity(), "Thanh cong", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Lỗi ảnh", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FODEL && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            try {
                InputStream stream = getActivity().getContentResolver().openInputStream(fileUri);
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                edImg.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}