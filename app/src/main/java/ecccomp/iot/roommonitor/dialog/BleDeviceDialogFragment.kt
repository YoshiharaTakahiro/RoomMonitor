package ecccomp.iot.roommonitor.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class BleDeviceDialogFragment: DialogFragment() {

        private lateinit var devices: ArrayList<BluetoothDevice>

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            devices = arguments?.getParcelableArrayList("devices")
                ?: arrayListOf()
        }

        @SuppressLint("MissingPermission")
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            // デバイス名だけを表示用の配列にする
            val items = devices.map { device ->
                device.name ?: "名前なし"
            }.toTypedArray()

            return AlertDialog.Builder(requireContext())
                .setTitle("Bluetoothデバイスを選択")
                .setItems(items) { _, which ->

                    // 選択されたデバイス
                    val device = devices[which]

                    // Activityへ通知
                    (activity as? OnDeviceSelectedListener)
                        ?.onDeviceSelected(device)
                }
                .setNegativeButton("キャンセル", null)
                .create()
        }

        interface OnDeviceSelectedListener {
            fun onDeviceSelected(device: BluetoothDevice)
        }
}