package com.linguaceleris.designsystem.widgets

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

// TODO: вернуть при добавлении лендскейпа
// @Preview(
//    showBackground = true,
//    device = LANDSCAPE_DEVICE,
// )
// @Preview(
//    showBackground = true,
//    device = LANDSCAPE_DEVICE,
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
// )

@Preview(
    name = "Small",
    showBackground = true,
    device = "id:Galaxy Nexus",
)
@Preview(
    name = "Medium",
    showBackground = true,
)
@Preview(
    name = "Medium dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
annotation class ScreenPreviews
