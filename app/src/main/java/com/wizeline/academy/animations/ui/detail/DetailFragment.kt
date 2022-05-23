package com.wizeline.academy.animations.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wizeline.academy.animations.R
import com.wizeline.academy.animations.databinding.DetailFragmentBinding
import com.wizeline.academy.animations.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(layoutInflater, container, false)
        initListeners()
        binding.ivImageDetail.loadImage(args.imageId)
        return binding.root
    }

    private fun goToMoreDetails() {
        //val directions =
        //    DetailFragmentDirections.toMoreDetailsFragment(args.imageId, viewModel.contentIndex)
        //findNavController().navigate(directions)

        val extras = FragmentNavigatorExtras(
            binding.ivImageDetail to "image_detail",
            binding.tvTitle to "title",
            binding.tvSubtitle to "subtitle"
        )

        val bundle = Bundle()
        bundle.putInt("imageId", args.imageId)
        bundle.putInt("contentIndex", viewModel.contentIndex)

        findNavController().navigate(
            R.id.moreDetailsFragment,
            bundle,
            null,
            extras
        )
    }

    private fun initListeners() {
        binding.btnMoreDetails.setOnClickListener {
            goToMoreDetails()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.title.observe(viewLifecycleOwner) { binding.tvTitle.text = it }
        viewModel.subtitle.observe(viewLifecycleOwner) { binding.tvSubtitle.text = it }
    }
}