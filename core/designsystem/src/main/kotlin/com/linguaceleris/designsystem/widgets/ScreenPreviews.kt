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
@Preview(
    name = "Medium big Font",
    showBackground = true,
    fontScale = 2f,
)
@Preview(
    name = "Small big Font",
    showBackground = true,
    device = "id:Galaxy Nexus",
    fontScale = 2f,
)
annotation class ScreenPreviews
