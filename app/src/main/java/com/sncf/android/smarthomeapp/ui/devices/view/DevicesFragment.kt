package com.sncf.android.smarthomeapp.ui.devices.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.sncf.android.smarthomeapp.R
import com.sncf.android.smarthomeapp.databinding.FragmentDevicesBinding
import com.sncf.android.smarthomeapp.ui.devices.DeviceAdapter
import com.sncf.android.smarthomeapp.ui.devices.IDeviceListener
import com.sncf.android.smarthomeapp.ui.main.MainViewModel
import com.sncf.android.smarthomeapp.ui.model.Device
import com.sncf.android.smarthomeapp.ui.model.Heater
import com.sncf.android.smarthomeapp.ui.model.Light
import com.sncf.android.smarthomeapp.ui.model.Roller
import com.sncf.android.smarthomeapp.utils.SwipeHelper
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment listing available devices
 */
@AndroidEntryPoint
class DevicesFragment : Fragment(), IDeviceListener {

    private lateinit var binding: FragmentDevicesBinding

    private val mainViewModel: MainViewModel by activityViewModels()

    var deviceList: ArrayList<Device> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDevicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModelObservations()
        initListeners()
    }

    private fun initViewModelObservations() {
        mainViewModel.devices.observe(viewLifecycleOwner, { deviceList ->
            deviceList?.let {
                this.deviceList = deviceList
                evaluateDeviceAdapter(deviceList)
                evaluateTypeAdapter(deviceList)
            }
        })

        mainViewModel.user.observe(viewLifecycleOwner, { user ->
            activity?.findViewById<ShapeableImageView>(R.id.b_profile)?.setOnClickListener {
                findNavController().navigate(
                    DevicesFragmentDirections.actionDevicesFragmentToProfileFragment(user)
                )
            }
        })
    }

    private fun initListeners() {
        with(binding.tiDeviceType.editText as AutoCompleteTextView) {
            this.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                evaluateDeviceAdapter(
                    deviceList.filter {
                        it.productType == (this.adapter.getItem(position) as Device).productType
                    } as ArrayList<Device>
                )
            }

            this.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty() || !deviceList.any { it.toString() == text.toString() }) {
                    evaluateDeviceAdapter(deviceList)
                }
            }
        }
    }

    private fun evaluateDeviceAdapter(deviceList: ArrayList<Device>) {
        val adapter = DeviceAdapter(deviceList, this)
        val swipeHelper = object : SwipeHelper(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.deleteItem(viewHolder.adapterPosition)
            }
        }
        ItemTouchHelper(swipeHelper).attachToRecyclerView(binding.rvDevices)
        binding.rvDevices.adapter = adapter
    }

    private fun evaluateTypeAdapter(deviceList: List<Device>) {
        with(deviceList.distinctBy { it.productType }) {
            (binding.tiDeviceType.editText as AutoCompleteTextView).setAdapter(
                ArrayAdapter(
                    requireContext(), R.layout.list_item,
                    this
                )
            )
        }
    }

    override fun onDeviceClicked(device: Device) {
        when (device) {
            is Light -> findNavController().navigate(
                DevicesFragmentDirections.actionDevicesFragmentToLightMonitorFragment(device)
            )
            is Heater -> findNavController().navigate(
                DevicesFragmentDirections.actionDevicesFragmentToHeaterMonitorFragment(device)
            )
            is Roller -> findNavController().navigate(
                DevicesFragmentDirections.actionDevicesFragmentToRollerMonitorFragment(device)
            )
        }
    }
}