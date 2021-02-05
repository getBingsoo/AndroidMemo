package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.lesson1.R

class DestinationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_destination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val valueOne = it.getString(KEY_VALUE_ONE)
            val valueTwo = it.getString(KEY_VALUE_TWO)

            Toast.makeText(requireContext(), valueOne + valueTwo, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val KEY_VALUE_ONE = "KEY_VALUE_ONE"
        private const val KEY_VALUE_TWO = "KEY_VALUE_TWO"

        @JvmStatic
        fun newInstance(valueOne: String, valueTwo: String) =
            DestinationFragment().apply {
                arguments = bundleOf(
                    KEY_VALUE_ONE to valueOne,
                    KEY_VALUE_TWO to valueTwo
                )
            }

        fun findFragment(fragmentManager: FragmentManager, @IdRes id: Int): DestinationFragment? {
            return fragmentManager.findFragmentById(id) as? DestinationFragment
        }
    }
}











