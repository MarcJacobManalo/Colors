package com.david.colors


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            context?.let{
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }

        },2500)
    }

 /*   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val URL = "https://www.google.com/search?q=COLOR+IMAGE+PNG&tbm=isch&ved=2ahUKEwiKgs6b36rnAhVWJisKHZ8sCBYQ2-cCegQIABAA&oq=COLOR+IMAGE+PNG&gs_l=img.3..0j0i5i30j0i8i30l8.147.9656..10002...0.0..0.321.723.0j3j0j1......0....1..gws-wiz-img.......0i67.wtJ9bxRimz8&ei=BX4yXsrrLtbMrAGf2aCwAQ#imgrc=0ZXvc5kWwRZn5M"
       loadIm
    }*/

}
