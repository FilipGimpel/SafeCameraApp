package com.gimpel.safecamera.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gimpel.safecamera.R
import com.gimpel.safecamera.databinding.FragmentPhotoGridBinding
import com.gimpel.safecamera.storage.Photo
import com.gimpel.safecamera.ui.view.PhotoThumbnailAdapter
import com.gimpel.safecamera.utils.viewLifecycleLazy
import com.gimpel.safecamera.viewmodel.PhotoGridViewModel
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoGridFragment : BaseFragment(R.layout.fragment_photo_grid),
    PhotoThumbnailAdapter.OnItemClickListener {
    private val viewModel by viewModel<PhotoGridViewModel>()
    private val binding by viewLifecycleLazy { FragmentPhotoGridBinding.bind(requireView()) }

    private val picasso: Picasso by inject()

    private lateinit var viewAdapter: PhotoThumbnailAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        viewAdapter = PhotoThumbnailAdapter(
            ArrayList(),
            picasso,
            this
        )

        binding.photoList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        binding.buttonCapture.setOnClickListener {
            findNavController().navigate(R.id.action_PhotoGridFragment_to_CameraFragment)
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.deleteAllPhotos()
            viewAdapter.notifyDataSetChanged()
        }

        viewModel.getAllPhotos().observe(viewLifecycleOwner, Observer { photos ->
            viewAdapter.setPhotos(photos)
        })

    }

    override fun onItemClick(photo: Photo) {
        val action =
            PhotoGridFragmentDirections.actionPhotoGridFragmentToPhotoDetailFragment(photo.uid)
        findNavController().navigate(action)
    }
}

