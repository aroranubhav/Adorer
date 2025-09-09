package com.maxi.adorer.presentation.quotes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.maxi.adorer.R
import com.maxi.adorer.common.Constants.SEED_FILE_NAME
import com.maxi.adorer.databinding.ActivityQuotesBinding
import com.maxi.adorer.framework.work.QuotesWorkScheduler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class QuotesActivity : AppCompatActivity() {

    companion object {
        const val MAIN_ACTIVITY_TAG = "MainActivity"
    }

    private lateinit var binding: ActivityQuotesBinding
    private lateinit var viewModel: QuotesViewModel

    @Inject
    lateinit var workManager: WorkManager

    private val requiredPermissions = arrayOf(
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_PHONE_STATE
    )

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val smsGranted = permissions[Manifest.permission.SEND_SMS] ?: false
        val phoneGranted = permissions[Manifest.permission.READ_PHONE_STATE] ?: false

        if (smsGranted && phoneGranted) {
            onPermissionsGranted()
        } else {
            onPermissionsDenied()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        checkAndRequestPermissions()
    }

    private fun checkAndRequestPermissions() {
        if (hasPermissions()) {
            onPermissionsGranted()
        } else {
            requestPermissionLauncher.launch(requiredPermissions)
        }
    }

    private fun hasPermissions(): Boolean {
        return requiredPermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun onPermissionsGranted() {
        Log.d(MAIN_ACTIVITY_TAG, "Permissions granted!")
        setupUi()
    }

    private fun setupUi() {
        binding.apply {
            btnContinue.setOnClickListener {
                val number = edtNumber.text.toString()
                if (viewModel.uiState.value && number.isNotEmpty()) { //TODO: checks for number validation, country selection
                    Log.d(MAIN_ACTIVITY_TAG, "work scheduled!")
                    QuotesWorkScheduler.scheduleWork(
                        workManager,
                        number
                    )
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[QuotesViewModel::class.java]
        viewModel.seedDb(SEED_FILE_NAME)
    }

    private fun onPermissionsDenied() {
        AlertDialog.Builder(this)
            .setTitle("Permissions Required")
            .setMessage("This app requires SMS and phone state permissions to function. Please enable them in app settings.")
            .setPositiveButton("Go to Settings") { dialog, _ ->
                dialog.dismiss()
                openAppSettings()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                Snackbar.make(
                    binding.root,
                    "Permissions denied. Cannot proceed ahead!",
                    Snackbar.LENGTH_LONG
                ).show()
                finish()
            }
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }
}