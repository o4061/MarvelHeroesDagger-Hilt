package com.userfaltakas.marvelheroesdagger_hilt.ui.fragment.heroesPreview

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.userfaltakas.marvelheroesdagger_hilt.R
import com.userfaltakas.marvelheroesdagger_hilt.data.api.Result
import com.userfaltakas.marvelheroesdagger_hilt.data.ui.ButtonState
import com.userfaltakas.marvelheroesdagger_hilt.databinding.FragmentHeroPreviewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HeroPreviewFragment : Fragment() {
    private var _binding: FragmentHeroPreviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<HeroPreviewViewModel>()
    private lateinit var hero: Result
    private lateinit var hireBtnState: ButtonState
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

        binding.hireBtn.setOnClickListener {
            if (hireBtnState == ButtonState.HIRE) {
                viewModel.addHeroToSquad(args.hero)
                fireHero()
            } else {
                fireHeroAlertDialog().show()
            }
        }
    }

    private fun fireHeroAlertDialog(): AlertDialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.are_you_sure))
            .setMessage(resources.getString(R.string.do_you_want_to_fire_hero))
            .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                viewModel.removeHeroFromSquad(args.hero)
                hireHero()
            }
            .setNegativeButton(resources.getString(R.string.no)) { _, _ ->
            }.create()
    }

    private fun fireHero() {
        binding.hireBtn.apply {
            setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
            text = getString(R.string.fire)
            hireBtnState = ButtonState.FIRE
        }
    }

    private fun hireHero() {
        binding.hireBtn.apply {
            setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.purple_500
                )
            )

            text = getString(R.string.hire)
            hireBtnState = ButtonState.HIRE
        }
    }

    private fun setContext() {
        hero = args.hero
        setButton(hero.id)
        binding.apply {
            heroName.text = hero.name
            description.text = hero.description
            Glide.with(requireContext()).load(hero.thumbnail?.getURL()).into(image)
        }
    }

    private fun setButton(id: Int) {
        viewModel.isHeroExist(id)
        viewModel.heroExist.observe(viewLifecycleOwner, { response ->
            if (response) {
                fireHero()
            } else {
                hireHero()
            }
        })
    }
}