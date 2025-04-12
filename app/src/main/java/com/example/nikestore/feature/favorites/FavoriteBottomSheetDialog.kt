package com.example.nikestore.feature.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikestore.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class FavoriteBottomSheetDialog: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=LayoutInflater.from(context).inflate(R.layout.layout_favorites_hint,container,false)
        view.findViewById<MaterialButton>(R.id.acceptBtn).setOnClickListener {
            dismiss()
        }
        return view
    }
}