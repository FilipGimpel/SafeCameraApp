package com.gimpel.safecamera.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 this dude created it: https://gist.github.com/jamiesanson/478997780eb6ca93361df311058dc5c2
 use it as:
 private val binding by viewLifecycleLazy { YourFragmentNameBinding.bind(requireView()) }
 and don't worry about managing the reference in onDestroy

 ReadOnlyProperty is implemented so it's a delegate property and we can initialize it lazily using "by" keyword
 */
fun <T> Fragment.viewLifecycleLazy(initialise: () -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, LifecycleObserver {

        // A backing property to hold our value
        private var binding: T? = null

        private var viewLifecycleOwner: LifecycleOwner? = null

        init {
            // Observe the View Lifecycle of the Fragment
            this@viewLifecycleLazy
//              Overriding this method is no longer supported and this method will be made
//              final in a future version of Fragment. -> TODO
                .viewLifecycleOwnerLiveData
                .observe(this@viewLifecycleLazy, Observer { newLifecycleOwner ->
                    viewLifecycleOwner
                        ?.lifecycle
                        ?.removeObserver(this)

                    viewLifecycleOwner = newLifecycleOwner.also {
                        it.lifecycle.addObserver(this)
                    }
                })
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy(owner: LifecycleOwner) {
            binding = null
        }

        override fun getValue(
            thisRef: Fragment,
            property: KProperty<*>
        ): T {
            // Return the backing property if it's set, or initialise (elvis operator)
            return this.binding ?: initialise().also {
                this.binding = it
            }
        }
    }