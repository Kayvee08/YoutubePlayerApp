package com.example.youtubeplayerapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.youtubeplayerapp.databinding.FragmentFormBinding

class FormFragment : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private val viewModel: myViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.img.setImageURI(viewModel.getUri())
        binding.name.setText(viewModel.getName())
        binding.email.setText(viewModel.getEmail())
        binding.okBtn.setOnClickListener {
            viewModel.imageClicked(false)
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }
}