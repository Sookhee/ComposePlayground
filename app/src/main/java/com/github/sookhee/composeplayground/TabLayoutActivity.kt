package com.github.sookhee.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.TextStyle
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

@OptIn(ExperimentalPagerApi::class)
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
                                .background(color = Temp)
                        ) { page ->
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

@OptIn(ExperimentalPagerApi::class)
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

@OptIn(ExperimentalPagerApi::class)
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
