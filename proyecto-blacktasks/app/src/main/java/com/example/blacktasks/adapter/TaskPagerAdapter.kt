package com.example.blacktasks.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.blacktasks.view.fragments.AllTasksFragment
import com.example.blacktasks.view.fragments.CompletedTasksFragment
import com.example.blacktasks.view.fragments.NoTasksCompletedFragment

class TaskPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val idUsuario: Int // Agregar idUsuario al adaptador
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3 // Número de pestañas

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllTasksFragment.newInstance(idUsuario)
            1 -> NoTasksCompletedFragment.newInstance(idUsuario)
            2 -> CompletedTasksFragment.newInstance(idUsuario)
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
