package com.example.psiappshelter.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.psiappshelter.R
import com.example.psiappshelter.ui.theme.GradientPurple
import com.example.psiappshelter.ui.theme.PurpleGrey80


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoggoDetailsScreen(
    navController: NavController,
    doggoId: String?,
    doggoViewModel: DoggoViewModel
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val doggo = doggoViewModel.doggoList.find { it.id == doggoId }


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = (PurpleGrey80.copy(alpha = 0.15f)),
                ),
                title = {
                    Text("Detale")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()}
                    ){
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Cofnij"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (doggo != null) {
                                doggoViewModel.removeDoggo(doggo)
                                navController.popBackStack()
                            }
                        }
                    ){
                        Icon(
                            painter = painterResource(
                                id = R.drawable.delete_icon
                            ),
                            contentDescription = "Favorite",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(60.dp))
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colorStops = GradientPurple
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "\uD83D\uDC15",
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.size(40.dp))
            if (doggo != null) {
                Text(
                    text = doggo.name,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            if (doggo != null) {
                Text(
                    text = "${doggo.breed}, ${doggo.age} years old",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoggoDetailsScreenPreview() {
    val navController = rememberNavController()

    val doggoViewModel = DoggoViewModel().apply{}

    val exampleDoggoId = doggoViewModel.doggoList.first().id

    MaterialTheme {
        DoggoDetailsScreen(
            navController = navController,
            doggoId = exampleDoggoId,
            doggoViewModel = doggoViewModel
        )
    }
}

