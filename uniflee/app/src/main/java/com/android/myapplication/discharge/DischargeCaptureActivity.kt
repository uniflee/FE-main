package com.android.myapplication.discharge

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.android.myapplication.App
import com.android.myapplication.api.RetrofitClient
import com.android.myapplication.databinding.ActivityDischargeCaptureBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

private const val CAMERA_PERMISSION_CODE = 1001
class DischargeCaptureActivity : AppCompatActivity() {
    private val binding: ActivityDischargeCaptureBinding by lazy {
        ActivityDischargeCaptureBinding.inflate(layoutInflater)
    }
    private lateinit var previewView: PreviewView
    private lateinit var imageCapture: ImageCapture

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        previewView = binding.cameraPreview
        val btnClose = binding.close
        val btnConfirm = binding.btnCapture

        checkCameraPermission()
        startCamera()

        btnClose.setOnClickListener {
            finish()
        }

        btnConfirm.setOnClickListener {
            binding.cameraText1.text = "인식 중입니다. 완료 시까지"
            binding.cameraText2.text = "화면을 터치하지 마세요"
            captureAndSendPhoto()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.e("CameraX", "카메라 시작 실패", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        } else {
            startCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            }
        }
    }

    private fun captureAndSendPhoto() {
        val photoFile = File(externalMediaDirs.firstOrNull(), "photo-${System.currentTimeMillis()}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Log.d("File", "File saved at: ${photoFile.absolutePath}")
                    if (photoFile.exists() && photoFile.length() > 0) {
                        Log.e("File Size", getFileSize(photoFile.absolutePath))
                        sendPhotoToServer(photoFile)
                    } else {
                        Log.e("File Error", "File does not exist or is empty.")
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("CameraX", "사진 촬영 실패", exception)
                }
            }
        )
    }

    private fun sendPhotoToServer(photoFile: File) {
        // api 연결
        val apiService = RetrofitClient.apiservice

        // token 가져오기
        val globalToken: String = App.prefs.getItem("token", "no Token")

        // user정보 가져오기
        val token = "Bearer ${globalToken.replace("\"", "")}"

        val requestBody = photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", photoFile.name, requestBody)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.checkPhoto(token, body)
                if (response.isSuccessful) {
                    Log.d("API Response", "Upload successful: ${response.body()}")
                    var realPre = ""
                    when(response.body()?.predict){
                        "철캔" -> realPre = "IRON_CAN"
                        "알루미늄캔" -> realPre = "ALUMINUM_CAN"
                        "종이" -> realPre = "PAPER"
                        "무색 단일" -> realPre = "COLORLESS_PET"
                        "유색 단일" -> realPre = "COLORED_PET"
                        "PE" -> realPre ="PE"
                        "PP" -> realPre ="PP"
                        "PS" -> realPre ="PS"
                        "스티로폼" -> realPre = "STYROFOAM"
                        "비닐" -> realPre = "VINYL"
                        "갈색 유리병" -> realPre = "BROWN_GLASS"
                        "녹색 유리병" -> realPre = "GREEN_GLASS"
                        "투명 유리병" -> realPre = "CLEAR_GLASS"
                    }
                    response.body()?.let { intentGuide(realPre) }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.cameraText1.text = "인식에 실패했습니다."
                        binding.cameraText2.text = "다시 시도하세요"
                    }
                    Log.e(
                        "API Response",
                        "Upload failed: ${response.code()} ${response.errorBody()?.string()}"

                    )
                }
            } catch (e: Exception) {
                Log.e("Error", "Network request failed: ${e.message}")
            }
        }
    }
    // 파일 크기 확인
    fun getFileSize(filePath: String): String {
        val file = File(filePath)
        if (!file.exists()) {
            return "File does not exist."
        }
        val fileSizeInBytes = file.length()
        val fileSizeInKB = fileSizeInBytes / 1024
        val fileSizeInMB = fileSizeInKB / 1024

        return "File Size: $fileSizeInBytes bytes ($fileSizeInKB KB, $fileSizeInMB MB)"
    }

    fun intentGuide(predict : String){
        val intent = Intent(this, DischargeGuideActivity::class.java)
        intent.putExtra("predict", predict)
        startActivity(intent)
    }

}
