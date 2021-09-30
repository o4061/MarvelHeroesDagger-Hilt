package com.userfaltakas.marvelheroesdagger_hilt.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.userfaltakas.marvelheroesdagger_hilt.R
import com.userfaltakas.marvelheroesdagger_hilt.data.api.Result
import com.userfaltakas.marvelheroesdagger_hilt.data.ui.HeroPreviewDestination
import com.userfaltakas.marvelheroesdagger_hilt.databinding.FragmentHeroPreviewBinding
import com.userfaltakas.marvelheroesdagger_hilt.ui.activity.StartViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HeroPreviewFragment : Fragment() {
    private var _binding: FragmentHeroPreviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<StartViewModel>()
    private lateinit var hero: Result
    private val args: HeroPreviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeroPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContext()

        binding.closeBtn.setOnClickListener {
            val action =
                HeroPreviewFragmentDirections.actionHeroPreviewFragmentToAllHeroesFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
    }

    private fun hireHero() {
        Glide.with(requireContext()).load(hero.thumbnail?.getURL()).into(binding.image)
        binding.hireBtn.apply {
            text = getString(R.string.hire)
            setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.purple_500
                )
            )
            setOnClickListener {
                viewModel.addHeroToSquad(args.hero)
                Toast.makeText(requireContext(), "Hero hired", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fireHeroAlertDialog(): AlertDialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Are you sure!")
            .setMessage("Do you want to fire the hero?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.removeHeroFromSquad(args.hero)
                Toast.makeText(requireContext(), "Hero fired", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ ->
            }.create()
    }

    private fun fireHero() {
        Glide.with(requireContext()).load(hero.thumbnail?.path).into(binding.image)
        binding.hireBtn.apply {
            text = getString(R.string.fire)
            setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
            setOnClickListener {
                fireHeroAlertDialog().show()
            }
        }
    }

    private fun setContext() {
        hero = args.hero

        when (args.destination) {
            HeroPreviewDestination.FROM_SQUAD -> {
                fireHero()
            }
            HeroPreviewDestination.FROM_WEB -> {
                hireHero()
            }
        }

        binding.apply {
            heroName.text = hero.name
            description.text = hero.description
        }
    }
}