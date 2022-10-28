package com.example.bitfit2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Intent

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val TAG = "ExerciseListFragment"

class ExerciseListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val exercises = mutableListOf<ExerciseData>()
    private lateinit var exerciseRecyclerView: RecyclerView
    private lateinit var exerciseAdapter: MainListAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExerciseData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_exercise_list, container, false)

        // Recycler View
        exerciseRecyclerView = view.findViewById(R.id.exercise_recycler_view)
        // Layout Manager
        val layoutManager = LinearLayoutManager(context).also {
            val dividerItemDecoration = DividerItemDecoration(context, it.orientation)
            exerciseRecyclerView.addItemDecoration(dividerItemDecoration)
        }
        // Add to Recycler View
        exerciseRecyclerView.layoutManager = layoutManager
        // Set the adapter
        exerciseAdapter = MainListAdapter(view.context, exercises)
        // Add adapter to Recycler View
        exerciseRecyclerView.adapter = exerciseAdapter

        // Updated return statement to return inflated view
        return view
    }

    // Function to set the data base and add information
    private fun getExerciseData() {
        val exerciseMain = activity?.intent?.getSerializableExtra("Exercise!") as ExerciseData?

        lifecycleScope.launch {
            (activity?.application as ExerciseApplication).db. ExerciseDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    ExerciseData(
                        entity.exerciseComplete,
                        entity.exerciseTiming
                    )
                }.also { mappedList ->
                    exercises.clear()
                    exercises.addAll(mappedList)
                    exerciseAdapter.notifyItemRangeInserted(0, exercises.size)
                }

            }
        }

        if(exerciseMain != null) {
            lifecycleScope.launch(Dispatchers.IO) {
                (activity?.application as ExerciseApplication).db.ExerciseDao().insert(
                    ExerciseSql (
                        exerciseComplete = exerciseMain.exerciseName,
                        exerciseTiming = exerciseMain.exerciseTime
                    )
                )
            }
            activity?.intent?.removeExtra("Exercise!")
        }

    }// Gets the function

    companion object {
        fun newInstance(): ExerciseListFragment {
            return ExerciseListFragment()
        }
    }
}