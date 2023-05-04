package com.github.sookhee.composeplayground

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.sookhee.composeplayground.ui.theme.ComposePlaygroundTheme
import com.github.sookhee.composeplayground.ui.theme.Yellow
import com.google.accompanist.pager.ExperimentalPagerApi

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current

            ComposePlaygroundTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Column {
                        ButtonWithFunction("TabLayout") {
                            val intent = Intent(context, TabLayoutActivity::class.java)
                            context.startActivity(intent)
                        }

                        ButtonWithFunction("CustomButton") {
                            val intent = Intent(context, ButtonActivity::class.java)
                            context.startActivity(intent)
                        }

                        ButtonWithFunction("Random Activity") {
                            val intent = Intent(context, RandomActivity::class.java)
                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonWithFunction(buttonText: String, moveTo: () -> Unit) {
    Button(
        onClick = moveTo,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Black,
            contentColor = Yellow
        )
    ) {
        Text(buttonText)
    }
}

@Preview(
    showBackground = true,
    device = Devices.DEFAULT
)

@Composable
fun DefaultPreview() {
    ComposePlaygroundTheme {
        ButtonWithFunction("Android") {}
    }
}
