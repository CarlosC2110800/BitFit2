package com.example.bitfit2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var exerciseAddButton: Button
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Define the fragments here
        val listOfExercisesFragment: Fragment = ExerciseListFragment()
        val overviewFragment: Fragment = OverviewFragment()

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.main_menu -> fragment = listOfExercisesFragment
                R.id.dash -> fragment = overviewFragment
            }
            replaceFragment(fragment)
            true
        }

        // Default selection
        var count = 0
        if (count == 0) {
            bottomNavigationView.selectedItemId = R.id.main_menu
            ++count
        }


        exerciseAddButton = findViewById(R.id.exerciseButton)

        exerciseAddButton.setOnClickListener {
            val intentMain = Intent(this, ExerciseActivity::class.java)
            this.startActivity(intentMain)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.all_frame_layout, fragment)
        fragmentTransaction.commit()
    }

}