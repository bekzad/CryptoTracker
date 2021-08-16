package com.bekzad.cryptotracker.ui.coins

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bekzad.cryptotracker.R
import com.bekzad.cryptotracker.databinding.FragmentCoinsBinding

class CoinsFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentCoinsBinding

    private val adapter: CoinsAdapter by lazy {
        CoinsAdapter(CoinClickListener {
            "Future feature: Will move to detailview of ${it.name}".toast(context)
        })
    }

    private val viewModel by viewModels<CoinsViewModel> {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        CoinsViewModelFactory(activity.application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCoinsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
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
        binding.coinsListRv.adapter = adapter
        viewModel.coins.observe(viewLifecycleOwner, { list ->
            list?.let {
                adapter.submitList(it)
            }
        })
    }

    private fun setUpRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    /**
     * Shows the refresher icon if loading alerts the user if network error happened
     */
    private fun setUpNetworkStatus() {
        viewModel.status.observe(viewLifecycleOwner, { status ->
            status?.let {
                when(it) {
                    CoinsApiStatus.LOADING -> {
                        binding.swipeRefresh.isRefreshing = true
                    }
                    CoinsApiStatus.DONE -> {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    CoinsApiStatus.ERROR -> {
                        requireContext().showInternetAlert()
                        viewModel.alertFinished()
                    }
                }
            }
        })
    }

    /**
     *  Creating a search menu
     *  Can be searched by name and a symbol of a coin
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        viewModel.searchDatabase(query).observe(viewLifecycleOwner, { list ->
            list?.let {
                adapter.submitList(it)
            }
        })
    }
}