package com.bekzad.cryptotracker

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

//@BindingAdapter("listData")
//fun RecyclerView.bindRecyclerView(data: List<Coin>?) {
//    val adapter = this.adapter as CoinsAdapter
//    adapter.submitList(data)
//}

/**
 * For getting an image from the specified url and setting it to that view
 */
@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val uri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(this.context)
            .load(uri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(this)
    }
}

/**
 * FOr changing the color of a text according to a given number
 */
@BindingAdapter("textColor")
fun TextView.bindTextView(priceChange: Double?) {
    priceChange?.let { price ->
        if (price >= 0) {
            this.setTextColor(Color.GREEN)
        } else {
            this.setTextColor(Color.RED)
        }
    }
}
