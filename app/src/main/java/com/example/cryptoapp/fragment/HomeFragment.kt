package com.example.cryptoapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.GONE
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import androidx.viewpager2.widget.ViewPager2.VISIBLE
import com.example.cryptoapp.R
import com.example.cryptoapp.adapter.TopGainLosePagerAdapter
import com.example.cryptoapp.adapter.TopMarketAdapter
import com.example.cryptoapp.api.ApiInterface
import com.example.cryptoapp.api.ApiUtilities
import com.example.cryptoapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        getTopCurrencyList()
        setTabLayout()

        return binding.root
    }

    private fun setTabLayout() {
        val adapter = TopGainLosePagerAdapter(this)
        binding.contentViewPager.adapter = adapter
        // jab ham fragment tab me swipe krenge to callback generate hoga
        binding.contentViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    binding.topGainIndicator.visibility = VISIBLE
                    binding.topLoseIndicator.visibility = GONE
                } else {

                    binding.topGainIndicator.visibility = GONE
                    binding.topLoseIndicator.visibility = VISIBLE
                }
            }
        })

        TabLayoutMediator(binding.tabLayout,binding.contentViewPager){
            tab,position  ->
            var title  = if(position == 0){
                "Top Gainers"
            }else{
                "Top Losers"
            }
            tab.text = title
        }.attach()
    }

    private fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

//    binding list with  adapter
            // using dispatcher bcz it is the background work and we have to do the main ui work on main thread while the background work on different thread dispacthers
            withContext(Dispatchers.Main) {
                binding.topCurrencyRecyclerView.adapter =
                    TopMarketAdapter(requireContext(), res.body()!!.data.cryptoCurrencyList)
            }
            Log.d("TAG", "getTopCurrencyList: ${res.body()!!.data.cryptoCurrencyList}")
        }

    }
}