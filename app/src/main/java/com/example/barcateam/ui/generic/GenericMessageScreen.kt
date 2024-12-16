package com.example.barcateam.ui.generic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GenericMessageScreen(
    imageResId: Int,
    title: String,
    description: String? = null,
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Error Image
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Error Image",
            modifier = Modifier
                .size(128.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        // Error Title
        Text(
            text = title,
            fontSize = 20.sp,
            color = Color.Red,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Error Description
        if (!description.isNullOrEmpty()) {
            Text(
                text = description,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        onRetry?.let {
            // Retry Button
            Button(onClick = { it.invoke() }) {
                Text(text = "Retry")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenericMessageScreenPreview() {
    GenericMessageScreen(
        imageResId = android.R.drawable.ic_dialog_alert, // Replace with your drawable resource
        title = "Oops! Something Went Wrong",
        description = "We encountered an unexpected error. Please try again.",
        onRetry = { /* Define retry action */ }
    )
}