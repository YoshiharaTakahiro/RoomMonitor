package ecccomp.iot.roommonitor

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ecccomp.iot.roommonitor.adapter.RoomAdapter
import ecccomp.iot.roommonitor.model.RoomItem
import androidx.core.content.edit

class RoomListActivity : AppCompatActivity() {

    private lateinit var buildingSpinner: Spinner
    private lateinit var searchButton: ImageButton
    private lateinit var roomRecyclerView: RecyclerView
    private lateinit var roomAddFab: FloatingActionButton

    // 教室一覧データ
    private var roomItems = mutableListOf<RoomItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_room_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buildingSpinner = findViewById(R.id.buildingSearchSpinner)
        searchButton = findViewById(R.id.buildingSearchButton)
        roomRecyclerView = findViewById(R.id.roomRecyclerView)
        roomAddFab = findViewById(R.id.roomAddFab)

        // ダミーデータ ※WebAPIから教室情報が取得できれば不要
        val dummyData = listOf(
            RoomItem(null, "3701教室", "3号館7F", "少人数教室", "70795"),
            RoomItem(null, "3601教室", "3号館6F", "実習室", "70795"),
            RoomItem(null, "3501教室", "3号館5F", "IoT部屋", "70795"),
        )
        roomItems.addAll(dummyData)

        // リサイクラービューにはGridレイアウトを設定
        val layoutManager = GridLayoutManager(this, 2)
        roomRecyclerView.layoutManager = layoutManager

        // リサイクラービューのアダプター設定
        val roomAdapter = RoomAdapter(roomItems, onItemClick = {

            // 選択された教室情報を設定して詳細画面を呼び出す
            val intent = Intent(this, RoomDetailActivity::class.java)
            startActivity(intent)

        })
        roomRecyclerView.adapter = roomAdapter


        searchButton.setOnClickListener {

        }

        roomAddFab.setOnClickListener {

            val intent = Intent(this, CreateRoomActivity::class.java)
            startActivity(intent)
        }

        // 教室一覧情報の取得


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