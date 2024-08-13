package com.sudheer.aware

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudheer.aware.screens.AutismList
import com.sudheer.aware.ui.theme.Primary

class DetailActivity: AppCompatActivity() {

    val localContext = LocalContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
                Surface(color = MaterialTheme.colors.background) {
                    MovieDetail(movie = intent.getParcelableExtra("autism_data")!!)
            }
        }
    }
}

@Composable
fun ChoiceQuantity() {

}

@Composable
fun MovieDetail(movie: Autism) {
//detail
    val context = LocalContext.current

    LazyColumn(content = {
        item {
            Spacer(Modifier.size(30.dp))
            Image(
                painterResource(id = movie.poster),
                modifier = Modifier
                    .width(400.dp)
                    .height(100.dp),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center)
            Spacer(Modifier.size(20.dp))
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(start = 10.dp),
                style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold),
                text = movie.name
            )
           //text
            Spacer(Modifier.size(10.dp))
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(start = 10.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                text = "Description: ")

            Spacer(Modifier.size(10.dp))
            Spacer(Modifier.size(10.dp))
            val aware = ""
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .heightIn(48.dp),
                onClick = {
                    context.startActivity(
                        Intent(context, AddtoCartActivity::class.java)
                            .putExtra("aware_data", aware))
                },
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                shape = RoundedCornerShape(50.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(48.dp)
                        .background(
                            brush = Brush.horizontalGradient(listOf(Primary, Primary)),
                            shape = RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Get Subscription",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold)
                }





            }
        }
    })



}

@Composable
fun GeneralText(textToShow: String) {
    Text(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        style = TextStyle(fontSize = 16.sp),
        text = textToShow
    )
}

@Composable
fun CartText(textToShow: String) {
    Text(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        style = TextStyle(fontSize = 16.sp),
        text = textToShow
    )
}


