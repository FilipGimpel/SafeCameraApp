package com.gimpel.safecamera.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gimpel.safecamera.R
import com.gimpel.safecamera.databinding.FragmentLoginBinding
import com.gimpel.safecamera.utils.viewLifecycleLazy
import com.gimpel.safecamera.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment(R.layout.fragment_login){
    private val viewModel by viewModel<LoginViewModel>()
    private val binding by viewLifecycleLazy { FragmentLoginBinding.bind(requireView()) }
    override val isToolbarVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            hideKeyboard()
            viewModel.login(
                binding.inputLogin.text.toString(),
                binding.inputPassword.text.toString())
        }

        viewModel.authenticatedUser.observe(viewLifecycleOwner, Observer { user ->
            if (user != null){
                findNavController().navigate(R.id.action_LoginFragment_to_PhotoGridFragment)
            } else {
                binding.inputPassword.error = resources.getString(R.string.wrong_user_or_password)
            }
        })
    }
}