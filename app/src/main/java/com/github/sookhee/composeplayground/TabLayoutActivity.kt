package com.github.sookhee.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.sookhee.composeplayground.ui.theme.ComposePlaygroundTheme
import com.github.sookhee.composeplayground.ui.theme.DarkBlack
import com.github.sookhee.composeplayground.ui.theme.Temp
import com.github.sookhee.composeplayground.ui.theme.Yellow
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

/**
 *  TabLayoutActivity.kt
 *
 *  Created by Minji Jeong on 2022/06/13
 *  Copyright © 2022 MashUp All rights reserved.
 */

@ExperimentalPagerApi
class TabLayoutActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val tabItems = listOf("내가 쓴 글", "알림설정")
            val pagerState = rememberPagerState()

            ComposePlaygroundTheme {
                Surface(color = Color.Black) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        // TabLayout
                        TabLayout(tabItems, pagerState)

                        // HorizontalPager
                        HorizontalPager(
                            count = tabItems.size,
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.Black)
                        ) { page ->

                            if (page == 0) {
                                LazyColumn {
                                    items(10) {
                                        MyPost()
                                    }
                                }
                            } else {
                                Text(
                                    text = tabItems[page],
                                    modifier = Modifier.padding(50.dp),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabLayout(tabItems: List<String>, pagerState: PagerState) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Black,
        modifier = Modifier
            .padding(top = 100.dp, bottom = 40.dp)
            .background(color = Color.Transparent)
            .width(200.dp)
            .height(36.dp)
            .clip(RoundedCornerShape(30.dp)),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .pagerTabIndicatorOffset(pagerState, tabPositions)
                    .width(0.dp)
                    .height(0.dp)
            )
        }
    ) {
        tabItems.forEachIndexed { index, tab ->
            Tab(index, tab, pagerState)
        }
    }
}

@ExperimentalPagerApi
@Composable
fun Tab(index: Int, tab: String, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    val color = remember { Animatable(Yellow) }

    LaunchedEffect(pagerState.currentPage == index) {
        color.animateTo(if (pagerState.currentPage == index) Color.Yellow else Color.Black)
    }

    Tab(
        text = {
            Text(
                text = tab,
                style = if (pagerState.currentPage == index) TextStyle(
                    color = DarkBlack,
                    fontSize = 16.sp
                ) else TextStyle(color = Color.White, fontSize = 16.sp)
            )
        },
        selected = pagerState.currentPage == index,
        modifier = Modifier
            .background(
                color = color.value,
                shape = RoundedCornerShape(30.dp)
            ),
        onClick = {
            coroutineScope.launch {
                pagerState.scrollToPage(index)
            }
        }
    )
}

@Composable
fun MyPost(isChecked: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .background(color = Temp)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.End
    ) {
        if (!isChecked) {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .height(8.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Yellow)
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Image(
                painter = painterResource(R.drawable.rectangle_34),
                contentDescription = "",
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .padding(end = 16.dp)
            )

            Column {
                Row {
                    Text(text = "강남구 00동", color = Color.White, fontSize = 20.sp)
                    Text(text = " - ", color = Color.White, fontSize = 20.sp)
                    Text(text = "5분 전", color = Color.White, fontSize = 20.sp)
                }

                Text(text = "말 많은 11번째 코알라", color = Color.White, fontSize = 12.sp)
            }
        }

    }
}

@Composable
@Preview(
    device = Devices.DEFAULT
)
fun PreviewMyPost() {
    ComposePlaygroundTheme {
        MyPost()
    }
}
