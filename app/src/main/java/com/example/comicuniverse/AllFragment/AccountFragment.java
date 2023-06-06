package com.example.comicuniverse.AllFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.comicuniverse.SignInUp.LoginApp;
import com.example.comicuniverse.SignInUp.LoginFragment;
import com.example.comicuniverse.allModel.ComicModel;
import com.example.comicuniverse.allModel.UserModel;
import com.exmple.comicuniverse.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

   TextView tvNameUser;
   ImageView id_Avarta;
   LinearLayout id_DoiPass;
    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNameUser=view.findViewById(R.id.idAccount);
        id_DoiPass=view.findViewById(R.id.id_DoiPass);
        id_Avarta=view.findViewById(R.id.id_Avarta);
        LinearLayout Logout=view.findViewById(R.id.Logout);
        SharedPreferences  preferences=getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference  reference=database.getReference("UserModel");
        Intent intent = getActivity().getIntent();
        UserModel userModel = (UserModel) intent.getSerializableExtra("user");
        tvNameUser.setText(userModel.getUserName());
        Glide.with(getActivity()).load(userModel.getAvarta_Usre()).error(R.drawable.ic_action_erron).into(id_Avarta);
        id_DoiPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(getActivity());
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setContentView(R.layout.change_password_item);
                Window window=dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                EditText edOldPass=dialog.findViewById(R.id.oldPass);
                EditText edNewpass=dialog.findViewById(R.id.newPass);
                EditText edverify=dialog.findViewById(R.id.verifypass);
                Button btnHuy=dialog.findViewById(R.id.btnHuy);
                Button btnUpdate=dialog.findViewById(R.id.btnUpdatepass);
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String OldPass=edOldPass.getText().toString();
                        String Newpass=edNewpass.getText().toString();
                        String verify=edverify.getText().toString();
                        if (OldPass.isEmpty()||Newpass.isEmpty()||verify.isEmpty()){
                            Toast.makeText(getActivity(), "Hay nhap lieu", Toast.LENGTH_SHORT).show();
                            return;
                        }else if (!Newpass.equals(verify)){
                            Toast.makeText(getActivity(), "Kiểm tra lại mật khẩu mới", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (OldPass.equals(userModel.getPassWord_User())){
                            userModel.setPassWord_User(Newpass);
                            reference.child(userModel.getId_User()).setValue(userModel, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    SharedPreferences.Editor editor=preferences.edit();
                                    editor.putString("PassWord",Newpass);
                                    editor.apply();
                                    Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }else {
                            Toast.makeText(getActivity(), "Kiểm tra lại mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("Đăng xuất");
                builder.setMessage("Bạn có chắc muốn đăng xuất");
                builder.setNegativeButton("NO",null);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.remove("PassWord");
                        editor.remove("Check");
                        editor.apply();
                        startActivity(new Intent(getActivity(),LoginApp.class));
                    }
                });
                builder.show();
            }
        });
    }
}