package com.example.appmanga;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    //Khởi tạo các biến ánh xạ
    TextView tv_username, tv_count;
    EditText et_old_password, et_new_password, et_new_password_retype;
    Button btn_change, btn_my_books;

    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("TRUYENDADANG").child(user.getUid());

        //Ánh xạ các control
        tv_username = v.findViewById(R.id.tv_username);
        tv_count = v.findViewById(R.id.tv_count);
        et_old_password = v.findViewById(R.id.et_old_password);
        et_new_password = v.findViewById(R.id.et_new_password);
        et_new_password_retype = v.findViewById(R.id.et_new_password_retype);
        btn_change = v.findViewById(R.id.btn_change);
        btn_my_books = v.findViewById(R.id.btn_my_books);

        tv_username.setText(user.getEmail());

        //Lấy dữ liệu từ firebase
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tv_count.setText("Số truyện đã đăng: " + snapshot.getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        //Chạy sự kiện nhấn nút "Đổi mật khẩu"
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Lấy dữ liệu từ các control
                String old_password =  et_old_password.getText().toString();
                String new_password =  et_new_password.getText().toString();
                String new_password_retype =  et_new_password_retype.getText().toString();

                if(TextUtils.isEmpty(old_password) || TextUtils.isEmpty(new_password) || TextUtils.isEmpty(new_password_retype)) {
                    showToast("Vui lòng nhập đầy đủ các trường dữ liệu!");
                    return;
                }
                else
                {
                    if(new_password.length() < 6) {
                        showToast("Mật khẩu không hợp lệ!");
                        return;
                    }
                    else if(!new_password.equals(new_password_retype)) {
                        showToast("Xác nhận mật khẩu chưa trùng khớp!");
                        return;
                    }
                    else {
                        user.updatePassword(new_password);
                        showToast("Cập nhật mật khẩu thành công!");
                    }
                }
            }
        });

        return v;
    }

    //Phương thức hiển thị toast
    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
