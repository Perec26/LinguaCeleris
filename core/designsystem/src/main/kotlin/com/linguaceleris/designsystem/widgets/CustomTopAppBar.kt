package com.linguaceleris.designsystem.widgets

import android.content.res.Configuration
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    onNavigationClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(modifier = Modifier.basicMarquee(), text = title, maxLines = 1) },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        navigationIcon = {
            onNavigationClick?.let {
                IconButton(onClick = it) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = "",
                    )
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun CustomTopAppBarPreview() {
    CustomTopAppBarPreviewContent()
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CustomTopAppBarPreviewDark() {
    CustomTopAppBarPreviewContent()
}

@Composable
private fun CustomTopAppBarPreviewContent() {
    LCPreview {
        Column {
            CustomTopAppBar("Title") {}
            SpacerHeight(height = 8.dp)
            CustomTopAppBar("Title")
            SpacerHeight(height = 8.dp)
            CustomTopAppBar(title = "Title") {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_forward),
                        contentDescription = "",
                    )
                }
            }
        }
    }
}
