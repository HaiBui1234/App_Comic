package com.example.comicuniverse.SignInUp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comicuniverse.MainActivity;
import com.example.comicuniverse.allModel.ComicModel;
import com.example.comicuniverse.allModel.UserModel;
import com.exmple.comicuniverse.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LoginFragment extends Fragment {
    public static String TAG="TAG";
    private EditText edUsersName, edPassword;
    CheckBox id_Rememberaccount;
    SharedPreferences preferences;
    Button btn_Login;
    ArrayList<UserModel> listUsers=new ArrayList<>();

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edUsersName = view.findViewById(R.id.ed_userLogin);
        edPassword = view.findViewById(R.id.ed_passwordLogin);
        btn_Login = view.findViewById(R.id.btn_Login);
        id_Rememberaccount=view.findViewById(R.id.id_Rememberaccount);
        preferences=getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        edUsersName.setText(preferences.getString("UserName",""));
        edPassword.setText(preferences.getString("PassWord",""));
        id_Rememberaccount.setChecked(preferences.getBoolean("Check",false));
        UserModel model=new UserModel
                (preferences.getString("ID",""),preferences.getString("fullName_User",""),
                        preferences.getString("UserName",""),preferences.getString("PassWord",""),
                        preferences.getInt("code_User",0),preferences.getString("avarta_Usre",""),
                                preferences.getBoolean("isActive",false),null);
        if (id_Rememberaccount.isChecked()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    intent.putExtra("user",model);
                    startActivity(intent);
                }
            },100);
        }
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserModel();
                SharedPreferences.Editor editor=preferences.edit();
                String username = edUsersName.getText().toString().trim();
                String passWord = edPassword.getText().toString().trim();
                for (int i = 0; i < listUsers.size(); i++) {
                    for (UserModel user : listUsers) {
                        if (username.equals(user.getUserName()) && passWord.equals(user.getPassWord_User())) {
                            if (id_Rememberaccount.isChecked()){
                            editor.putString("UserName",user.getUserName());
                            editor.putString("PassWord",user.getPassWord_User());
                            editor.putString("ID",user.getId_User());
                            editor.putString("fullName_User",user.getFullName_User());
                            editor.putInt("code_User",user.getCode_User());
                            editor.putString("avarta_Usre",user.getAvarta_Usre());
                            editor.putBoolean("isActive",user.isActive());
                            editor.putBoolean("Check",true);
                            editor.apply();
                            }else {
                            editor.remove("PassWord");
                            editor.remove("Check");
                            editor.remove("ID");
                            editor.remove("fullName_User");
                            editor.remove("code_User");
                            editor.remove("avarta_Usre");
                            editor.remove("isActive");
                            editor.apply();
                            }
                            Intent intent=new Intent(getActivity(),MainActivity.class);
                            intent.putExtra("user",user);
                            startActivity(intent);
                            Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                            return;
                        }
                    }
                }
                Toast.makeText(getActivity(), "Kiểm tra lại thông tin!", Toast.LENGTH_SHORT).show();
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
                Log.d(TAG, "onChildAdded: "+listUsers.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserModel userModel=snapshot.getValue(UserModel.class);
                if (userModel==null){
                    return;
                }
                for (int i=0;i<listUsers.size();i++){
                    listUsers.set(i,userModel);
                }
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
    public void onResume() {
        super.onResume();
        getUserModel();
    }
}