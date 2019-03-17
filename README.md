# Android MetaParser

[![](https://jitpack.io/v/dan-0/metaparser.svg)](https://jitpack.io/#dan-0/metaparser)


A library to parse basic meta tags from a website.

Currently relies primarily on basic [Open Graph](http://ogp.me/#metadata) tags.

Basic usage:

```kotlin
val parser = MetaParserBuilder().build()

val data = try {
                parser.parseWebsite(url, 5000)
            } catch (e: IOException) {
                Log.e(TAG, "Timeout occurred")
                return@launch
            }
            
findViewById<TextView>(R.id.titleResult).text = data.title
findViewById<TextView>(R.id.urlResult).text = data.url
findViewById<TextView>(R.id.imageUrlResult).text = data.imageUrl
findViewById<TextView>(R.id.typeResult).text = data.type
findViewById<TextView>(R.id.descriptionResult).text = data.description
```

To use:

Add JitPack as a repository
```gradle
    allprojects {
            repositories {
                    maven { url 'https://jitpack.io' }
            }
    }
```

Add the dependency:
```gradle
    dependencies {
            implementation 'com.github.dan-0:metaparser:v0.1.0'
    }
```