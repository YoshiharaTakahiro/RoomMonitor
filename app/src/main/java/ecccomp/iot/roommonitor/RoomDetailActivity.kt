package ecccomp.iot.roommonitor

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RoomDetailActivity : AppCompatActivity() {

    private lateinit var roomNameText: TextView
    private lateinit var buildingText: TextView
    private lateinit var remarksText: TextView

    private lateinit var roomImageView: ImageView

    private lateinit var bluetoothFab: FloatingActionButton
    private lateinit var notificationFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_room_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        roomNameText = findViewById(R.id.roomNameDetailText)
        buildingText = findViewById(R.id.buildingDetailText)
        remarksText = findViewById(R.id.remarksDetailText)
        roomImageView = findViewById(R.id.roomDetailImageView)

        bluetoothFab = findViewById(R.id.bluetoothFab)
        notificationFab = findViewById(R.id.notificationFab)


        bluetoothFab.setOnClickListener {

        }

        notificationFab.setOnClickListener {

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_logout -> {

                // ユーザIDを削除してログアウト状態にする
                val preferences = getSharedPreferences("RoomMonitor", MODE_PRIVATE)
                preferences.edit { remove("userId") }

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}