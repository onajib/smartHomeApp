package com.sncf.android.smarthomeapp.ui.monitoring.light

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sncf.android.smarthomeapp.R
import com.sncf.android.smarthomeapp.databinding.FragmentLightMonitorBinding
import com.sncf.android.smarthomeapp.domain.common.ModeEnum
import com.sncf.android.smarthomeapp.ui.model.Light
import com.sncf.android.smarthomeapp.utils.Constants.LIGHT_LABEL

class LightMonitorFragment : Fragment() {

    private lateinit var binding: FragmentLightMonitorBinding

    private lateinit var lightArg: Light

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Light>(LIGHT_LABEL)?.let {
            lightArg = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLightMonitorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    private fun initUi() {
        binding.tvLightName.text = getString(R.string.device_name_label, lightArg.deviceName)
        binding.smLightMode.isChecked = when (lightArg.mode) {
            ModeEnum.ON.mode -> true
            else -> false
        }
        binding.sLightIntensity.value = lightArg.intensity?.toFloat() ?: 0F
    }

    private fun initListeners() {
        binding.smLightMode.setOnCheckedChangeListener { buttonView, isChecked ->
            // LightMonitorViewModel.updateLightMode
        }

        binding.sLightIntensity.addOnChangeListener { slider, value, fromUser ->
            // LightMonitorViewModel.updateLightIntensity
        }
    }
}