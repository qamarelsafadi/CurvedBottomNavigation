# CurvedBottomNavigation
A simple android library which helps you to create a curved bottom navigation

# DEMO 

![alt text](https://media3.giphy.com/media/RsTb9IItEQJiLyoAqw/giphy.gif?cid=790b76118cad4a0b5e3733aae12aa7837e19d88d5c1ab677&rid=giphy.gif&ct=g "DEMO")

# Setup 

Update your module level `build.gradle` file and add the following dependency. Please check the project releases for latest versions.

```groovy
dependencies {
 implementation 'com.github.qamarelsafadi:CurvedBottomNavigation:0.1.3'
}
```

**Important!** if your android studio version is fox and higher please add the following dependency in your project level `build.gradle`
above `plugins` 

```groovy
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

# Usage

Add `com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation` in your layout xml file.

```XML
    <com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
        android:id="@+id/bottomNavigation"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:cbn_background="@color/black"
        app:cbn_fabColor="@color/purple_200"
        app:cbn_iconColor="@color/white"
        app:cbn_height="76dp"
        app:cbn_icon_size="24dp"
        app:cbn_curve_radius="26dp"
        app:cbn_selected_icon_size="48dp"
        app:cbn_selectedIconColor="@color/white"
        app:cbn_titleColor="@color/white"
        app:cbn_titleFont="@font/book"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />
```


# XML Attributes 

| Attribute     | Description |
| ------------- | ------------- |
| app:cbn_background  | Background for bottom navigation view  |
| app:cbn_fabColor  | Background for FAB view  |
| app:cbn_iconColor  | Icon color tint  |
| app:cbn_height  | Bottom navigation height  |
| app:cbn_icon_size  | Icon item size  |
| app:cbn_curve_radius  | Curve raduis  |
| app:cbn_selected_icon_size  | Selected icon item size  |
| app:cbn_selectedIconColor  | FAB icon tint color  |
| app:cbn_titleColor  | Item title text color  |
| app:cbn_titleFont  | Item title font type |


# Setup on code 

In your Activiy defined the `ids` of your items 
```Kotlin 
    companion object {
        // you can put any unique id here, but because I am using Navigation Component I prefer to put it as
        // the fragment id.
        const val HOME_ITEM = R.id.homeFragment
        const val OFFERS_ITEM = R.id.offersFragment
        const val MORE_ITEM = R.id.moreFragment
        const val SECTION_ITEM = R.id.sectionFragment
        const val CART_ITEM = R.id.cartFragment
        const val BLANK_ITEM = R.id.blankFragment
    }
  ```
In `onCreate` 
```Kotlin 
  initNavHost()
  setUpBottomNavigation()
  ```
            

Let's setup our Navigation Component // this step is optional but I prefer it
```Kotlin
  private fun initNavHost() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
```

Now let's create our `CurvedNavigationBottomItems` 

```Kotlin 
   private fun ActivityMainBinding.setUpBottomNavigation() {
        val bottomNavigationItems = mutableListOf(
            CurvedBottomNavigation.Model(HOME_ITEM, getString(R.string.home), R.drawable._01_home),
            CurvedBottomNavigation.Model(OFFERS_ITEM, getString(R.string.offers), R.drawable.offers),
            CurvedBottomNavigation.Model(SECTION_ITEM, getString(R.string.sections), R.drawable.section),
            CurvedBottomNavigation.Model(CART_ITEM, getString(R.string.cart), R.drawable.cart),
            CurvedBottomNavigation.Model(MORE_ITEM, getString(R.string.more), R.drawable.more),
            CurvedBottomNavigation.Model(BLANK_ITEM, getString(R.string.more), R.drawable.more),
        )
        bottomNavigation.apply {
            bottomNavigationItems.forEach { add(it) }
            setOnClickMenuListener {
                navController.navigate(it.id)
            }
            show(HOME_ITEM)
            // optional
            setupNavController(navController)
        }
    }
````
   
And 
```Kotlin
  // if you need your backstack of your items always back to home please override this method
    override fun onBackPressed() {
        if (navController.currentDestination!!.id == HOME_ITEM)
            super.onBackPressed()
        else {
            when (navController.currentDestination!!.id) {
              OFFERS_ITEM -> {
                    navController.popBackStack(R.id.homeFragment, false)
                }
               SECTION_ITEM -> {
                    navController.popBackStack(R.id.homeFragment, false)
                }
                CART_ITEM -> {
                    navController.popBackStack(R.id.homeFragment, false)
                }
               MORE_ITEM -> {
                    navController.popBackStack(R.id.homeFragment, false)
                }
                else -> {
                    navController.navigateUp()
                }
            }
        }
    }
```

        
    
        
# Credits 

This library is inspired by Meow Bottom Navigation it helps me a lot to do this but seems like the owner is not reciveing the pull requests 
and I needed more custome components this Library comes out :rocket:.

