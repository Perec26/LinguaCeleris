package com.linguaceleris.designsystem.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun RowWithMoreElement(
    modifier: Modifier = Modifier,
    moreElement: @Composable () -> Unit,
    divider: @Composable () -> Unit = { VerticalDivider(modifier = Modifier.width(1.dp)) },
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

        var sum = morePlaceables.width
        val dividerWidth = dividerPlaceable.first().width
        var showMore = false

        var count = 0

        for (element in elementsPlaceables) {
            if (sum + element.width + dividerWidth > constraints.maxWidth) {
                showMore = true
                onElementsCounted(count)
                break
            }
            sum += element.width + dividerWidth
            count += 1
        }
        if (!showMore) sum -= morePlaceables.width

        var offset = 0
        layout(
            height = elementsPlaceables.maxOf(Placeable::height),
            width = sum,
        ) {
            for (i in 0 until count) {
                elementsPlaceables[i].place(offset, 0)
                offset += elementsPlaceables[i].width
                if (i != count - 1) {
                    dividerPlaceable[i].place(offset, 0)
                    offset += dividerWidth
                }
            }
            if (showMore) {
                dividerPlaceable[count - 1].place(offset, 0)
                offset += dividerWidth
                morePlaceables.place(offset, 0)
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun RowWithMoreElementPreview() {
    LCPreview {
        Box(modifier = Modifier.fillMaxSize()) {
            RowWithMoreElement(
                elements = { (1..8).forEach { GetPreviewElement(it) } },
                moreElement = { Text(text = "More") },
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
            .fillMaxHeight()
            .width(50.dp)
            .background(color = backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "${index * 50}",
            color = textColor,
        )
    }
}
