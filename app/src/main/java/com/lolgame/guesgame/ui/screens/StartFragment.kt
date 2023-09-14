package com.lolgame.guesgame.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lolgame.guesgame.R
import com.lolgame.guesgame.databinding.FragmentStartBinding


class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
//    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(layoutInflater)
//        Log.i("RetroWinStart", viewModel.getInteger().toString())

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

    }

    private fun initListeners() {
        binding.btnStartGame.setOnClickListener(){
            findNavController().navigate(R.id.action_startFragment_to_gameFragment)
        }
    }
}
//
//    private fun retrofit() {
//        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
//        val call = apiService.getPost()
//
//        call.enqueue(object : Callback<Int> {
//            override fun onResponse(call: Call<Int>, response: Response<Int>) {
//                if (response.isSuccessful) {
//                    val apiResponse = response.body()
//                    binding.welcomeText.text = apiResponse.toString()
//                    Log.i("RetroWin", apiResponse.toString())
//                    // Do something with the response data
//                } else {
//                    Log.i("RetroWin", "Error: ${response.code()}")
//                    // Handle the error
//                }
//            }
//
//            override fun onFailure(call: Call<Int>, t: Throwable) {
//                Log.i("RetroWin", "Throwable, connection problem: ${t.message}")
//                // Handle the network failure
//            }
//        })
//    }
//}
//
//object RetrofitClient {
//    private const val BASE_URL = "https://www.random.org/" // Replace with your API base URL
//
//    val retrofit: Retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//}
//
//interface ApiService {
//    //    @Headers("Content-Type: application/json")
//    @GET("integers/?num=1&min=1&max=100&col=1&&base=10&format=plain&rnd=new") // Replace with the actual endpoint
//    fun getPost(): Call<Int>
//}