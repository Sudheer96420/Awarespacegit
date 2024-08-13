package com.sudheer.aware

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sudheer.aware.components.HeadingTextComponent
import com.sudheer.aware.ui.theme.AutismAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.min

class ProfileActivity : ComponentActivity() {

    enum class CameraPermissionStatus { NoPermission, PermissionGranted, PermissionDenied }

    @OptIn(ExperimentalMaterial3Api::class)
    //profile

    private fun getImageRotation(resolver: ContentResolver, uri: Uri): Int {
        val cursor = resolver.query(
            uri, arrayOf(MediaStore.Images.Media.ORIENTATION), null,
            null, null
        )
        var result = 0

        cursor?.apply {
            moveToFirst()
            val index = getColumnIndex(MediaStore.Images.Media.ORIENTATION)
            result = getInt(index)
            close()
        }
        println("Rotation = $result")
        return result
    }
}

@Composable
fun TakePhotoButton(
    cameraPermissionStatus: ProfileActivity.CameraPermissionStatus,
    requestPermissionLauncher: ActivityResultLauncher<String>,
    takePhoto: () -> Unit
) {
    OutlinedButton(
        onClick = {
            when (cameraPermissionStatus) {
                ProfileActivity.CameraPermissionStatus.NoPermission ->
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)

                ProfileActivity.CameraPermissionStatus.PermissionGranted ->
                    takePhoto()

                ProfileActivity.CameraPermissionStatus.PermissionDenied -> {}
            }
        }
    ) {
        when (cameraPermissionStatus) {
            ProfileActivity.CameraPermissionStatus.NoPermission ->
                Text(text = "Request Camera Permissions")

            ProfileActivity.CameraPermissionStatus.PermissionDenied ->
                Text(text = "Camera Permissions Have Been Denied")

            ProfileActivity.CameraPermissionStatus.PermissionGranted ->
                Text(text = "Take a Photo")
        }
    }
}

@Composable
fun SaveButton(onSave: () -> Unit) {
    Button(
        onClick = { onSave() },
        modifier = Modifier.padding(8.dp)
    ) {
        Text("Save")
    }
}

@Composable
fun EditButton(onEdit: () -> Unit) {
    Button(
        onClick = { onEdit() },
        modifier = Modifier.padding(8.dp)
    ) {
        Text("Edit")
    }
}

@Composable
fun BackButton(onBack: () -> Unit) {
    Button(
        onClick = { onBack() },
        modifier = Modifier.padding(8.dp)
    ) {
        Text("Back")
    }
}
