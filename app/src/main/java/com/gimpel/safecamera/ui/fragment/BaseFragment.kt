package com.gimpel.safecamera.ui.fragment

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.gimpel.safecamera.ui.activity.MainActivity


open class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    open val isToolbarVisible = true

    private fun setActionBarVisibility(isVisible: Boolean) {
        (requireActivity() as? MainActivity)?.setActionBarVisibility(isVisible)
    }

    override fun onResume() {
        super.onResume()
        setActionBarVisibility(isToolbarVisible)
    }

    open fun hideKeyboard() {
        val inputManager: InputMethodManager = requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val currentFocusedView: View? = requireActivity().currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}