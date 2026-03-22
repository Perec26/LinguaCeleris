package com.linguaceleris.designsystem.widgets

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Shader
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import com.linguaceleris.designsystem.R
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
import kotlinx.coroutines.isActive

private val patternAngleDeg = Random.nextInt(0, 360).toDouble()
private val moveAngleDeg = Random.nextInt(0, 360).toDouble()
private val speedPxPerSec = Random.nextInt(15, 50).toDouble()
private val tileSizePx = Random.nextInt(400, 1200)
private val alphaRad = (moveAngleDeg - patternAngleDeg) * PI / 180.0

private fun Modifier.animatedPattern(
    shader: BitmapShader,
    brush: ShaderBrush,
    matrix: Matrix,
    backgroundColor: Color,
    patternColor: Color,
    tileSize: Double,
    timeState: State<Long>,
): Modifier = drawBehind {
    val dist = timeState.value * speedPxPerSec / 1000.0
    val lx = (cos(alphaRad) * dist % tileSize).toFloat()
    val ly = (sin(alphaRad) * dist % tileSize).toFloat()

    matrix.reset()
    matrix.preRotate(patternAngleDeg.toFloat())
    matrix.preTranslate(lx, ly)
    shader.setLocalMatrix(matrix)

    drawRect(color = backgroundColor)
    drawRect(
        brush = brush,
        colorFilter = ColorFilter.tint(patternColor, BlendMode.SrcIn),
    )
}

@Composable
fun PatternSurface(
    modifier: Modifier = Modifier,
    @DrawableRes patternRes: Int = R.drawable.seamless_background,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    val bitmap: Bitmap = remember(patternRes, tileSizePx) {
        val drawable = checkNotNull(ContextCompat.getDrawable(context, patternRes))
        createBitmap(tileSizePx, tileSizePx).also {
            drawable.setBounds(0, 0, tileSizePx, tileSizePx)
            drawable.draw(Canvas(it))
        }
    }

    val timeState: State<Long> = produceState(0L) {
        while (isActive) {
            withFrameMillis { value = it }
        }
    }

    val shader = remember(bitmap) {
        BitmapShader(
            bitmap,
            Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT,
        )
    }
    val brush = remember(shader) { ShaderBrush(shader) }
    val matrix = remember { Matrix() }

    Surface(
        modifier = modifier,
        color = Color.Transparent,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .animatedPattern(
                    shader = shader,
                    brush = brush,
                    matrix = matrix,
                    tileSize = tileSizePx.toDouble(),
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    patternColor = MaterialTheme.colorScheme.surfaceContainer,
                    timeState = timeState,
                ),
            content = { content() },
        )
    }
}

@Preview
@Composable
private fun PatternSurfacePreview() {
    LinguaCelerisTheme {
        PatternSurface(
            modifier = Modifier.fillMaxSize(),
        ) {}
    }
}
