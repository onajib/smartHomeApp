package com.sncf.android.smarthomeapp.ui.profil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sncf.android.smarthomeapp.databinding.FragmentProfilBinding
import com.sncf.android.smarthomeapp.ui.main.MainViewModel
import com.sncf.android.smarthomeapp.ui.model.User
import com.sncf.android.smarthomeapp.utils.Constants
import com.sncf.android.smarthomeapp.utils.DateUtils.initDatePicker

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfilBinding

    private lateinit var userArg: User

    private val mainViewModel: MainViewModel by activityViewModels()

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
    }

    private fun initUi() {
        userArg.run {
            binding.tiFirstName.editText?.setText(this.firstName)
            binding.tiLastName.editText?.setText(this.lastName)
            binding.tiBirthDate.editText?.setText(this.birthDate)
            binding.tiStreet.editText?.setText(this.address.street)
            binding.tiStreetCode.editText?.setText(this.address.streetCode)
            binding.tiPostalCode.editText?.setText(this.address.postalCode)
            binding.tiCity.editText?.setText(this.address.city)
            binding.tiCountry.editText?.setText(this.address.country)
        }
    }

    private fun initListeners() {
        with(binding.tiBirthDate.editText) {
            this?.setOnClickListener {
                initDatePicker(requireContext(), this)
            }
        }
    }
}