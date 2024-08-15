package com.example.peer_pulse.presentation.college_page

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.peer_pulse.R
import com.example.peer_pulse.domain.model.College
import com.example.peer_pulse.domain.model.colleges
import com.example.peer_pulse.presentation.preferences.PageHeader
//import com.example.peer_pulse.presentation.preferences.PostLazyColumn

@Composable
fun CollegePage(
    //navController: NavController,
    collegeName : String
){
    Scaffold(

    ) {
        var selectedScreen by remember {
            mutableStateOf("Ratings")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
           CollegePageHeader(
               collegeName = collegeName,
              // navController = navController
           )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { 
                        selectedScreen = "Overview"
                    },
                    modifier = Modifier.weight(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = if(selectedScreen == "Overview") Color.Black else Color.Gray,
                        containerColor = Color.Transparent
                    ),
                    shape = RectangleShape
                ) {
                    Column {
                        Text(
                            text = "Overview",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 8.dp),
                            fontWeight = FontWeight.Black
                        )
                        HorizontalDivider(
                            thickness = 4.dp,
                            color = if(selectedScreen == "Overview") Color.Gray else Color.Transparent
                        )
                    }
                }
                TextButton(
                    onClick = {
                        selectedScreen = "Ratings"
                    },
                    modifier = Modifier.weight(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = if(selectedScreen == "Ratings") Color.Black else Color.Gray,
                        containerColor = Color.Transparent
                    ),
                    shape = RectangleShape
                ) {
                    Column {
                        Text(
                            text = "Ratings",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 8.dp),
                            fontWeight = FontWeight.Bold
                        )
                        HorizontalDivider(
                            thickness = 4.dp,
                            color = if(selectedScreen == "Ratings") Color.Gray else Color.Transparent
                        )
                    }

                }
            }
            if(selectedScreen == "Overview"){
                CollegeOverview(
                    collegeName = collegeName
                )
            }
            else if (selectedScreen == "Ratings"){
                CollegeRatings()
            }
        }
    }
}

@Composable
fun CollegeRatings(

) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Ratings",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ){
            Text(
                text = "4.0",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            RatingBar(
                rating = 4,
            )
        }
        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape
        ) {
            Text(text = "Write a Review")
        }
        Spacer(modifier = Modifier.height(4.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Column {
            for( i in 1..5) {
                RatingCard()
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun RatingCard(

) {
    Column (
        modifier = Modifier.padding(8.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ){
            Text(
                text = "4.0",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            RatingBar(
                rating = 4,
            )
        }
        Text(
            text = "Good College",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "This college is an awesome place with great vibes and tons of cool activities. The professors are chill and really care, making it a fun and rewarding experience. Plus, the campus community feels like one big family!",
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Composable
fun CollegeOverview(
    collegeName: String
) {
    val overview  = colleges.find { it.name == collegeName }?.description ?: "No overview available"
    Column (
        modifier = Modifier.padding(8.dp)
    ){
        Text(
            text = "Overview",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = overview,
            fontSize = 14.sp,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.SemiBold
        )
    }

}


@Composable
fun CollegePageHeader(
    collegeName: String,
    //navController: NavController
){
    val logo = colleges.find { it.code == collegeName }?.logo ?: R.drawable.google_image
    val background = colleges.find { it.code == collegeName }?.background ?: R.drawable.ic_launcher_background
    val name = colleges.find { it.code == collegeName }?.name ?: "Not Available"
    Box(modifier = Modifier.height(200.dp)) {
        Image(
            painter = painterResource(id = background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .align(Alignment.TopStart),
        ){
            IconButton(
                onClick = {
                    // navController.navigateUp()
                }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription =null )
            }
        }
        Row(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            Image(
                painter = painterResource(id = logo),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )
        }
    }
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = name,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun RatingBar(
    rating: Int,
    maxRating: Int = 5,
    modifier: Modifier = Modifier,
    filledStar: Int = R.drawable.ic_star_filled,
    starColor: Color = Color.Yellow
) {
    Row(modifier = modifier) {
        for (i in 1..maxRating) {
            val icon = filledStar
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = if (i <= rating) starColor else Color.Gray,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewCollege(){
    CollegePage(collegeName = "1DA" )
}