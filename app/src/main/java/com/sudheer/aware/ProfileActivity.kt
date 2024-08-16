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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cameraPermissionStatusState = mutableStateOf(CameraPermissionStatus.NoPermission)
        val photoUriState: MutableState<Uri?> = mutableStateOf(null)
        val hasPhotoState = mutableStateOf(value = false)
        val resolver = applicationContext.contentResolver

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    cameraPermissionStatusState.value = CameraPermissionStatus.PermissionGranted
                } else {
                    cameraPermissionStatusState.value = CameraPermissionStatus.PermissionDenied
                }
            }

        val takePhotoLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
                hasPhotoState.value = isSaved
            }

        val takePhoto: () -> Unit = {
            hasPhotoState.value = false

            val values = ContentValues().apply {
                val title = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
                put(MediaStore.Images.Media.TITLE, "Compose Camera Example Image - $title")
                put(MediaStore.Images.Media.DISPLAY_NAME, title)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            }

            val uri = resolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
            takePhotoLauncher.launch(uri)
            photoUriState.value = uri
        }

        // Ideally these would be cached instead of reloaded
        val getThumbnail: (Uri?) -> ImageBitmap? = { uri ->
            val targetSize = 256f
            println("URI is $uri")
            uri?.let {
                println("Opening Input Stream")
                resolver.openInputStream(it)
            }?.let {
                BitmapFactory.decodeStream(it)
            }?.let {
                val height = it.height.toFloat()
                val width = it.width.toFloat()
                val scaleFactor = min(targetSize / height, targetSize / width)
                Bitmap.createScaledBitmap(it, (scaleFactor * width).toInt(), (scaleFactor * height).toInt(), true)
            }?.let {
                val rotation = getImageRotation(resolver, uri)
                Bitmap.createBitmap(it, 0, 0, it.width, it.height, Matrix().apply { postRotate(rotation.toFloat()) }, true)
            }?.asImageBitmap()
        }

        val getFullImage: (Uri?) -> ImageBitmap? = { uri ->
            uri?.let {
                resolver.openInputStream(it)
            }?.let {
                BitmapFactory.decodeStream(it)
            }?.let {
                val rotation = getImageRotation(resolver, uri)
                Bitmap.createBitmap(it, 0, 0, it.width, it.height, Matrix().apply { postRotate(rotation.toFloat()) }, true)
            }?.asImageBitmap()
        }

        setContent {
            val cameraPermissionStatus by remember { cameraPermissionStatusState }
            val hasPhoto by remember { hasPhotoState }
            var shouldShowFullImage by remember { mutableStateOf(false) }
            var isEditing by remember { mutableStateOf(false) }

            val nameState = remember { mutableStateOf("Sudheer") }
            val emailState = remember { mutableStateOf("s@gmail.com") }
            val locationState = remember { mutableStateOf("Middlesbrough") }

            AutismAppTheme {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEDEDED))) { // Light background color
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp), // Add padding if needed
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (isEditing) {
                            OutlinedTextField(
                                value = nameState.value,
                                onValueChange = { nameState.value = it },
                                label = { Text("Name") }
                            )
                            OutlinedTextField(
                                value = emailState.value,
                                onValueChange = { emailState.value = it },
                                label = { Text("Email") }
                            )
                            OutlinedTextField(
                                value = locationState.value,
                                onValueChange = { locationState.value = it },
                                label = { Text("Location") }
                            )
                        } else {
                            HeadingTextComponent(value = "Name: ${nameState.value}")
                            HeadingTextComponent(value = "Email: ${emailState.value}")
                            HeadingTextComponent(value = "Location: ${locationState.value}")
                        }

                        TakePhotoButton(
                            cameraPermissionStatus = cameraPermissionStatus,
                            requestPermissionLauncher = requestPermissionLauncher,
                            takePhoto = takePhoto
                        )
                        if (hasPhoto) {
                            val bitmap = getThumbnail(photoUriState.value)
                            println("Is bitmap null: $bitmap")
                            if (bitmap != null) {
                                Image(
                                    bitmap = bitmap,
                                    contentDescription = "Thumbnail of Saved Photo",
                                    modifier = Modifier.clickable {
                                        shouldShowFullImage = true
                                    }
                                )
                            }
                        }

                        SaveButton {
                            // Implement save functionality here
                            isEditing = false
                        }

                        EditButton {
                            // Implement edit functionality here
                            isEditing = true
                        }

                        BackButton {
                            finish() // This will close the activity and go back to the previous screen
                        }
                    }

                    if (shouldShowFullImage && hasPhoto) {
                        val bitmap = getFullImage(photoUriState.value)
                        if (bitmap != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        shouldShowFullImage = false
                                    }
                            ) {
                                Image(
                                    bitmap = bitmap,
                                    contentDescription = "Full image of Saved Photo",
                                    modifier = Modifier.align(Alignment.Center)
                                )
                                Surface(
                                    modifier = Modifier
                                        .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
                                        .align(Alignment.Center)
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = "Click to Close",
                                        style = androidx.compose.material3.MaterialTheme.typography.headlineMedium.copy(
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    )
                                }
                            }
                        } else {
                            shouldShowFullImage = false
                        }
                    }
                }
            }
        }
    }


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
