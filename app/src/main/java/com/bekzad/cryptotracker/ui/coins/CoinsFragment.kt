package com.bekzad.cryptotracker.ui.coins

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting up bindings and adapters
        binding.lifecycleOwner = viewLifecycleOwner
        setupAdapter()
        setUpRefreshLayout()
        setUpNetworkStatus()
    }

    private fun setupAdapter() {
        val adapter = CoinsAdapter(CoinClickListener {
            Toast.makeText(context, "will move to detailview of ${it.name}",
                Toast.LENGTH_SHORT).show()
        })

        binding.coinsListRv.adapter = adapter
    }

    private fun setUpRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setUpNetworkStatus() {
        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                when(it) {
                    CoinsApiStatus.LOADING -> {
                        binding.swipeRefresh.isRefreshing = true
                    }
                    CoinsApiStatus.DONE -> {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    CoinsApiStatus.ERROR -> {
                        showAlert()
                        viewModel.alertFinished()
                    }
                }
            }
        })
    }

    private fun showAlert() {
        AlertDialog.Builder(requireContext())
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Internet Connection Alert")
            .setMessage("Please connect to network")
            .setPositiveButton("Close") { dialogInterface, i ->
            }.show()
    }
}