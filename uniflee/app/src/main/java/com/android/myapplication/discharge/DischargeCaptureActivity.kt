package com.android.myapplication.discharge

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.camera.lifecycle.ProcessCameraProvider
import com.android.myapplication.databinding.ActivityDischargeCaptureBinding

class DischargeCaptureActivity : AppCompatActivity() {
    private val binding: ActivityDischargeCaptureBinding by lazy {
        ActivityDischargeCaptureBinding.inflate(layoutInflater)
    }
    private lateinit var previewView: PreviewView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        previewView = binding.cameraPreview
        val btnClose = binding.close
        val btnConfirm = binding.btnCapture
        // 카메라 초기화
        startCamera()

        // X 버튼 클릭 이벤트 처리
        btnClose.setOnClickListener {
            finish() // 액티비티 종료
        }

        // 버튼 클릭 시 이벤트 처리
        btnConfirm.setOnClickListener {
            // 분리배출 방법 확인 로직 추가
        }
    }
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview
                )
            } catch (e: Exception) {
                Log.e("CameraX", "카메라 시작 실패", e)
            }

        }, ContextCompat.getMainExecutor(this))
    }
}