package com.sncf.android.smarthomeapp.ui.monitoring.roller

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
import com.sncf.android.smarthomeapp.databinding.FragmentRollerMonitorBinding
import com.sncf.android.smarthomeapp.ui.model.Roller
import com.sncf.android.smarthomeapp.utils.Constants.ROLLER_LABEL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RollerMonitorFragment : Fragment() {

    private lateinit var binding: FragmentRollerMonitorBinding

    private lateinit var rollerArg: Roller

    private val rollerMonitorViewModel: RollerMonitorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Roller>(ROLLER_LABEL)?.let {
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
        initViewModelObservations()
    }

    private fun initUi() {
        binding.tvRollerName.text = getString(R.string.device_name_label, rollerArg.deviceName)
        binding.sRollerPosition.value = rollerArg.position?.toFloat() ?: 0F
        activity?.findViewById<ShapeableImageView>(R.id.b_profile)?.visibility = View.GONE
    }

    private fun initListeners() {
        binding.bConfirmRoller.setOnClickListener {
            rollerMonitorViewModel.updateRollerConfiguration(
                Roller(
                    rollerArg.id,
                    rollerArg.deviceName,
                    rollerArg.productType,
                    binding.sRollerPosition.value.toInt()
                )
            )
        }
    }

    private fun initViewModelObservations() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            rollerMonitorViewModel.updatedRollerEvent.collect { event ->
                when (event) {
                    is RollerMonitorViewModel.UpdatedRollerEvent.ShowUpdatedRollerMessage -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}