package com.sncf.android.smarthomeapp.ui.devices

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sncf.android.smarthomeapp.R
import com.sncf.android.smarthomeapp.ui.model.Device

class DeviceAdapter(
    private var deviceList: ArrayList<Device>,
    private var listener: IDeviceListener? = null
) :
    RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    fun deleteItem(position: Int) {
        deviceList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, deviceList.size)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.device_row_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.tvDeviceName.text = deviceList[i].deviceName
        viewHolder.tvType.text = deviceList[i].productType
        viewHolder.itemView.setOnClickListener {
            listener?.onDeviceClicked(deviceList[i])
            return@setOnClickListener
        }
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDeviceName: TextView = itemView.findViewById(R.id.tv_device_name)
        val tvType: TextView = itemView.findViewById(R.id.tv_device_type)
    }
}