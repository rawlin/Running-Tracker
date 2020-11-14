package com.example.runningtracker.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.runningtracker.R
import com.example.runningtracker.databinding.FragmentSetupBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import com.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.other.Constants.KEY_NAME
import com.other.Constants.KEY_WEIGHT
import dagger.hilt.android.AndroidEntryPoint
import java.lang.NumberFormatException
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment:Fragment(R.layout.fragment_setup) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @set:Inject
    var isFirstAppOpen=true

    private var _binding: FragmentSetupBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentSetupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!isFirstAppOpen){
            val navOptions=NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment,true)
                .build()
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment,
                savedInstanceState,
                navOptions
            )
        }

        binding.tvContinue.setOnClickListener {
            val success=writePersonalDataToSharedPref()
            if(success){
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)

            }else{
                Snackbar.make(requireView(),"Please enter all the fields",Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun writePersonalDataToSharedPref():Boolean{
        val name=binding.etName.text.toString()
        val weight=binding.etWeight.toString()
        if(name.isEmpty()||weight.isEmpty()){
            return false
        }
        var f:Float?=null
        try {
            val f=weight.toFloat()
        }catch (e:NumberFormatException){
            f=0.0f
        }

        sharedPreferences.edit()
            .putString(KEY_NAME,name)
            .putFloat(KEY_WEIGHT,f!!)
            .putBoolean(KEY_FIRST_TIME_TOGGLE,false)
            .apply()

        val toolbarText= "Let's go, $name!"
        val toolbar=requireActivity().findViewById<MaterialTextView>(R.id.tvToolbarTitle)!!
        toolbar.text = toolbarText
        return true

    }
}