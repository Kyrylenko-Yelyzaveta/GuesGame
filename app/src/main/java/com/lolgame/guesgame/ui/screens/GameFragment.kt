package com.lolgame.guesgame.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lolgame.guesgame.databinding.FragmentGameBinding
import com.lolgame.guesgame.ui.vm.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GameFragment : Fragment() {
     private var listOfLivesImages = mutableListOf<ImageView>()


    private val viewModel: SharedViewModel by viewModels()
    private lateinit var binding: FragmentGameBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(layoutInflater)
        listOfLivesImages = mutableListOf( binding.fifthItem, binding.fourthItem,binding.thirdItem, binding.secondItem,binding.firstItem)
        for (items in 0 until listOfLivesImages.size) {
            listOfLivesImages[items].visibility = View.VISIBLE // or View.GONE to completely remove them from layout
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState==null) startAction()

        initListeners()
        initObservers()
    }

    private fun startAction() {
        lifecycleScope.launch {
            viewModel.getInteger()
        }
    }

    private fun initObservers() {
        viewModel.lives.observe(viewLifecycleOwner){
            if (it != null && it!=5) {
                for (items in it until listOfLivesImages.size) {
                    listOfLivesImages[items].visibility = View.INVISIBLE // or View.GONE to completely remove them from layout
                }
            }
        }

    }

    private fun initListeners() {
        binding.apply {
            btnConfirmAnswer.setOnClickListener() {
                val inputNumber = edTxtAction.text.toString()
                if (viewModel.isTextValid(inputNumber)) {
                    if (viewModel.checkForWin(inputNumber)) {
                        lifecycleScope.launch {
                            navigateToResultScreen(true)
                        }
                    } else {
                        lifecycleScope.launch {
                            showMessage(viewModel.checkWhatsWrong(inputNumber.toInt()))
                            viewModel.updateLivesCounter()
                            if (listOfLivesImages[1].visibility==View.INVISIBLE) navigateToResultScreen(false)
                        }
                    }
                } else {
                    showMessage("Invalid input. Please enter a valid value.")
                }
            }
        }
    }

    private suspend fun navigateToResultScreen(winOrLose: Boolean) {
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToResultGameFragment()
            .setWinOrNo(winOrLose)
            .setHiddenNumber(viewModel.getHiddenNumberUseCase()))
        viewModel.clearData()
//        onDestroy()
    }

    private fun showMessage(textMessage: String) {
        binding.txtTaskGame.text = textMessage
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.rememberState()
    }
}