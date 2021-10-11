package com.sncf.android.smarthomeapp.ui.profil.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.imageview.ShapeableImageView
import com.sncf.android.smarthomeapp.R
import com.sncf.android.smarthomeapp.data.model.User
import com.sncf.android.smarthomeapp.databinding.FragmentProfilBinding
import com.sncf.android.smarthomeapp.ui.profil.form.ProfileForm
import com.sncf.android.smarthomeapp.utils.Constants
import com.sncf.android.smarthomeapp.utils.DateUtils
import com.sncf.android.smarthomeapp.utils.DateUtils.initDatePicker
import com.sncf.android.smarthomeapp.utils.TextUtils.highlightMandatoryField
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.Date

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfilBinding

    private var userArg: User? = null

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<User>(Constants.USER_LABEL)?.let {
            userArg = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
        initViewModelObservations()
    }

    private fun initUi() {
        userArg?.let {
            binding.tiLastName.editText?.setText(it.lastName)
            binding.tiFirstName.editText?.setText(it.firstName)
            binding.tiBirthDate.editText?.setText(DateUtils.parseToString(Date(it.birthDate)))
            binding.tiStreet.editText?.setText(it.address.street)
            binding.tiStreetCode.editText?.setText(it.address.streetCode)
            binding.tiPostalCode.editText?.setText(it.address.postalCode.toString())
            binding.tiCity.editText?.setText(it.address.city)
            binding.tiCountry.editText?.setText(it.address.country)
        }
        activity?.findViewById<ShapeableImageView>(R.id.b_profile)?.visibility = View.GONE
    }

    private fun initListeners() {
        with(binding.tiBirthDate.editText) {
            this?.setOnClickListener {
                initDatePicker(requireContext(), this)
            }
            this?.doOnTextChanged { _, _, _, _ ->
                binding.tiBirthDate.editText?.error = null
            }
        }
        binding.bConfirmUser.setOnClickListener {
            profileViewModel.submitForm(
                ProfileForm(
                    userArg!!.id,
                    binding.tiLastName.editText?.text.toString(),
                    binding.tiFirstName.editText?.text.toString(),
                    binding.tiBirthDate.editText?.text.toString(),
                    binding.tiStreet.editText?.text.toString(),
                    binding.tiStreetCode.editText?.text.toString(),
                    binding.tiPostalCode.editText?.text.toString(),
                    binding.tiCity.editText?.text.toString(),
                    binding.tiCountry.editText?.text.toString()
                )
            )
        }
    }

    private fun initViewModelObservations() {
        observeForm()
        observeUserUpdate()
    }

    private fun observeForm() {
        highlightField(profileViewModel.isLastNameOnError, binding.tiLastName.editText)
        highlightField(profileViewModel.isFirstNameOnError, binding.tiFirstName.editText)
        highlightField(profileViewModel.isBirthDateOnError, binding.tiBirthDate.editText)
        highlightField(profileViewModel.isStreetOnError, binding.tiStreet.editText)
        highlightField(profileViewModel.isStreetCodeOnError, binding.tiStreetCode.editText)
        highlightField(profileViewModel.isPostalCodeOnError, binding.tiPostalCode.editText)
        highlightField(profileViewModel.isCityOnError, binding.tiCity.editText)
        highlightField(profileViewModel.isCountryOnError, binding.tiCountry.editText)
    }

    private fun highlightField(isOnError: LiveData<Boolean>, etField: TextView?) {
        isOnError.observe(viewLifecycleOwner) {
            highlightMandatoryField(etField, it)
        }
    }

    private fun observeUserUpdate() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            profileViewModel.updatedUserEvent.collect { event ->
                when (event) {
                    is ProfileViewModel.UpdatedUserEvent.ShowUpdatedUserMessage -> {
                        Toast.makeText(context, "User updated", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}