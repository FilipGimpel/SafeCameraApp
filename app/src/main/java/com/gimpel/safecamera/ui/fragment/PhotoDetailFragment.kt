package com.gimpel.safecamera.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.gimpel.safecamera.R
import com.gimpel.safecamera.databinding.FragmentPhotoDetailBinding
import com.gimpel.safecamera.utils.viewLifecycleLazy
import com.gimpel.safecamera.viewmodel.PhotoDetailViewModel
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class PhotoDetailFragment : BaseFragment(R.layout.fragment_photo_detail) {
    private val viewModel by viewModel<PhotoDetailViewModel>()
    private val binding by viewLifecycleLazy { FragmentPhotoDetailBinding.bind(requireView()) }

    private val picasso: Picasso by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt("photoID")?.let {
            viewModel.getPhotoById(it).observe(viewLifecycleOwner, Observer { photo ->
                picasso
                    .load(File(photo.encryptedPhotoPath))
                    .placeholder(R.drawable.lock)
                    .fit()
                    .into(binding.photo);
            })
        }
    }
}