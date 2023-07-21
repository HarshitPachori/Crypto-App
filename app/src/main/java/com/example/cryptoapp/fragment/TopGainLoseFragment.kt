package com.example.cryptoapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapp.adapter.MarketAdapter
import com.example.cryptoapp.api.ApiInterface
import com.example.cryptoapp.api.ApiUtilities
import com.example.cryptoapp.databinding.FragmentTopGainLoseBinding
import com.example.cryptoapp.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections

class TopGainLoseFragment : Fragment() {
    lateinit var binding: FragmentTopGainLoseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTopGainLoseBinding.inflate(layoutInflater)
        getMarketData()
        return binding.root
    }

    private fun getMarketData() {
        val position = requireArguments().getInt("position")
        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()
            if (res.body() != null) {
                // now get on to main thread
                withContext(Dispatchers.Main) {
                    val dataItem = res.body()!!.data.cryptoCurrencyList
                    Collections.sort(dataItem) { o1, o2 ->
                        (o2.quotes[0].percentChange24h.toInt())
                            .compareTo(o1.quotes[0].percentChange24h.toInt())
                    }
                    binding.spinKitView.visibility = View.GONE
                    val list = ArrayList<CryptoCurrency>()
                    if (position == 0) {
                        //                            show in increasing order to get winers

                        list.clear()
                        for (i in 0..9) {
                            list.add(dataItem[i])
                        }
                        binding.topGainLoseRecyclerView.adapter =
                            MarketAdapter(requireContext(), list, "home")
                    } else {
                        list.clear()
                        for (i in 0..9) {
//                            show in decreasing order to get losers
                            list.add(dataItem[dataItem.size - 1 - i])
                        }
                        binding.topGainLoseRecyclerView.adapter =
                            MarketAdapter(requireContext(), list, "home")
                    }
                }
            }
        }
    }
}