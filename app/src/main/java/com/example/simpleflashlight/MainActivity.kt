package com.example.simpleflashlight

import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simpleflashlight.databinding.ActivityMainBinding

/**
 * Main activity
 *
 * @author Karim.2501
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraManager: CameraManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
    }

    override fun onResume() {
        super.onResume()
        checkFlashlight()
        onOffFlashlight()
    }

    /**
     * Check flashlight
     *
     */
    fun checkFlashlight(){
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                binding.swFlash.isEnabled = true
            }else{
                Toast.makeText(this, getString(R.string.thisNoFlash), Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, getString(R.string.thisNoCamera), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * On off flashlight
     *
     */
    fun onOffFlashlight(){
        binding.swFlash.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode("0", true)
                    binding.tvFlash.text = getString(R.string.on)
                }
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode("0", false)
                    binding.tvFlash.text = getString(R.string.off)
                }
            }
        }
    }
}