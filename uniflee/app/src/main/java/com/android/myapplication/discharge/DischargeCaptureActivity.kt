package com.android.myapplication.discharge

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.camera.lifecycle.ProcessCameraProvider
import com.android.myapplication.databinding.ActivityDischargeCaptureBinding

private const val CAMERA_PERMISSION_CODE = 1001
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
        checkCameraPermission()
        startCamera()

        // X 버튼 클릭 이벤트 처리
        btnClose.setOnClickListener {
            finish() // 액티비티 종료
        }

        // 버튼 클릭 시 이벤트 처리
        btnConfirm.setOnClickListener {
            val intent = Intent(this, DischargeGuideActivity::class.java)
            startActivity(intent)
        }
    }
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // 카메라 Preview 설정
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            // 후면 카메라 선택
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // 카메라 Preview 시작
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview
                )
            } catch (e: Exception) {
                Log.e("CameraX", "카메라 시작 실패", e)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    // 카메라 권한을 요청하는 함수
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        } else {
            // 권한이 이미 부여된 경우 카메라 실행
            startCamera()
        }
    }

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 카메라 권한이 부여됨
                startCamera()
            } else {
                // 카메라 권한이 거부됨
                // 권한이 필요한 이유를 설명하거나, 기능을 제한하는 등의 처리
            }
        }
    }
}