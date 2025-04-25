package com.example.nikestore.feature.product.comment

import android.os.Bundle
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import com.example.nikestore.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddCommentBottomSheetDialog: BottomSheetDialogFragment() {
    var eventListener: AddCommentDialogEventListener?=null
    var titleEt:TextInputEditText?=null
    var titleEtl: TextInputLayout?=null
    var contentEt:TextInputEditText?=null
    var contentEtl: TextInputLayout?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= LayoutInflater.from(context).inflate(R.layout.layout_add_comment,container,false)
        titleEt= view.findViewById(R.id.commentTitleEt)
        titleEtl= view.findViewById(R.id.commentTitleEtl)
        contentEt= view.findViewById(R.id.commentContentEt)
        contentEtl= view.findViewById(R.id.commentContentEtl)

        titleEt!!.doOnTextChanged { text, start, before, count ->
            if(text!!.isNotEmpty()){
                titleEtl!!.error=null
            }
        }
        contentEt!!.doOnTextChanged { text, start, before, count ->
            if(text!!.isNotEmpty()){
                contentEtl!!.error=null
            }
        }
        view.findViewById<View>(R.id.saveCommentBtn).setOnClickListener {
            if(checkAllFields()){
                eventListener?.onSaveButtonClicked(view.findViewById<TextInputEditText>(R.id.commentTitleEt).text.toString(),view.findViewById<TextInputEditText>(R.id.commentContentEt).text.toString())
                dismiss()
            }
        }
        return view
    }

    interface AddCommentDialogEventListener{
        fun onSaveButtonClicked(title: String,content:String)
    }

    private fun checkAllFields(): Boolean{
            if(titleEt!!.length()==0){
                titleEtl!!.error=getString(R.string.commentTitleEmptyError)
                return false
            }else if(contentEt!!.length()==0){
                contentEtl!!.error=getString(R.string.commentContentEmptyError)
                return false
            }
            return true
    }
}