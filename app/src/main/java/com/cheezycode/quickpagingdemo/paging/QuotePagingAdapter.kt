package com.cheezycode.quickpagingdemo.paging

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cheezycode.quickpagingdemo.R
import com.cheezycode.quickpagingdemo.models.DataX

class QuotePagingAdapter(private val context: Context,
                         private val sharedPreferences: SharedPreferences,
                         private val onFavoriteClick: (DataX?) -> Unit) :
    PagingDataAdapter<DataX, QuotePagingAdapter.QuoteViewHolder>(COMPARATOR) {

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quote = itemView.findViewById<TextView>(R.id.quote)
        val ivMain = itemView.findViewById<ImageView>(R.id.ivMain)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.quote.text = item.title
        }
        val isFavorite = sharedPreferences.getBoolean("${item?.id}_favorite", false)
        holder.ivMain.setImageResource(
            if (isFavorite) R.drawable.ic_fav // Filled icon for favorite
            else R.drawable.ic_unfav // Outline icon for non-favorite
        )

        holder.ivMain.setOnClickListener {
            val newFavoriteState = !isFavorite
            sharedPreferences.edit().putBoolean("${item?.id}_favorite", newFavoriteState).apply()
            onFavoriteClick(item)
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_quote_layout, parent, false)
        return QuoteViewHolder(view)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<DataX>() {
            override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean {
                return oldItem == newItem
            }
        }
    }
}





















