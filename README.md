[![License](https://img.shields.io/github/license/mashape/apistatus.svg)](https://opensource.org/licenses/MIT)
[![Download](https://api.bintray.com/packages/spetrosiukmlsdev/mlsdev/livedatasocialauth/images/download.svg)](https://bintray.com/spetrosiukmlsdev/mlsdev/connectivitylivedata/_latestVersion)


# ConnectivityLiveData
The application network state observation

## Setup
To use this library your `minSdkVersion` must be >= `19`

In your build.gradle :
```gradle
dependencies {
    implementation "com.mlsdev.connectivitylivedata:library:$latestVersion"
}
```

## Usage
* Add **ConnectivityLiveData** into your **ViewModel**
```kotlin
  class MainViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

      private val connectivityLiveData = ConnectivityLiveData(application)

      fun getConnectivity(): LiveData<ConnectivityState> =
          Transformations.switchMap(connectivityLiveData) { connectivityState ->
              MutableLiveData<ConnectivityState>().apply { value = connectivityState }
          }
  }
```
* Observe the connectivity state in your **Activity** or **Fragment** 
```kotlin
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
```

## Authors
* [Sergey Petrosyuk](mailto:petrosyuk@mlsdev.com), MLSDev

## About MLSDev

[<img src="https://cloud.githubusercontent.com/assets/1778155/11761239/ccfddf60-a0c2-11e5-8f2a-8573029ab09d.png" alt="MLSDev.com">][mlsdev]

ConnectivityLiveData is maintained by MLSDev, Inc. We specialize in providing all-in-one solution in mobile and web development. Our team follows Lean principles and works according to agile methodologies to deliver the best results reducing the budget for development and its timeline.

Find out more [here][mlsdev] and don't hesitate to [contact us][contact]!

[mlsdev]: http://mlsdev.com
[contact]: http://mlsdev.com/contact-us
[github-frederikos]: https://github.com/SerhiyPetrosyuk
