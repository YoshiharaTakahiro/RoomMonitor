package ecccomp.iot.roommonitor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import ecccomp.iot.roommonitor.R
import ecccomp.iot.roommonitor.model.RoomItem
import kotlinx.coroutines.runInterruptible

class RoomAdapter(private val dataSet: List<RoomItem>, private val onItemClick:(RoomItem) -> Unit) :
    RecyclerView.Adapter<RoomAdapter.ViewHolder> (){
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val roomName: TextView
        val roomImage: ImageView
        val building: TextView
        val roomRemarks: TextView

        init {
            roomName = view.findViewById(R.id.roomItemNameText)
            roomImage = view.findViewById(R.id.roomItemImageView)
            building = view.findViewById(R.id.buildingText)
            roomRemarks = view.findViewById(R.id.roomItemRemarksText)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoomAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = dataSet[position]

        holder.roomName.text = item.name
        item.image_uri?.let{
            // URIから画像を取得してImageViewに設定

        }
        holder.building.text = item.building
        holder.roomRemarks.text = item.remarks

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}