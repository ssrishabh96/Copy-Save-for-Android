package dynamic.movies.rishabh.sliderwelcome.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by RISHABH on 6/19/2017.
 */

public class MyImageView extends View {

    private static final String TAG = MyImageView.class.getSimpleName();

    private Bitmap mBitmapImage;
    private float mAnimateFactor = 0f;
    private Rect mSrcBounds = new Rect();
    private Rect mDrawBounds = new Rect();
    private Paint mBitmapPaint = new Paint();

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mSrcBounds.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        mDrawBounds.set(0, 0, mSrcBounds.right, mSrcBounds.bottom);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmapImage != null) {
            int range = mBitmapImage.getWidth() - mDrawBounds.width();
            mSrcBounds.left = Math.round(mAnimateFactor * range);
            mSrcBounds.right = mDrawBounds.width() + mSrcBounds.left;
            canvas.drawBitmap(mBitmapImage, mSrcBounds, mDrawBounds, mBitmapPaint);
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        mBitmapImage = bitmap;
        invalidate();
    }

    public void setAnimationFactor(float factor) {
        if (factor < 0 || factor > 1) {
            return;
        }
        mAnimateFactor = 1 - factor;
        invalidate();
    }

}
