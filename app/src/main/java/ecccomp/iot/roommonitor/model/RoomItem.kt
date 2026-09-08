package ecccomp.iot.roommonitor.model

data class RoomItem(
    val id: Int,
    val name: String,
    val building: String,
    val remarks: String?,
    val image_path: String?,
    val user_id: Int,
)
