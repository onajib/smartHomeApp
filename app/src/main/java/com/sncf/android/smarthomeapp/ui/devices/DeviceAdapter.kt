package com.sncf.android.smarthomeapp.ui.devices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sncf.android.smarthomeapp.data.model.Device
import com.sncf.android.smarthomeapp.databinding.DeviceRowLayoutBinding

class DeviceAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Device, DeviceAdapter.DeviceViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding =
            DeviceRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class DeviceViewHolder(private val binding: DeviceRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val device = getItem(position)
                        listener.onItemClick(device)
                    }
                }
            }
        }

        fun bind(device: Device) {
            binding.apply {
                tvDeviceName.text = device.deviceName
                tvDeviceType.text = device.productType
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(device: Device)
    }

    class DiffCallback : DiffUtil.ItemCallback<Device>() {
        override fun areItemsTheSame(oldItem: Device, newItem: Device) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Device, newItem: Device) =
            oldItem.deviceName == newItem.deviceName
    }
}