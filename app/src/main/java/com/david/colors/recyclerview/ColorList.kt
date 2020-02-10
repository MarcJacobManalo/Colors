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
import com.david.colors.model.Color
import android.content.SharedPreferences
import com.david.colors.R
import com.david.colors.`interface`.RetrofitInit
import com.david.colors.model.ColorList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_color_list.*
import retrofit2.Response


class ColorList : Fragment(),OnClickItemsColor{

    private var mNavController: NavController?= null
    private lateinit var mPref: SharedPreferences
    private var mCompositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        mNavController = Navigation.findNavController(view)
    }

    override fun onItemClicked(item: Color, position: Int) {
        val name = item.name
        val pantone = item.pantone_value
        val color = item.color

        val editor = requireContext().getSharedPreferences("SP",MODE_PRIVATE).edit()
        editor.putString("NAME",name)
        editor.putString("PANTONE",pantone)
        editor.putString("COLOR",color)
        editor.apply()

        mNavController?.navigate(R.id.action_colorList_to_colorDetails)
    }

    private fun getResponse() {
        val retrofit = RetrofitInit.create()
        mCompositeDisposable?.add(retrofit.getAllColors()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe{
                    t1: Response<ColorList>?, t2: Throwable? ->

                if(t1 != null){
                    if (t1.isSuccessful){
                        t1.body()?.data?.let { handleResponse(it) }
                    } }
                else{
                    Toast.makeText(requireContext(),"No Internet Connection",Toast.LENGTH_SHORT).show()
                    d("error", t2?.message.toString())
                }

            })
    }

    private fun handleResponse(data: List<Color>) {
        color_list_RecyclerView.layoutManager = LinearLayoutManager(context)
        color_list_RecyclerView.hasFixedSize()

        val adapter = CustomAdapter(data,this)
        color_list_RecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun sessionGreetUser() {
        mPref = requireContext().getSharedPreferences("user_details", MODE_PRIVATE)
        val userName = mPref.getString("password","User ")!!
        if (userName.contains("cityslicka")) {
            Toast.makeText(requireContext(), "Hello, eve! ", Toast.LENGTH_SHORT).show() }
        else if (userName.contains("pistol")) {
            Toast.makeText(requireContext(), "Hello, holt! ", Toast.LENGTH_SHORT).show() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mCompositeDisposable?.clear()
    }

}
