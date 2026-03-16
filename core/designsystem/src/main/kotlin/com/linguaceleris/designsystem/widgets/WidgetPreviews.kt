package com.linguaceleris.designsystem.widgets

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import com.linguaceleris.designsystem.LANDSCAPE_DEVICE

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(device = LANDSCAPE_DEVICE)
@Preview(device = LANDSCAPE_DEVICE, uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class WidgetPreviews
