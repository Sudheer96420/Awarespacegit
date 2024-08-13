package com.sudheer.aware.screens

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sudheer.aware.AddtoCartActivity
import com.sudheer.aware.Autism
import com.sudheer.aware.DetailActivity
import com.sudheer.aware.FunfruitActivity
import com.sudheer.aware.LetterAndWordActivity
import com.sudheer.aware.ProfileActivity
import com.sudheer.aware.R
import com.sudheer.aware.components.AppToolbar
import com.sudheer.aware.components.NavigationDrawerBody
import com.sudheer.aware.components.NavigationDrawerHeader
import com.sudheer.aware.data.home.HomeViewModel
import com.sudheer.aware.getActionList
import com.sudheer.aware.getCommonList
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val gradient45 = Brush.linearGradient(
        colors = listOf(Color.White, Color.Green),
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val localContext = LocalContext.current
    val CELL_COUNT = 2
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(CELL_COUNT) }


    homeViewModel.getUserData()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppToolbar(toolbarTitle = stringResource(id = R.string.home),
                logoutButtonClicked = {
                    homeViewModel.logout()
                },
                navigationIconClicked = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            NavigationDrawerHeader(homeViewModel.emailId.value)
            NavigationDrawerBody(navigationDrawerItems = homeViewModel.navigationItemsList,
                onNavigationItemClicked = {
                    Log.d("ComingHere","inside_NavigationItemClicked")
                    Log.d("ComingHere","${it.itemId} ${it.title}")
                })
        }

    ) { paddingValues ->
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(paddingValues)
        Surface(
            modifier = Modifier
                .background(gradient45)
                .fillMaxSize()
                .padding(paddingValues)
        ) {




            Column(
                modifier = Modifier.fillMaxSize(

                )
                    .background(gradient45),
                verticalArrangement = Arrangement.Center,

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val context = LocalContext.current

//                Spacer(modifier = Modifier.height(50.dp))
                Spacer(modifier = Modifier.height(16.dp)) // Optional spacer for spacing below the image

                Image(
                    painterResource(id = R.drawable.a1),
                    modifier = Modifier
                        .clickable {
                            context.startActivity(
                                Intent(context, LetterAndWordActivity::class.java))
                        }
                        .width(990.dp)
                        .height(190.dp),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()

                        .padding(start = 10.dp),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    text = "Letters and Words"
                )
                Spacer(Modifier.size(40.dp))

                Image(
                    painterResource(id = R.drawable.a2),
                    modifier = Modifier
                        .clickable {
                            context.startActivity(
                                Intent(context, FunfruitActivity::class.java))
                        }
                        .width(990.dp)
                        .height(190.dp),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(start = 10.dp),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    text = "Fruits Count"
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .clickable {
                            context.startActivity(
                                Intent(context, AddtoCartActivity::class.java))
                        }
                        .padding(start = 10.dp),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    text = "Get Subscription"
                )





//                GenreTitle(genreTitle = "Select Types")
                //imageprof
                Image(
                    painter = painterResource(id = R.drawable.prof),
                    contentDescription = "Profile Info",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(90.dp)
                        .height(100.dp)

                        .offset(x = 140.dp, y = (-10).dp)
                        .clickable {
                            context.startActivity(
                                Intent(context, ProfileActivity::class.java)
                            )
                        }
                        .padding(start = 10.dp)
                )




                // Add more content here if needed









            }



        }
    }
}

@Composable
fun GenreTitle(genreTitle: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp),
        style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold),
        text = genreTitle
    )
}

@Composable
fun AutismList(autismList: List<Autism>, onItemClick: (movie: Autism) -> Unit) {
    LazyRow(content = {
        items(autismList) { autism ->
            MovieItemView(aware = autism) {
                onItemClick(autism)
            }
        }
    })
}

@Composable
fun MovieItemView(aware: Autism, onClick: (movie: Autism) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                onClick = { onClick(aware) }
            )
            .width(100.dp)
            .wrapContentHeight()) {
        Image(
            painterResource(id = aware.poster),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(5.dp),
            contentDescription = ""
        )
        Text(
            text = aware.name,
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}