package com.sncf.android.smarthomeapp.ui.monitoring.heater

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
import com.sncf.android.smarthomeapp.databinding.FragmentHeaterMonitorBinding
import com.sncf.android.smarthomeapp.ui.model.Heater
import com.sncf.android.smarthomeapp.utils.Constants.HEATER_LABEL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HeaterMonitorFragment : Fragment() {

    private lateinit var binding: FragmentHeaterMonitorBinding

    private lateinit var heaterArg: Heater

    private val heaterMonitorViewModel: HeaterMonitorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Heater>(HEATER_LABEL)?.let {
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
        initViewModelObservations()
    }

    private fun initUi() {
        binding.tvHeaterName.text = getString(R.string.device_name_label, heaterArg.deviceName)
        binding.smHeaterMode.isChecked = when (heaterArg.mode) {
            ModeEnum.ON.mode -> true
            else -> false
        }
        binding.sHeaterTemperature.value = heaterArg.temperature?.toFloat() ?: 0F
        binding.sHeaterTemperature.setLabelFormatter { value: Float -> "$value°" }
        activity?.findViewById<ShapeableImageView>(R.id.b_profile)?.visibility = View.GONE
    }

    private fun initListeners() {
        binding.bConfirmHeater.setOnClickListener {
            heaterMonitorViewModel.updateHeaterConfiguration(
                Heater(
                    heaterArg.id,
                    heaterArg.deviceName,
                    heaterArg.productType,
                    when {
                        binding.smHeaterMode.isChecked -> ModeEnum.ON.mode
                        else -> ModeEnum.OFF.mode
                    },
                    binding.sHeaterTemperature.value.toInt()
                )
            )
        }
    }

    private fun initViewModelObservations() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            heaterMonitorViewModel.updatedHeaterEvent.collect { event ->
                when (event) {
                    is HeaterMonitorViewModel.UpdatedHeaterEvent.ShowUpdatedHeaterMessage -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}