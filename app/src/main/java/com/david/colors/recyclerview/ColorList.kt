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
import com.david.colors.adapter.CustomAdapter
import com.david.colors.adapter.OnClickItemsColor
import com.david.colors.model.ColorDataModels
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import android.content.SharedPreferences
import com.david.colors.R
import com.david.colors.`interface`.RetrofitInit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class ColorList : Fragment(),OnClickItemsColor{

    private var navController: NavController?= null
    private lateinit var pref: SharedPreferences
    private var mCompositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mCompositeDisposable = CompositeDisposable()
        sessionGreetUser()
    }
    override fun onResume() {
        super.onResume()
        getResponse()
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
        when(item.itemId) {
              R.id.opLogout -> {
                val editor = pref.edit()
                editor.clear()
                editor.apply()
                navController?.navigate(R.id.action_colorList_to_loginFragment)
            }
             R.id.opSetting -> Toast.makeText(activity, "Setting!", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(item: ColorDataModels, position: Int) {
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
    private fun getResponse() {
        val retrofit = RetrofitInit.create()
        mCompositeDisposable?.add(retrofit.getAllColors()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                handleResponse(it.data)
                d("-->COLOR_DETAILS<--", it.data.toString())
            })
    }

    private fun handleResponse(data: List<ColorDataModels>) {
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.color_list_RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.hasFixedSize()

        val adapter = CustomAdapter(data,this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }
    private fun sessionGreetUser() {
        pref = context!!.getSharedPreferences("user_details", MODE_PRIVATE)
        val userName = pref.getString("password","User ")!!
        if (userName.contains("cityslicka")) {
            Toast.makeText(context, "Hello, eve! ", Toast.LENGTH_SHORT).show() }
        else if (userName.contains("pistol")) {
            Toast.makeText(context, "Hello, holt! ", Toast.LENGTH_SHORT).show() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mCompositeDisposable?.clear()
    }

}
