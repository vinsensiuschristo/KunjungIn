package com.bangkit.kunjungin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.kunjungin.data.remote.response.NearbyPlaceRecommendation
import com.bangkit.kunjungin.data.remote.response.TopRatedPlaceRecommendation
import com.bangkit.kunjungin.databinding.FragmentHomeBinding
import com.bangkit.kunjungin.ui.MainViewModel
import com.bangkit.kunjungin.utils.ViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    private lateinit var nearbyPlaceAdapter: NearbyPlaceAdapter
    private lateinit var topRatedPlaceAdapter: TopRatedPlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nearbyPlaceAdapter = NearbyPlaceAdapter()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recylerView1.layoutManager = layoutManager
        binding.recylerView1.adapter = nearbyPlaceAdapter

        topRatedPlaceAdapter = TopRatedPlaceAdapter()
        val layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recylerView2.layoutManager = layoutManager2
        binding.recylerView2.adapter = topRatedPlaceAdapter


        mainViewModel.nearbyPlacesResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                val nearbyPlaces: List<NearbyPlaceRecommendation> = response.result
                nearbyPlaceAdapter.submitList(nearbyPlaces)
            }
        }

        mainViewModel.topRatedPlacesResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                val topRatedPlaces: List<TopRatedPlaceRecommendation> = response.result
                topRatedPlaceAdapter.submitList(topRatedPlaces)
            }
        }
    }
}