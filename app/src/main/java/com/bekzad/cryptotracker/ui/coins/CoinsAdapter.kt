package com.bekzad.cryptotracker.ui.coins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.databinding.ListViewItemBinding

// List adapter is a clean implementation of Diffutil in a background thread (We should not do it in main thread)
// Which also adds some animations
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
        // Called first. Do two items in the recyclerview have the same objects
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        // Called only when areItemsTheSame returns true. Did the contents of the item(or object) changed
        // and do i have to update the ui now
        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        // Use the Layout parameters of parent but do not attach it to the parent yet, because the attachment will do the recyclerview
        // And if we attach it to the parent we would get the reference to the parent and not the ListViewItem
        return CoinViewHolder(ListViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        // this is why our view was not obeying the constraints of its parent, it doesn't know about it
//        return CoinViewHolder(ListViewItemBinding.inflate(LayoutInflater.from(parent.context)))
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