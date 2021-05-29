package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValue: EditText? = null
    private var maxValue: EditText? = null
    private var listener: ActionPerformedListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ActionPerformedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        minValue = view.findViewById(R.id.min_value)
        maxValue = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            val minValueString = minValue?.text.toString()
            val maxValueString = maxValue?.text.toString()
            try {
                if (minValue?.text.isNullOrEmpty() || maxValue?.text.isNullOrEmpty()) {
                    Toast.makeText(context, "set min and max values", Toast.LENGTH_SHORT).show()
                } else if (Integer.parseInt(minValueString) >= Integer.parseInt(maxValueString)) {
                    Toast.makeText(
                        context,
                        "min value is bigger or equal than max value",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    val min = Integer.parseInt(minValue?.text.toString())
                    val max = Integer.parseInt(maxValue?.text.toString())
                    listener?.onActionPerformed(min, max)
                }
            }catch (nfe:NumberFormatException){
                    Toast.makeText(context, "value is bigger than int", Toast.LENGTH_SHORT).show()
                }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

    interface ActionPerformedListener {
        fun onActionPerformed(min: Int, max: Int)
    }
}