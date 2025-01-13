#  corcaAds_analytics
[![Analytics mavencentral badge](https://img.shields.io/maven-central/v/io.github.corca-ai/corcaAds_analytics.svg)](https://central.sonatype.com/artifact/io.github.corca-ai/corcaAds_analytics) 

Android plugin that collects logs for event analysis of corcaAds projects.

To learn more about corcaAds, please visit the [corcaAds website](https://www.corcaAds.ai/)
</br>

## Getting Started
To get started with corcaAds account, please register [corcaAds account](https://app.corcaAds.ai/en/)
</br>

## Usage

**corcaAdsImpressionDetector example:**

If a view equipped with `corcaAdsImpressionDetector` is exposed more than half of its size on the screen, the `onImpression` function will be called.

**Use in XML file.**

  Cover the exposed corcaAds Ad View with an `corcaAdsImpressionDetector`

  ```xml
  <ai.corca.corcaAds_analytics.feature.corcaAdsImpressionDetector
    android:id="@+id/corcaAdsImpressionDetector"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:use_impression="true">
    ...
  </ai.corca.corcaAds_analytics.feature.corcaAdsImpressionDetector>
 ```
**onClick example:**

```kotlin
corcaAdsAnalytics.onClick(
  option = logOption
)
```

**onPurchase example:**

```kotlin
corcaAdsAnalytics.onPurchase(
  option = logOption
  amount: 23910 // actual purchase price
)
```

For the `onPurchase` event, you input the actual purchase price that the customer paid into price and call the event when the customer clicks the button at the purchase point.

**onPageView example:**

```kotlin
corcaAdsAnalytics.onPageView(
  path = "main"
)
```
Called every time a screen is created
</br>

## Features

It mainly collects three events: impression, click, and purchase.

| Event | Function |
| --- | --- |
| impression | corcaAdsAnalytics.onImpression(option) |
| click | corcaAdsAnalytics.onClick(option) |
| purchase | corcaAdsAnalytics.onPurchase(option, amount) |
| pageView | corcaAdsAnalytics.onPageView(path) |


To learn more about usage of plugin, please visit the [corcaAdsAnalytics Usage documentation.](https://docs.corcaAds.ai/en/sdk/log-collection/android)
</br>

## Issues and feedback
If the plugin has issues, bugs, feedback, Please contact <dev@corca.ai>.
