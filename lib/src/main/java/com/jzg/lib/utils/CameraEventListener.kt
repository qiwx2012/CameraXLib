package com.jzg.jcpt.utils

/**
 *
 * @description
 * @Author qiwx
 * @Date 2020/8/5 1:53 PM
 * 相机事件监听
 **/
public interface CameraEventListener {
    fun zoom()//放大
    fun zoomOut()//缩小
    fun click(x: Float, y: Float)
    fun doubleClick(x: Float, y: Float)
    fun longClick(x: Float, y: Float)

}