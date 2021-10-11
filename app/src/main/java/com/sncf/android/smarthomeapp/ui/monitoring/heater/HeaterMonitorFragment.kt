package com.sncf.android.smarthomeapp.ui.monitoring.heater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sncf.android.smarthomeapp.R
import com.sncf.android.smarthomeapp.databinding.FragmentHeaterMonitorBinding
import com.sncf.android.smarthomeapp.domain.common.ModeEnum
import com.sncf.android.smarthomeapp.ui.model.Heater
import com.sncf.android.smarthomeapp.utils.Constants

class HeaterMonitorFragment : Fragment() {

    private lateinit var binding: FragmentHeaterMonitorBinding

    private lateinit var heaterArg: Heater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Heater>(Constants.HEATER_LABEL)?.let {
            heaterArg = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeaterMonitorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    private fun initUi() {
        binding.tvHeaterName.text = getString(R.string.device_name_label, heaterArg.deviceName)
        binding.smHeaterMode.isChecked = when (heaterArg.mode) {
            ModeEnum.ON.mode -> true
            else -> false
        }
        binding.sHeaterTemperature.value = heaterArg.temperature?.toFloat() ?: 0F
        binding.sHeaterTemperature.setLabelFormatter { value: Float -> "$value°" }
    }

    private fun initListeners() {
        binding.smHeaterMode.setOnCheckedChangeListener { buttonView, isChecked ->
            // heaterMonitorViewModel.updateHeaterMode
        }

        binding.sHeaterTemperature.addOnChangeListener { slider, value, fromUser ->
            // heaterMonitorViewModel.updateHeaterTemperature
        }
    }
}