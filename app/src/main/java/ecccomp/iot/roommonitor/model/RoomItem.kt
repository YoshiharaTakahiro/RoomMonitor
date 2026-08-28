package ecccomp.iot.roommonitor.model

data class RoomItem(
    val imageUri: String?,
    val name: String,
    val building: String,
    val remarks: String?,
    val userId: String,
)
