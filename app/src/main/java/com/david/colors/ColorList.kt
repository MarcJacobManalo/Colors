package com.david.colors

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.david.colors.api_retrofit.ColorListApi
import com.david.colors.api_retrofit.GetUserService
import com.david.colors.adapter.CustomAdapter
import com.david.colors.api_retrofit.DataClassColorApi
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ColorList : Fragment(){
    //check update push
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)

    val retrofit = Retrofit.Builder()
        .baseUrl("https://reqres.in/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val api = retrofit.create(GetUserService::class.java)
    api.getAllColors().enqueue(object : Callback<ColorListApi> {

        override fun onResponse(call: Call<ColorListApi>, response: Response<ColorListApi>) {
            val body = response.body()
            val colors = body?.data
            d("jason", "onResponse: $colors")

            colors?.let { showData(it) }
        }

        override fun onFailure(call: Call<ColorListApi>, t: Throwable) {
            d("jason", "onFailure")
            d("jason", "onFailure ${t.message}")
        }

    })
}

    fun showData(data: List<DataClassColorApi>) {
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.color_list_RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = CustomAdapter(data)
        recyclerView.adapter = adapter

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_color_list, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.actionbar_items_layout, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.opLogout ->  navController.navigate(R.id.action_colorList_to_loginFragment)
            R.id.opSetting -> Toast.makeText(activity, "Setting!", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)

    }

}
