package com.jzg.lib.view

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.camera.view.PreviewView
import com.jzg.jcpt.utils.CameraEventListener

/**
 *
 * @description
 * @Author qiwx
 * @Date 2020/8/5 3:48 PM
 *
 **/
class CameraPreviewView : PreviewView, GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {
    constructor(ctx: Context) : this(ctx, null)
    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)
    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        ctx,
        attrs,
        defStyleAttr, 0
    )

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        ctx,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        this.ctx = ctx
        mGestureDetector = GestureDetector(ctx, this)
        mGestureDetector!!.setOnDoubleTapListener(this)
    }

    private var ctx: Context? = null
    private var mGestureDetector: GestureDetector? = null
    private var mCameraEventListener: CameraEventListener? = null

    /**
     * 缩放相关
     */
    private var currentDistance = 0f
    private var lastDistance = 0f


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // 接管onTouchEvent
        return mGestureDetector!!.onTouchEvent(event)
    }
    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }



    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        currentDistance = 0f
        lastDistance = 0f
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        // 大于两个触摸点

        // 大于两个触摸点
        if (e2!!.pointerCount >= 2) {

            //event中封存了所有屏幕被触摸的点的信息，第一个触摸的位置可以通过event.getX(0)/getY(0)得到
            val offSetX = e2.getX(0) - e2.getX(1)
            val offSetY = e2.getY(0) - e2.getY(1)
            //运用三角函数的公式，通过计算X,Y坐标的差值，计算两点间的距离
            currentDistance =
                Math.sqrt(offSetX * offSetX + offSetY * offSetY.toDouble()).toFloat()
            if (lastDistance == 0f) { //如果是第一次进行判断
                lastDistance = currentDistance
            } else {
                if (currentDistance - lastDistance > 10) {
                    // 放大
                    mCameraEventListener?.zoom()
                } else if (lastDistance - currentDistance > 10) {
                    // 缩小
                    mCameraEventListener?.zoomOut()
                }
            }
            //在一次缩放操作完成后，将本次的距离赋值给lastDistance，以便下一次判断
            //但这种方法写在move动作中，意味着手指一直没有抬起，监控两手指之间的变化距离超过10
            //就执行缩放操作，不是在两次点击之间的距离变化来判断缩放操作
            //故这种将本次距离留待下一次判断的方法，不能在两次点击之间使用
            lastDistance = currentDistance
        }
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        mCameraEventListener?.longClick(e!!.x, e.y)

    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        mCameraEventListener?.doubleClick(e!!.x, e.y)
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        mCameraEventListener?.click(e!!.x, e.y)
        return true
    }

    public fun setCameraToucherListener(listener: CameraEventListener) {
        this.mCameraEventListener = listener
    }


}