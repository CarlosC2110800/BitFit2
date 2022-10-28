package com.example.bitfit2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

private const val TAG = "OverviewFragment"

class OverviewFragment : Fragment() {
    private lateinit var maxTime: TextView
    private lateinit var minTime: TextView
    private lateinit var avgTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getNewTimes()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_overview, container, false)
        maxTime = view.findViewById(R.id.maximumTV)
        minTime = view.findViewById(R.id.minimumTV)
        avgTime = view.findViewById(R.id.averageTV)

        // Inflate the layout for this fragment
        return view
    }

    private fun getNewTimes() {
        lifecycleScope.launch {
            (activity?.application as ExerciseApplication).db.ExerciseDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    ExerciseData(
                        entity.exerciseComplete,
                        entity.exerciseTiming
                    )
                }.also { mappedList ->
                    val sizeOfList = mappedList.size
                    var total = 0.0
                    var max = mappedList[0].exerciseTime as Double
                    var min = mappedList[0].exerciseTime as Double
                    for (item in mappedList) {
                        total += item.exerciseTime as Double
                        if (max < item.exerciseTime) {
                            max =  item.exerciseTime
                        }
                        if (min > item.exerciseTime) {
                            min =  item.exerciseTime
                        }
                }
                    val average = total / sizeOfList

                    avgTime.text = roundOffDecimal(average).toString()
                    maxTime.text = max.toString()
                    minTime.text = min.toString()

                }

            }
        }
    }
    private fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }

    companion object {
        fun newInstance(): OverviewFragment {
            return OverviewFragment()
        }
    }
}