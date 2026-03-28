package com.linguaceleris.designsystem.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun ColumnWithMoreElement(
    modifier: Modifier = Modifier,
    moreElement: @Composable () -> Unit,
    divider: @Composable () -> Unit = { DefaultHorizontalDivider() },
    onElementsCounted: (Int) -> Unit = {},
    elements: @Composable () -> Unit,
) {
    val dividers = @Composable {
        repeat(50) {
            divider()
        }
    }

    Layout(
        contents = listOf(elements, moreElement, dividers),
        modifier = modifier,
    ) { (elementsMeasurables, moreMeasurables, dividerMeasurables), constraints ->

        val elementsPlaceables = elementsMeasurables.map { it.measure(constraints) }
        val morePlaceables = moreMeasurables.first().measure(constraints)
        val dividerPlaceable = dividerMeasurables
            .take(elementsPlaceables.size)
            .map { it.measure(constraints) }

        var sum = morePlaceables.height
        val dividerHeight = dividerPlaceable.first().height
        var showMore = false

        var count = 0

        for (element in elementsPlaceables) {
            if (sum + element.height + dividerHeight > constraints.maxHeight) {
                showMore = true
                onElementsCounted(count)
                break
            }
            sum += element.height + dividerHeight
            count += 1
        }
        if (!showMore) sum -= morePlaceables.height

        var offset = 0
        layout(
            height = sum,
            width = elementsPlaceables.maxOf(Placeable::width),
        ) {
            for (i in 0 until count) {
                elementsPlaceables[i].place(0, offset)
                offset += elementsPlaceables[i].height
                if (i != count - 1) {
                    dividerPlaceable[i].place(0, offset)
                    offset += dividerHeight
                }
            }
            if (showMore && count > 0) {
                dividerPlaceable[count - 1].place(0, offset)
                offset += dividerHeight
                morePlaceables.place(0, offset)
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun ColumnWithMoreElementPreview() {
    LCPreview {
        Box(modifier = Modifier.fillMaxSize()) {
            ColumnWithMoreElement(
                elements = { (1..8).forEach { GetPreviewElement(it) } },
                moreElement = { Text(text = "more") },
            )
        }
    }
}

@Composable
private fun GetPreviewElement(
    i: Int,
    firstColor: Color = Color.White,
    secondColor: Color = Color.Black,
) {
    val textColor = if (i % 2 > 0) secondColor else firstColor
    val backgroundColor = if (i % 2 > 0) firstColor else secondColor
    PreviewElement(index = i, textColor = textColor, backgroundColor = backgroundColor)
}

@Composable
private fun PreviewElement(index: Int, textColor: Color, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "${index * 50}",
            color = textColor,
        )
    }
}
