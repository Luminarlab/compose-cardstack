# CardStack
![Compose Version](https://img.shields.io/badge/Jetpack%20Compose-1.0.0--alpha09-brightgreen)

## Table of contents
- [Description](#description)
- [Overview](#overview)
- [Installation](#installation)
- [Contributing](#contributing)

## Description
Use swipeable cards like Tinder Cards in Jetpack Compose.
This project was originally forked from [DavideC00](https://github.com/davideC00/CardStack) and changed to support the latest compose version and be more generic.

## Overview
![MainGif](./docs/cardstack.gif)

## Installation
 Latest version: [ ![Download](https://api.bintray.com/packages/luminarlab/compose-cardstack/compose-cardstack/images/download.svg) ](https://bintray.com/luminarlab/compose-cardstack/compose-cardstack/_latestVersion)


Add the maven repo to your root `build.gradle` (If you haven't already)

```groovy
allprojects {
    repositories {
       jcenter()
    }
}
```

Add the dependency:
```groovy
dependencies {
    implementation 'com.luminarlab.ui:compose-cardstack:{latest_version}'
}
```

## Usage
A simple use case
```kotlin
//EmptyStack() and CardContent() are custom composables and not included in the library
 CardStack(
                modifier = Modifier,
                items = items,
                empty = {
                    EmptyStack()
                },
                buttons = {
                    CardStackButtons()
                }
            ) { item, index ->
                SwipeableCard(index = index) {
                    CardContent(item = item)
                }
            }
```
See more in the example app


## Contributing
Any contribution is appreciated
