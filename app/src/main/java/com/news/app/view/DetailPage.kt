package com.news.app.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.news.app.ui.theme.NewsTheme

@Composable
@Preview(showBackground = true)
fun DetailPage(navController: NavHostController) {
    NewsTheme {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (title, ivBack) = createRefs()

            Icon(
                Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(16.dp)
                    .size(32.dp)
                    .constrainAs(ivBack) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Text(text = "Testing",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier
                    .constrainAs(title) {
                        centerHorizontallyTo(parent)
                        centerVerticallyTo(parent)
                    })
        }
    }
}