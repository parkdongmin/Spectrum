package com.toomanybytes.spectrum

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class RoundedTopCornerImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    override fun onDraw(canvas: Canvas?) {
        val clipPath = Path()
        val rect = RectF(0F, 0F, width.toFloat(), height.toFloat() + 200)

        // 좌측 상단과 우측 상단의 코너를 둥글게 만듭니다.
        val radius = resources.getDimension(R.dimen.corner_radius)
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW)

        canvas?.clipPath(clipPath)
        super.onDraw(canvas)
    }
}