package com.bekzad.cryptotracker.ui.coins

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bekzad.cryptotracker.databinding.FragmentCoinsBinding

class CoinsFragment : Fragment() {

    private lateinit var binding: FragmentCoinsBinding

    private val viewModel: CoinsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, CoinsViewModelFactory(activity.application))
            .get(CoinsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCoinsBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = CoinsAdapter(CoinClickListener {
            Toast.makeText(context, "will move to detailview of ${it.name}", Toast.LENGTH_SHORT).show()
        })

        binding.coinsListRv.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
            binding.swipeRefresh.isRefreshing = false
        }

        checkNetworkConnection(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun checkNetworkConnection(context: Context) {
        val ConnectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo == null || networkInfo.isConnected == false) {
            Toast.makeText(context, "Network Not Available", Toast.LENGTH_LONG).show()
        }
    }
}