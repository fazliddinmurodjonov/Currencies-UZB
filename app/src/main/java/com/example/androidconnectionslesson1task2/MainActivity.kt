package com.example.androidconnectionslesson1task2

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.androidconnectionslesson1task2.adapters.CurrencyAdapter
import com.example.androidconnectionslesson1task2.databinding.ActivityMainBinding
import com.example.androidconnectionslesson1task2.databinding.CustomDialogCurrencyBinding
import com.example.androidconnectionslesson1task2.models.Currency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding: ActivityMainBinding by viewBinding()
    lateinit var requestQueue: RequestQueue
    lateinit var currencyAdapter: CurrencyAdapter
    val url = "https://cbu.uz/oz/arkhiv-kursov-valyut/json/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestQueue = Volley.newRequestQueue(this)


        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,
            url,
            null,
            { response ->
                val type = object : TypeToken<ArrayList<Currency>>() {}.type
                val currencyList =
                    Gson().fromJson<ArrayList<Currency>>(response.toString(), type)
                currencyAdapter = CurrencyAdapter(currencyList, this)
                binding.currenciesRV.adapter = currencyAdapter
                currencyAdapter.setOnItemClickListener { currency ->

                    val dialog = Dialog(this)
                    val dialogView =
                        CustomDialogCurrencyBinding.inflate(layoutInflater)
                    dialog.setContentView(dialogView.root)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialogView.currencyTv.text =        "Currency      : 1 ${currency.CcyNm_EN}"
                    dialogView.symbolCodeTv.text =      "Symbol code   : ${currency.Ccy}"
                    dialogView.numberCodeTv.text =      "Number code   : ${currency.Code}"
                    dialogView.changeTv.text =          "Change        : ${currency.Diff}"
                    dialogView.exchangeRateTv.text =    "Exchange rate : ${currency.Rate}"
                    dialogView.dateTv.text =            "Date          : ${currency.Date}"
                    dialog.show()
                }
            }
        ) { }
        requestQueue.add(jsonArrayRequest)

    }

}