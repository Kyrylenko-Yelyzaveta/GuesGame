package com.lolgame.guesgame.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lolgame.guesgame.R
import com.lolgame.guesgame.databinding.FragmentResultGameBinding
import com.lolgame.guesgame.ui.vm.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultGameFragment : Fragment() {

    private lateinit var binding: FragmentResultGameBinding
    val args: ResultGameFragmentArgs by navArgs()
    private val viewModel:SharedViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultGameBinding.inflate(layoutInflater)

        fillResultOfGameText()
        initListeners()

        return  binding.root
    }

    private fun initListeners() {
        binding.btTryAgain.setOnClickListener(){
                viewModel.resetLives()
                Log.d("hueta3", "hUETA")
            findNavController().navigate(R.id.action_resultGameFragment_to_gameFragment)
        }

    }

    private fun fillResultOfGameText() {
        if (args.winOrNo){
            "You have won!!!"

        }else{
            binding.txtValueOfResult.text = "Oops, you've used up all your lives. The number was ${args.hiddenNumber} "
        }
    }
}