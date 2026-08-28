package ecccomp.iot.roommonitor

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.edit

class LoginActivity : AppCompatActivity() {

    private lateinit var userIdEdit: EditText
    private lateinit var passwordEdit: EditText
    private lateinit var loginButton: Button
    private lateinit var createUserTextView: TextView

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide() //　ログイン画面でアクションバーは表示しない
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // プレファレンスをチェックしてユーザ情報があれば、ログイン済みで一覧画面へ遷移する
        preferences = getSharedPreferences("RoomMonitor", MODE_PRIVATE)
        val loginUserId = preferences.getString("userId", null)
        if(loginUserId != null){
            val intent = Intent(this, RoomListActivity::class.java)
            startActivity(intent)
            finish() // ログイン後は自身の画面を終了させておく
            return
        }

        userIdEdit = findViewById(R.id.userIdEdit)
        passwordEdit = findViewById(R.id.passwordEdit)
        loginButton = findViewById(R.id.loginButton)
        createUserTextView = findViewById(R.id.createUserTextView)

        loginButton.setOnClickListener {

            if(userIdEdit.text.isBlank()){
                Snackbar.make(findViewById(R.id.main), "学生番号を入力してください", Snackbar.LENGTH_SHORT).show()
                userIdEdit.requestFocus()
                return@setOnClickListener
            }

            if(passwordEdit.text.isBlank()){
                Snackbar.make(findViewById(R.id.main), "パスワードを入力してください", Snackbar.LENGTH_SHORT).show()
                passwordEdit.requestFocus()
                return@setOnClickListener
            }

            // UserIDの存在とパスワードチェックを行う


            // ユーザIDをプレファレンスに保存
            preferences.edit {
                putString("userId", userIdEdit.text.toString().trim())
            }

            val intent = Intent(this, RoomListActivity::class.java)
            startActivity(intent)
            finish() // ログイン後は自身の画面を終了させておく
        }

        createUserTextView.setOnClickListener {
            val intent = Intent(this, CreateUserActivity::class.java)
            startActivity(intent)
        }
    }
}