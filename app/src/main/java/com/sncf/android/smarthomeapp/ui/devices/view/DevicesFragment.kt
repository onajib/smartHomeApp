package com.sncf.android.smarthomeapp.ui.devices.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import com.sncf.android.smarthomeapp.R
import com.sncf.android.smarthomeapp.data.model.Device
import com.sncf.android.smarthomeapp.databinding.FragmentDevicesBinding
import com.sncf.android.smarthomeapp.ui.devices.DeviceAdapter
import com.sncf.android.smarthomeapp.ui.model.Heater
import com.sncf.android.smarthomeapp.ui.model.Light
import com.sncf.android.smarthomeapp.ui.model.Roller
import com.sncf.android.smarthomeapp.utils.Constants.HEATER_LABEL
import com.sncf.android.smarthomeapp.utils.Constants.LIGHT_LABEL
import com.sncf.android.smarthomeapp.utils.Constants.ROLLER_LABEL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * Fragment listing available devices
 */
@AndroidEntryPoint
class DevicesFragment : Fragment(), DeviceAdapter.OnItemClickListener {

    private lateinit var binding: FragmentDevicesBinding

    private val deviceViewModel: DeviceViewModel by viewModels()

    private var deviceList: List<Device> = listOf()

    ///////////////////////////////////////////////////////////////////////////
    // OVERRIDE
    ///////////////////////////////////////////////////////////////////////////

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDevicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deviceAdapter = DeviceAdapter(this)
        binding.rvDevices.adapter = deviceAdapter

        initViewModelObservations(deviceAdapter)
        initListeners(deviceAdapter)
    }

    override fun onItemClick(device: Device) {
        when (device.productType) {
            LIGHT_LABEL -> findNavController().navigate(
                DevicesFragmentDirections.actionDevicesFragmentToLightMonitorFragment(
                    Light(
                        device.id,
                        device.deviceName,
                        device.productType,
                        device.intensity,
                        device.mode
                    )
                )
            )
            HEATER_LABEL -> findNavController().navigate(
                DevicesFragmentDirections.actionDevicesFragmentToHeaterMonitorFragment(
                    Heater(
                        device.id,
                        device.deviceName,
                        device.productType,
                        device.mode,
                        device.temperature
                    )
                )
            )
            ROLLER_LABEL -> findNavController().navigate(
                DevicesFragmentDirections.actionDevicesFragmentToRollerMonitorFragment(
                    Roller(
                        device.id,
                        device.deviceName,
                        device.productType,
                        device.position,
                    )
                )
            )
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // PRIVATE
    ///////////////////////////////////////////////////////////////////////////

    private fun initViewModelObservations(deviceAdapter: DeviceAdapter) {
        observeDevices(deviceAdapter)
        observeUser()
        observeDeletionAction()
    }

    private fun observeDevices(deviceAdapter: DeviceAdapter) {
        deviceViewModel.getDevices().observe(viewLifecycleOwner) {
            deviceList = it
            deviceAdapter.submitList(it)
            initUi()
        }
    }

    private fun initUi() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.tgFilterButtons.clearChecked()
    }

    private fun observeUser() {
        deviceViewModel.getUser().observe(viewLifecycleOwner) { user ->
            activity?.findViewById<ShapeableImageView>(R.id.b_profile).apply {
                this?.visibility = View.VISIBLE
                this?.setOnClickListener {
                    user?.let {
                        findNavController().navigate(
                            DevicesFragmentDirections.actionDevicesFragmentToProfileFragment(it)
                        )
                    }
                }
            }
        }
    }

    private fun observeDeletionAction() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            deviceViewModel.deletedDeviceEvent.collect { event ->
                when (event) {
                    is DeviceViewModel.DeletedDeviceEvent.ShowUndoDeleteDeviceMessage ->
                        Snackbar.make(
                            requireView(),
                            "${event.device.deviceName} Deleted!",
                            Snackbar.LENGTH_LONG
                        ).setAction("Undo") {
                            deviceViewModel.onUndoDeleteClick(event.device)
                        }.show()
                }
            }
        }
    }

    private fun initListeners(deviceAdapter: DeviceAdapter) {
        initDeletionSwiperListener(deviceAdapter)
        initFilterTypeListener(deviceAdapter)
    }

    private fun initDeletionSwiperListener(deviceAdapter: DeviceAdapter) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val article = deviceAdapter.currentList[viewHolder.adapterPosition]
                deviceViewModel.onDeviceSwiped(article)
                deviceAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(binding.rvDevices)
    }

    private fun initFilterTypeListener(deviceAdapter: DeviceAdapter) {
        var filteredList: MutableList<Device> = mutableListOf()
        binding.tgFilterButtons.addOnButtonCheckedListener { group, checkedId, isChecked ->
            with(this.deviceList.toMutableList()) {
                if (group.checkedButtonIds.isEmpty()) {
                    filteredList = this
                } else {
                    if (this.size == filteredList.size && isChecked) filteredList = mutableListOf()
                    when (checkedId) {
                        binding.bLightFilter.id ->
                            evaluateFilteredDevice(isChecked, filteredList, LIGHT_LABEL)
                        binding.bRollerFilter.id ->
                            evaluateFilteredDevice(isChecked, filteredList, ROLLER_LABEL)
                        binding.bHeaterFilter.id ->
                            evaluateFilteredDevice(isChecked, filteredList, HEATER_LABEL)
                    }
                }
            }
            deviceAdapter.submitList(filteredList)
            deviceAdapter.notifyDataSetChanged()
        }
    }

    private fun evaluateFilteredDevice(
        isChecked: Boolean,
        filteredList: MutableList<Device>,
        deviceType: String
    ) {
        if (isChecked) {
            filteredList.addAll(this.deviceList.filter { it.productType == deviceType })
        } else {
            filteredList.removeAll(this.deviceList.filter { it.productType == deviceType })
        }
    }
}