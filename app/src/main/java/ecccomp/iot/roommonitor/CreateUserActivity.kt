package ecccomp.iot.roommonitor

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class CreateUserActivity : AppCompatActivity() {

    private lateinit var userIdEdit: EditText
    private lateinit var userNameEdit: EditText
    private lateinit var passwordEdit: EditText
    private lateinit var samePassEdit: EditText
    private lateinit var createButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userIdEdit = findViewById(R.id.createUserIdEdit)
        userNameEdit = findViewById(R.id.createUserNameEdit)
        passwordEdit = findViewById(R.id.createPasswordEdit)
        samePassEdit = findViewById(R.id.samePasswordEdit)

        createButton = findViewById(R.id.userCreateButton)

        createButton.setOnClickListener {

            if(userIdEdit.text.isBlank()){
                Snackbar.make(findViewById(R.id.main), "学生番号を入力してください", Snackbar.LENGTH_SHORT).show()
                userIdEdit.requestFocus()
                return@setOnClickListener
            }

            if(userNameEdit.text.isBlank()){
                Snackbar.make(findViewById(R.id.main), "氏名を入力してください", Snackbar.LENGTH_SHORT).show()
                userNameEdit.requestFocus()
                return@setOnClickListener
            }

            if(passwordEdit.text.isBlank()){
                Snackbar.make(findViewById(R.id.main), "パスワードを入力してください", Snackbar.LENGTH_SHORT).show()
                passwordEdit.requestFocus()
                return@setOnClickListener
            }

            if(passwordEdit.text.toString() != samePassEdit.text.toString()){
                Snackbar.make(findViewById(R.id.main), "パスワードが一致しません", Snackbar.LENGTH_SHORT).show()
                samePassEdit.requestFocus()
                return@setOnClickListener
            }

            // FCMトークン情報を取得


            // WebAPIを呼出しユーザ登録をおこなう


            // ユーザIDをプレファレンスに保存
            val preferences = getSharedPreferences("RoomMonitor", MODE_PRIVATE)
            preferences.edit {
                putString("userId", userIdEdit.text.toString().trim())
            }

            val intent = Intent(this, RoomListActivity::class.java)
            // 戻るで作成画面、ログイン画面に戻れないようにスタックを削除して遷移を行う
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
    }
}