package com.example.nikestore.feature.settings

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nikestore.R
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.databinding.ActivityCommentListBinding
import com.example.nikestore.databinding.ActivitySettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SettingsActivity : NikeActivity() {
    val viewModel: SettingsViewModel by viewModel()

    lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarView.onBackButtonClickListener= View.OnClickListener{
            finish()
        }

        if(viewModel.isDarkMode){
            binding.darkModeSwitch.isChecked=true
            Timber.i(viewModel.isDarkMode.toString())
        }

        binding.darkModeSwitch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(
                buttonView: CompoundButton?,
                isChecked: Boolean
            ) {
                if(isChecked){
                    viewModel.saveThemeState(true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                else{
                    viewModel.saveThemeState(false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

        })
    }
}