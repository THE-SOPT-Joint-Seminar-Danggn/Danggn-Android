package org.sopt.seminar.presentation.read

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.sopt.seminar.R
import org.sopt.seminar.databinding.ActivityReadBinding

class ReadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadBinding

    private val arrTextViewId = arrayListOf(R.id.tv_title, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stateBottomSheetView = layoutInflater.inflate(R.layout.read_bottom_sheet, null)
        val stateBottomSheetDialog = BottomSheetDialog(this, R.style.DialogCustomTheme) // dialog에 sytle 추가
        val stateArray = resources.getStringArray(R.array.read_state_bottom_sheet_array)

        stateBottomSheetDialog.setContentView(stateBottomSheetView)
        setBottomSheetView(stateBottomSheetView, stateArray, stateBottomSheetDialog, binding.tvState)
        binding.clState.setOnClickListener {
            stateBottomSheetDialog.show()
        }
    }

    private fun setBottomSheetView(bottomSheetView: View, arr: Array<String>, dialog: BottomSheetDialog, spinner: TextView) {
        for (i in arr.indices) {
            val textView = bottomSheetView.findViewById<TextView>(arrTextViewId[i])
            textView.text = arr[i]
            textView.visibility = View.VISIBLE
            textView.setOnClickListener {
                spinner.text = arr[i]
                dialog.dismiss()
            }
        }
    }

}