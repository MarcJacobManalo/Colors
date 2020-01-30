package com.david.colors.recyclerview

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log.d
import android.view.*
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
import com.david.colors.adapter.OnClickItemsColor
import com.david.colors.api_retrofit.DataClassColorApi
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import android.content.SharedPreferences
import com.david.colors.R


class ColorList : Fragment(),OnClickItemsColor{

    private var navController: NavController?= null
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        pref = context!!.getSharedPreferences("user_details", MODE_PRIVATE)
        navController?.navigate(R.id.action_colorList_to_loginFragment)
        val userName = pref.getString("username","User")
        if(userName != null){
            Toast.makeText(context,"Hello, $userName ",Toast.LENGTH_SHORT).show()
        }
    }
    override fun onResume() {
        super.onResume()

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
                d("RESPONSE", "onResponse: $colors")

                showData(colors!!)
            }

            override fun onFailure(call: Call<ColorListApi>, t: Throwable) {
                d("FAILURE", "onFailure")
                d("FAILURE", "onFailure ${t.message}")

            }
        })

    }
    fun showData(data: List<DataClassColorApi>) {
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.color_list_RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.hasFixedSize()

        val adapter = CustomAdapter(data,this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

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
        if (item.itemId == R.id.opLogout) {
                val editor = pref.edit()
                editor.clear()
                editor.apply()

                navController?.navigate(R.id.action_colorList_to_loginFragment)

        }
        else if (item.itemId == R.id.opSetting){
            Toast.makeText(activity, "Setting!", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onItemClicked(item: DataClassColorApi, position: Int) {
        val name = item.name
        val pantone = item.pantone_value
        val color = item.color

        val editor = activity!!.getSharedPreferences("SP",MODE_PRIVATE).edit()
        editor.putString("NAME",name)
        editor.putString("PANTONE",pantone)
        editor.putString("COLOR",color)
        editor.apply()
        d("n",name)
        d("p",pantone)
        d("c",color)

        navController?.navigate(R.id.action_colorList_to_colorDetails)
    }

}
