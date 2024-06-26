package com.example.notesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAddNoteBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewmodel


class AddNoteFragment : Fragment(R.layout.fragment_add_note),MenuProvider {

    private var addNotebinding: FragmentAddNoteBinding? = null
    private val binding get() = addNotebinding!!

    private lateinit var notesViewModel: NoteViewmodel
    private lateinit var addNoteView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addNotebinding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteviewmodel
        addNoteView = view

    }

    private fun saveNote(view: View){
        val notetitle = binding.addNoteTitle.text.toString().trim()
        val notebody = binding.addNoteDesc.text.toString().trim()

        if(notetitle.isNotEmpty()) {
            val note = Note(0, notetitle, notebody)
            notesViewModel.addNote(note)

            Toast.makeText(requireContext(), "Task Added", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        }else{
            Toast.makeText(addNoteView.context, "Please enter Task title", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.saveMenu -> {
                saveNote(addNoteView)
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNotebinding = null
    }


}