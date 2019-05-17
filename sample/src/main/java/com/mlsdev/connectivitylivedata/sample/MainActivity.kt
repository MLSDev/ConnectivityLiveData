package com.mlsdev.connectivitylivedata.sample

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.mlsdev.connectivitylivedata.library.ConnectivityState

class MainActivity : AppCompatActivity(), LifecycleOwner {

    lateinit var connectivityText: TextView
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectivityText = findViewById(R.id.text_connectivity_state)
        viewModel = MainViewModel(application)

        viewModel.getConnectivity().observe(this, Observer { connectivityState ->
            connectivityText.text = if (connectivityState == ConnectivityState.CONNECTED)
                "Your app is able to perform network operations"
            else
                "Your app isn't able to fetch date from the network"
        })

    }
}
