package com.gimpel.safecamera.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gimpel.safecamera.R
import com.gimpel.safecamera.databinding.FragmentCameraBinding
import com.gimpel.safecamera.storage.FileManager
import com.gimpel.safecamera.utils.viewLifecycleLazy
import com.gimpel.safecamera.viewmodel.CameraViewModel
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CameraFragment : BaseFragment(R.layout.fragment_camera) {
    private val viewModel by viewModel<CameraViewModel>()
    private val fileManager : FileManager by inject()

    private val binding by viewLifecycleLazy { FragmentCameraBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.camera.setLifecycleOwner(viewLifecycleOwner)

        binding.camera.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)

                showLoading()

                val file = fileManager.createTmpPhotoFile()

                result.toFile(file) {
                    viewModel.processPhotoFile(file.path)
                }
            }
        })

        binding.fabCapture.setOnClickListener {
            binding.camera.takePicture()
        }

        viewModel.photoSuccessfullyCaptured.observe(viewLifecycleOwner, Observer {
            findNavController().popBackStack()
        })
    }

    private fun showLoading() {
        binding.camera.visibility = View.GONE
        binding.loadingGroup.visibility = View.VISIBLE
    }
}