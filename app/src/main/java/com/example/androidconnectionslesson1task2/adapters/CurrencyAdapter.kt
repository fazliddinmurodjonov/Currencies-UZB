package com.example.androidconnectionslesson1task2.adapters

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.example.androidconnectionslesson1task2.databinding.ItemCurrencyBinding
import com.example.androidconnectionslesson1task2.models.Currency

import kotlin.collections.ArrayList

class CurrencyAdapter(var currencyList: ArrayList<Currency>, var context: Context) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {
    lateinit var currencyAdapter: OnItemClick

    fun interface OnItemClick {
        fun onClick(currency: Currency)
    }

    fun setOnItemClickListener(listener: OnItemClick) {
        currencyAdapter = listener
    }

    inner class CurrencyViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(currency: Currency) {
            val imageId = context.resources.getIdentifier(currency.Ccy.lowercase(),
                "drawable",
                context.packageName)
            binding.countryFlag.setImageResource(imageId)
            binding.currencyName.text = currency.CcyNm_EN
            binding.root.setOnClickListener {
                currencyAdapter.onClick(currency)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.onBind(currencyList[position])
    }

    override fun getItemCount(): Int = currencyList.size
}