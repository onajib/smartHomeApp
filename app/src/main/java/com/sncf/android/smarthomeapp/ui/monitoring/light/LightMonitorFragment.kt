package com.sncf.android.smarthomeapp.ui.monitoring.light

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.imageview.ShapeableImageView
import com.sncf.android.smarthomeapp.R
import com.sncf.android.smarthomeapp.common.ModeEnum
import com.sncf.android.smarthomeapp.databinding.FragmentLightMonitorBinding
import com.sncf.android.smarthomeapp.ui.model.Light
import com.sncf.android.smarthomeapp.utils.Constants.LIGHT_LABEL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LightMonitorFragment : Fragment() {

    private lateinit var binding: FragmentLightMonitorBinding

    private lateinit var lightArg: Light

    private val lightMonitorViewModel: LightMonitorViewModel by viewModels()

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
        initViewModelObservations()
    }

    private fun initUi() {
        binding.tvLightName.text = getString(R.string.device_name_label, lightArg.deviceName)
        binding.smLightMode.isChecked = when (lightArg.mode) {
            ModeEnum.ON.mode -> true
            else -> false
        }
        binding.sLightIntensity.value = lightArg.intensity?.toFloat() ?: 0F
        activity?.findViewById<ShapeableImageView>(R.id.b_profile)?.visibility = View.GONE
    }

    private fun initListeners() {
        binding.bConfirmLight.setOnClickListener {
            lightMonitorViewModel.updateLightConfiguration(
                Light(
                    lightArg.id,
                    lightArg.deviceName,
                    lightArg.productType,
                    binding.sLightIntensity.value.toInt(),
                    when {
                        binding.smLightMode.isChecked -> ModeEnum.ON.mode
                        else -> ModeEnum.OFF.mode
                    }
                )
            )
        }
    }

    private fun initViewModelObservations() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            lightMonitorViewModel.updatedLightEvent.collect { event ->
                when (event) {
                    is LightMonitorViewModel.UpdatedLightEvent.ShowUpdatedLightMessage -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}