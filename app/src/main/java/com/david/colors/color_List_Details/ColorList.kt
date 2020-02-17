package com.david.colors.color_List_Details

import android.os.Bundle
import android.util.Log.d
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.colors.adapter.OnClickItemsColor
import com.david.colors.model.Color
import com.david.colors.R
import com.david.colors.adapter.ColorAdapter
import com.david.colors.model.api.RetrofitInit
import com.david.colors.model.ColorList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_color_list.*
import retrofit2.Response
import java.util.*


class ColorList : Fragment(),OnClickItemsColor{

    private var mNavController: NavController? = null
    private var mCompositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCompositeDisposable = CompositeDisposable()
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
        val colorAttributes = arrayOf(
            item.color.toString().toUpperCase(Locale.ENGLISH),
            item.pantone_value.toString().toUpperCase(Locale.ENGLISH),
            item.name.toString().toUpperCase(Locale.ENGLISH))

        val action = ColorListDirections.actionColorListToColorDetails(colorAttributes)
        findNavController().navigate(action)
    }

    private fun getResponse() {
        val retrofit = RetrofitInit.create()
        mCompositeDisposable?.add(retrofit.getAllColors()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ it ->
                it.body()?.data?.let {
                    colorList(it)
                }
            },{
                Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
            }))
    }

    private fun colorList(data: List<Color>) {
        color_list_RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        color_list_RecyclerView.hasFixedSize()

        val adapter = ColorAdapter(data,this)
        color_list_RecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mCompositeDisposable?.clear()
    }

}
