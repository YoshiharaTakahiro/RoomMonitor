package ecccomp.iot.roommonitor

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.File

class CreateRoomActivity : AppCompatActivity() {

    private lateinit var roomNameEdit: EditText
    private lateinit var buildingSpinner: Spinner
    private lateinit var roomImageView: ImageView
    private lateinit var remarksEdit: EditText

    private lateinit var cameraFab: FloatingActionButton
    private lateinit var createButton: Button

    private lateinit var imageUri: Uri
    private var roomBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_room)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        roomNameEdit = findViewById(R.id.roomNameEdit)
        buildingSpinner = findViewById(R.id.buildingEditSpinner)
        roomImageView = findViewById(R.id.roomEditImageView)
        remarksEdit = findViewById(R.id.remarksEdit)

        cameraFab = findViewById(R.id.cameraFab)
        createButton = findViewById(R.id.roomCreateButton)


        cameraFab.setOnClickListener {

            // 画像データを保存するファイルを生成
            val file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "cameraPhoto.jpg"
            )

            imageUri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                file
            )

            // URIを指定して、ランチャーを起動してカメラを呼び出す
            getTakePictureLauncher.launch(imageUri)

        }

        createButton.setOnClickListener {

            if(roomNameEdit.text.isBlank()){
                Snackbar.make(findViewById(R.id.main), "教室名を入力してください", Snackbar.LENGTH_SHORT).show()
                roomNameEdit.requestFocus()
                return@setOnClickListener
            }

            if (!::imageUri.isInitialized) {
                Snackbar.make(findViewById(R.id.main), "教室の写真を撮影してください", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            roomBitmap?.let{

            }

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

    private val getTakePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()){ success : Boolean ->
            if(success){

                // カメラ画像をリサイズしてから画面に表示する
                roomBitmap = resizeImage(imageUri)
                roomImageView.setImageBitmap(roomBitmap)

            }else{
                Snackbar.make(findViewById(R.id.main), "カメラ処理がキャンセルされました", Snackbar.LENGTH_SHORT).show()
            }
        }

    // システム上、画像は正方形なのでリサイズを行うメソッド
    private fun resizeImage(uri: Uri): Bitmap {

        val inputStream = contentResolver.openInputStream(uri)

        val originalBitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        // リサイズ時に画像情報欠落により、不正な表示になるため修正をかける
        // EXIFの回転情報を取得
        val exif = contentResolver.openInputStream(uri)?.use {
            ExifInterface(it)
        }

        val orientation = exif?.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )

        // EXIFに合わせて回転
        val rotatedBitmap = when (orientation) {

            ExifInterface.ORIENTATION_ROTATE_90 -> {
                rotateBitmap(originalBitmap, 90f)
            }

            ExifInterface.ORIENTATION_ROTATE_180 -> {
                rotateBitmap(originalBitmap, 180f)
            }

            ExifInterface.ORIENTATION_ROTATE_270 -> {
                rotateBitmap(originalBitmap, 270f)
            }

            else -> originalBitmap
        }

        // 正方形にトリミング
        val size = minOf(
            rotatedBitmap.width,
            rotatedBitmap.height
        )

        val left = (rotatedBitmap.width - size) / 2
        val top = (rotatedBitmap.height - size) / 2

        val squareBitmap = Bitmap.createBitmap(
            rotatedBitmap,
            left,
            top,
            size,
            size
        )

        // 512 × 512
        return Bitmap.createScaledBitmap(
            squareBitmap,
            512,
            512,
            true
        )
    }

    // 画像データの回転処理
    private fun rotateBitmap(
        bitmap: Bitmap,
        degrees: Float
    ): Bitmap {

        val matrix = Matrix()
        matrix.postRotate(degrees)

        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }


}