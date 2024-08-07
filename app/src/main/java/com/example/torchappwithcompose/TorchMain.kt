package com.example.torchappwithcompose

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.CameraManager
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.torchappwithcompose.ui.theme.greenColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TorchMain() {

    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                MaterialTheme.colorScheme.primary
            ),
                title = {
                    Text(
                        text = "Torch App Jetpack Compose",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                })
        }) {

        torchApplication(LocalContext.current)
    }
}

@Composable
fun torchApplication(context: Context) {
    val torchStatus = remember {
        mutableStateOf(false)
    }
    val torchMsg = remember {
        mutableStateOf("Off")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Torch is " + torchMsg.value,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            fontSize = 20.sp, modifier = Modifier.padding(5.dp)
        )

        Switch(checked = torchStatus.value, onCheckedChange = {
            torchStatus.value = it

            lateinit var cameraManager: CameraManager
            lateinit var cameraID: String

            cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            try {
                // O means back camera unit,
                // 1 means front camera unit
                cameraID = cameraManager.cameraIdList[0]
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (torchStatus.value) {
                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

                        cameraManager.setTorchMode(cameraID, true)

                        Toast.makeText(context, "Torch turned on..", Toast.LENGTH_LONG).show()

                        torchMsg.value = "On"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        cameraManager.setTorchMode(cameraID, false)

                        Toast.makeText(context, "Torch turned off..", Toast.LENGTH_SHORT).show()

                        torchMsg.value = "Off"
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }
}

@Preview(showSystemUi = true)
@Composable
fun previewTorch() {

    TorchMain()
}
