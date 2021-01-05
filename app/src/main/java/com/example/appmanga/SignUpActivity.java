package com.example.appmanga;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    //Khởi tạo các biến ánh xạ
    EditText et_username, et_password, et_password_retype;
    Button btn_signup;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("TRUYENDADANG");

        //Ánh xạ các control
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_retype = (EditText) findViewById(R.id.et_password_retype);
        btn_signup = (Button) findViewById(R.id.btn_signup);

        //Chạy sự kiện nhấn nút "Đăng ký ngay"
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Lấy dữ liệu từ các control
                String username =  et_username.getText().toString();
                String password =  et_password.getText().toString();
                String password_retype =  et_password_retype.getText().toString();

                //Validation
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password_retype)) {
                    showToast("Vui lòng nhập đầy đủ các trường dữ liệu!");
                    return;
                }
                else if(!isEmailValid(username)) {
                    showToast("Email không hợp lệ!");
                    return;
                }
                else if(password.length() < 6) {
                    showToast("Mật khẩu không hợp lệ!");
                    return;
                }
                else if(!password.equals(password_retype)) {
                    showToast("Xác nhận mật khẩu chưa trùng khớp!");
                    return;
                }
                else {
                    DangKy(username,password);
                }
            }
        });

    }

    //Phương thức hiển thị toast
    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //Phương thức đăng ký
    private void DangKy(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, (OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        showToast("Đăng ký thành công!");
                        mDatabase.child(mAuth.getCurrentUser().getUid()).setValue(0);
                    } else {
                        showToast("Đăng ký thất bại!");
                    }
                });
    }

    //Phương thức kiểm tra email hợp lệ
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}