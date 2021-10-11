package com.sncf.android.smarthomeapp.ui.monitoring.roller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sncf.android.smarthomeapp.R
import com.sncf.android.smarthomeapp.databinding.FragmentRollerMonitorBinding
import com.sncf.android.smarthomeapp.ui.model.Roller
import com.sncf.android.smarthomeapp.utils.Constants

class RollerMonitorFragment : Fragment() {

    private lateinit var binding: FragmentRollerMonitorBinding

    private lateinit var rollerArg: Roller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Roller>(Constants.ROLLER_LABEL)?.let {
            rollerArg = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRollerMonitorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    private fun initUi() {
        binding.tvRollerName.text = getString(R.string.device_name_label, rollerArg.deviceName)
        binding.sRollerPosition.value = rollerArg.position?.toFloat() ?: 0F
    }

    private fun initListeners() {
        binding.sRollerPosition.addOnChangeListener { slider, value, fromUser ->
            // RollerMonitorViewModel.updateRollerPosition
        }
    }
}