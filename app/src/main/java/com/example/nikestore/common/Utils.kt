package com.sevenlearn.nikestore.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.example.nikestore.data.Banner
import com.example.nikestore.data.Product
import timber.log.Timber
import java.util.Locale

fun convertDpToPixel(dp: Float, context: Context?): Float {
    return if (context != null) {
        val resources = context.resources
        val metrics = resources.displayMetrics
        dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    } else {
        val metrics = Resources.getSystem().displayMetrics
        dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}

fun formatPrice(
    price: Number,
    unitRelativeSizeFactor: Float = 0.7f
): SpannableString {
    val newPrice=String.format("%,d",price)
    val currencyLabel="تومان"
    val spannableString = SpannableString("$newPrice $currencyLabel")
    spannableString.setSpan(
        RelativeSizeSpan(unitRelativeSizeFactor),
        spannableString.indexOf(currencyLabel),
        spannableString.length,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableString
}

fun View.implementSpringAnimationTrait() {
    val scaleXAnim = SpringAnimation(this, DynamicAnimation.SCALE_X, 0.90f)
    val scaleYAnim = SpringAnimation(this, DynamicAnimation.SCALE_Y, 0.90f)

    setOnTouchListener { v, event ->
        Timber.i(event.action.toString())
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                scaleXAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                scaleXAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                scaleXAnim.start()

                scaleYAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                scaleYAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                scaleYAnim.start()

            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                scaleXAnim.cancel()
                scaleYAnim.cancel()
                val reverseScaleXAnim = SpringAnimation(this, DynamicAnimation.SCALE_X, 1f)
                reverseScaleXAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                reverseScaleXAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                reverseScaleXAnim.start()

                val reverseScaleYAnim = SpringAnimation(this, DynamicAnimation.SCALE_Y, 1f)
                reverseScaleYAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                reverseScaleYAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                reverseScaleYAnim.start()


            }
        }

        false
    }
}

fun createProducts(): ArrayList<Product> {
    val product2 = Product(
        id = 440,
        title = "نایک فلیکس ران مدل ZoomX",
        price = 2491411,
        discount = 23,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/e5cc9ab3-5c10-43fa-b007-5bb4fcfc07d9/NIKE+FREE+METCON+6.png",
        status = 1,
        previous_price = 2491388
    )
    val product3 = Product(
        id = 432,
        title = "نایک ایر زوم",
        price = 2465825,
        discount = 14,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/3fa2eedd-f437-4b7b-b389-b2add0ee59e0/SABRINA+2.png",
        status = 1,
        previous_price = 2465811
    )
    val product4 = Product(
        id = 433,
        title = "نایک ترایل رانر",
        price = 4605005,
        discount = 29,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/be57a430-8d83-4546-901f-47dc2cd9de7c/PEGASUS+PLUS.png",
        status = 1,
        previous_price = 4604976
    )
    val product5 = Product(
        id = 434,
        title = "نایک وایپر ۳",
        price = 2220002,
        discount = 22,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/pexd96bxgbyteo2xziaf/AIR+VAPORMAX+PLUS.png",
        status = 1,
        previous_price = 2219980
    )
    val product6 = Product(
        id = 435,
        title = "نایک موشن",
        price = 1146512,
        discount = 30,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/wgxfbsoi6ruiakenoscv/NIKE+SHOX+TL.png",
        status = 1,
        previous_price = 1146482
    )
    val product7 = Product(
        id = 437,
        title = "نایک پریسیژن",
        price = 1214037,
        discount = 27,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/4f37fca8-6bce-43e7-ad07-f57ae3c13142/AIR+FORCE+1+%2707.png",
        status = 1,
        previous_price = 1214010
    )
    val product8 = Product(
        id = 438,
        title = "نایک مترو ران",
        price = 1880809,
        discount = 5,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/fdef08d5-ddf9-493e-8e27-8aab37447b78/NIKE+DUNK+LOW+RETRO.png",
        status = 1,
        previous_price = 1880804
    )
    val product9 = Product(
        id = 439,
        title = "نایک گریویتی",
        price = 3975416,
        discount = 26,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/89525e32-aeaf-4a53-8248-90536633f346/AIR+MAX+90.png",
        status = 1,
        previous_price = 3975390
    )
    val product10 = Product(
        id = 436,
        title = "کتانی نایک سیگما",
        price = 3286646,
        discount = 22,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/aee6b5e9-a5ee-4bc3-8427-3095ade69faf/AIR+MAX+270.png",
        status = 1,
        previous_price = 3286624
    )

    return arrayListOf(product2,product3,product4,product5,product6,product7,product8,product9,product10)
}

fun createProducts2(): ArrayList<Product> {
    val product2 = Product(
        id = 440,
        title = "نایک فلیکس ران",
        price = 2491411,
        discount = 23,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/3fa2eedd-f437-4b7b-b389-b2add0ee59e0/SABRINA+2.png",
        status = 1,
        previous_price = 2491388
    )
    val product3 = Product(
        id = 432,
        title = "نایک ایر زوم",
        price = 2465825,
        discount = 14,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/e5cc9ab3-5c10-43fa-b007-5bb4fcfc07d9/NIKE+FREE+METCON+6.png",
        status = 1,
        previous_price = 2465811
    )
    val product4 = Product(
        id = 433,
        title = "نایک ترایل رانر",
        price = 4605005,
        discount = 29,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/be57a430-8d83-4546-901f-47dc2cd9de7c/PEGASUS+PLUS.png",
        status = 1,
        previous_price = 4604976
    )
    val product5 = Product(
        id = 434,
        title = "نایک وایپر ۳",
        price = 2220002,
        discount = 22,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/pexd96bxgbyteo2xziaf/AIR+VAPORMAX+PLUS.png",
        status = 1,
        previous_price = 2219980
    )
    val product6 = Product(
        id = 435,
        title = "نایک موشن",
        price = 1146512,
        discount = 30,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/wgxfbsoi6ruiakenoscv/NIKE+SHOX+TL.png",
        status = 1,
        previous_price = 1146482
    )
    val product7 = Product(
        id = 437,
        title = "نایک پریسیژن",
        price = 1214037,
        discount = 27,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/4f37fca8-6bce-43e7-ad07-f57ae3c13142/AIR+FORCE+1+%2707.png",
        status = 1,
        previous_price = 1214010
    )
    val product8 = Product(
        id = 438,
        title = "نایک مترو ران",
        price = 1880809,
        discount = 5,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/fdef08d5-ddf9-493e-8e27-8aab37447b78/NIKE+DUNK+LOW+RETRO.png",
        status = 1,
        previous_price = 1880804
    )
    val product9 = Product(
        id = 439,
        title = "نایک گریویتی",
        price = 3975416,
        discount = 26,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/89525e32-aeaf-4a53-8248-90536633f346/AIR+MAX+90.png",
        status = 1,
        previous_price = 3975390
    )
    val product10 = Product(
        id = 436,
        title = "کتانی نایک سیگما",
        price = 3286646,
        discount = 22,
        image = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/aee6b5e9-a5ee-4bc3-8427-3095ade69faf/AIR+MAX+270.png",
        status = 1,
        previous_price = 3286624
    )

    return arrayListOf(
        product2,
        product3,
        product4,
        product5,
        product6,
        product7,
        product8,
        product9,
        product10
    )
}

fun createBanners():MutableList<Banner>{
    val banner1=Banner(1001,"https://images.lifestyleasia.com/wp-content/uploads/sites/6/2020/08/03154422/2020_RTT_Sustainability_Zero-Collection_RN_GROUP_06539_R2_hd_1600-1600x900.jpg",2,"0")
    val banner2=Banner(1002,"https://5.imimg.com/data5/SELLER/Default/2022/12/PL/IQ/XV/91293069/nike-men-sport-shoes.jpg",2,"0")
    val banner3=Banner(1003,"https://d26oc3sg82pgk3.cloudfront.net/files/media/edit/image/58252/article_full%401x.jpg",2,"0")
    val banners= mutableListOf(banner1,banner2,banner3)
    return banners
}