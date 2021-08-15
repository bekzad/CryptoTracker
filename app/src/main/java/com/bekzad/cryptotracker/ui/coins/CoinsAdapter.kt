package com.bekzad.cryptotracker.ui.coins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.databinding.ListViewItemBinding

class CoinsAdapter(val clickListener: CoinClickListener) :
    ListAdapter<Coin, CoinsAdapter.CoinViewHolder>(DiffCallback) {

    class CoinViewHolder(private var binding: ListViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: Coin) {
            binding.coin = coin
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(ListViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = getItem(position)
        holder.bind(coin)
        holder.itemView.setOnClickListener {
            clickListener.onClick(coin)
        }
    }
}

class CoinClickListener(val clickListener: (coinListener: Coin) -> Unit) {
    fun onClick(coin: Coin) = clickListener(coin)
}