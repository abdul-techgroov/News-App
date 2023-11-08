package com.news.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.news.app.ui.theme.HintGrey

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SearchView(modifier: Modifier = Modifier) {
    TextField(
        value = "", onValueChange = {},
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
        ),
        placeholder = {
            Text(
                text = "Testing",
                style = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = HintGrey
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        shape = RoundedCornerShape(32.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White
        ),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(16.dp)
    )
}
