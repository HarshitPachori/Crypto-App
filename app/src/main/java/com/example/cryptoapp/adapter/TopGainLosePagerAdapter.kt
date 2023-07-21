package com.example.cryptoapp.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cryptoapp.fragment.TopGainLoseFragment


class TopGainLosePagerAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
       return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = TopGainLoseFragment()
        val bundle = Bundle()
        // iske through hame position milegi jo ham set krke loss ya gain wala show krdenge fragment
        bundle.putInt("position" , position)
        fragment.arguments = bundle
        return fragment
    }

}