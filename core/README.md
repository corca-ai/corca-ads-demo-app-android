#  corcaAds_core
[![Analytics mavencentral badge](https://img.shields.io/maven-central/v/io.github.corca-ai/corcaAds_core.svg)](https://central.sonatype.com/artifact/io.github.corca-ai/corcaAds_core) 

A Android plugin that stores and provides resources commonly used by corcaAds.  
Because all plugins in corcaAds depend on corcaAds_core, the function of corcaAds_core must be called first.

To learn more about corcaAds, please visit the [corcaAds website](https://www.corcaAds.ai/)

## Getting Started
To get started with corcaAds account, please register [corcaAds account](https://app.corcaAds.ai/en/)

## Usage
There is a simple use example:
```kotlin
import com.corcaai.corcaAds_core.feature.corcaAdsCore

class CoreActivity : AppCompatActivity() {
    
    ...
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // this is important to call `corcaAdsCore.init(clientId: 'corcaAds_STORE_ID')` function.
        corcaAdsCore.initializeApp("67592c00-a230-4c31-902e-82ae4fe71866")
    }
}
```
To learn more about usage of plugin, please visit the [corcaAdsCore Usage documentation.](https://docs.corcaAds.ai/en/sdk/core/android)

## Issues and feedback
If the plugin has issues, bugs, feedback, Please contact <dev@corca.ai>.
