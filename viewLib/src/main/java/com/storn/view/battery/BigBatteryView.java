package com.storn.view.battery;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.storn.view.R;

/**
 * 用于展示电量的大电池
 */
public class BigBatteryView extends View {
    //电池方向
    private BatteryOrientation orientation;
    //电池头宽度
    private int headerWidth;
    //电池头高度
    private int headerHeight;
    //电池头颜色
    private int headerColor;
    //电池头圆角
    private float headerCorner;
    //电池头与电量的间隔
    private float headerMargin;
    //电量宽度
    private int bodyWidth;
    //电量高度
    private int bodyHeight;
    //电量圆角
    private float bodyCorner;
    //电量背景色
    private int bodyColor;
    //当前电量颜色
    private int powerColor;
    //是否显示电量百分比
    private boolean isShowPercentText;
    //电量百分比文字大小
    private int percentTextSize;
    //电量百分比文字颜色
    private int percentTextColor;
    //电量提示文字大小
    private int tipTextSize;
    //电量提示文字颜色
    private int tipTextColor;
    //电量提示文字
    private String tipText;
    //电量提示与百分比间距
    private int textMargin;
    //当前电量
    private int currentPower = 0;
    //电池总宽度
    private int totalWidth;
    //电池总高度
    private int totalHeight;

    //电池头区域
    private RectF headerRect;
    //电池区域
    private RectF bodyRect;
    //电量区域
    private RectF powerRect;
    //电池头画笔
    private Paint headerPaint;
    //电池画笔
    private Paint bodyPaint;
    //电量画笔
    private Paint powerPaint;
    //电量百分比画笔
    private Paint percentPaint;
    //电量提示画笔
    private Paint tipPaint;

    //是否动态改变电池方向
    private boolean isChangeOrientation;

    public BigBatteryView(Context context) {
        this(context, null);
    }

    public BigBatteryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigBatteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context, attrs);
        initPaints();
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BigBatteryView);
        headerWidth = a.getDimensionPixelSize(R.styleable.BigBatteryView_big_bv_header_width, 10);
        headerHeight = a.getDimensionPixelSize(R.styleable.BigBatteryView_big_bv_header_height, 2);
        headerCorner = a.getDimensionPixelSize(R.styleable.BigBatteryView_big_bv_header_corner, 5);
        headerColor = a.getColor(R.styleable.BigBatteryView_big_bv_header_color, Color.WHITE);
        headerMargin = a.getDimensionPixelSize(R.styleable.BigBatteryView_big_bv_header_margin, 5);
        bodyWidth = a.getDimensionPixelSize(R.styleable.BigBatteryView_big_bv_body_width, 50);
        bodyHeight = a.getDimensionPixelSize(R.styleable.BigBatteryView_big_bv_body_height, 150);
        bodyCorner = a.getDimensionPixelSize(R.styleable.BigBatteryView_big_bv_body_corner, 10);
        bodyColor = a.getColor(R.styleable.BigBatteryView_big_bv_body_bg_color, Color.WHITE);
        powerColor = a.getColor(R.styleable.BigBatteryView_big_bv_body_power_color, Color.GREEN);
        isShowPercentText = a.getBoolean(R.styleable.BigBatteryView_big_bv_show_power_text, true);
        percentTextSize = a.getDimensionPixelSize(R.styleable.BigBatteryView_big_bv_percent_text_size, 18);
        percentTextColor = a.getColor(R.styleable.BigBatteryView_big_bv_percent_text_color, Color.BLACK);
        tipTextSize = a.getDimensionPixelSize(R.styleable.BigBatteryView_big_bv_tip_text_size, 14);
        tipTextColor = a.getColor(R.styleable.BigBatteryView_big_bv_tip_text_color, Color.BLACK);
        tipText = a.getNonResourceString(R.styleable.BigBatteryView_big_bv_tip_text);
        textMargin = a.getDimensionPixelSize(R.styleable.BigBatteryView_big_bv_text_margin, 10);

        // 默认头朝上
        int orientationInt = a.getInt(R.styleable.BigBatteryView_big_bv_orientation, 2);
        switch (orientationInt) {
            case 0:
                orientation = BatteryOrientation.TO_LEFT;
                break;
            case 1:
                orientation = BatteryOrientation.TO_RIGHT;
                break;
            case 2:
                orientation = BatteryOrientation.TO_UP;
                break;
            case 3:
                orientation = BatteryOrientation.TO_DOWN;
                break;
        }
        a.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaints() {
        // 电池头部
        headerPaint = new Paint();
        headerPaint.setAntiAlias(true);
        headerPaint.setStyle(Paint.Style.FILL);
        headerPaint.setColor(headerColor);
        // 电池部分
        bodyPaint = new Paint();
        bodyPaint.setAntiAlias(true);
        bodyPaint.setStyle(Paint.Style.FILL);
        bodyPaint.setColor(bodyColor);
        //电量部分
        powerPaint = new Paint();
        powerPaint.setAntiAlias(true);
        powerPaint.setStyle(Paint.Style.FILL);
        powerPaint.setColor(powerColor);
        if (isShowPercentText) {//展示百分比文字
            //百分比
            percentPaint = new Paint();
            percentPaint.setAntiAlias(true);
            percentPaint.setColor(percentTextColor);
            percentPaint.setTextAlign(Paint.Align.CENTER);
            percentPaint.setTextSize(percentTextSize);
            percentPaint.setFakeBoldText(true);
            //文字提示
            tipPaint = new Paint();
            tipPaint.setAntiAlias(true);
            tipPaint.setColor(tipTextColor);
            tipPaint.setTextAlign(Paint.Align.CENTER);
            tipPaint.setTextSize(tipTextSize);
        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isChangeOrientation) {
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
            int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
            int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
            if (widthMode == MeasureSpec.EXACTLY) {
                //如果match_parent或者具体的值，直接赋值
                totalWidth = widthSize;
            } else {
                if (orientation == BatteryOrientation.TO_LEFT ||
                        orientation == BatteryOrientation.TO_RIGHT) {
                    totalWidth = (int) (headerHeight + headerMargin + bodyHeight);
                } else {
                    totalWidth = bodyWidth;
                }
            }
            if (heightMode == MeasureSpec.EXACTLY) {
                totalHeight = heightSize;
            } else {
                if (orientation == BatteryOrientation.TO_LEFT ||
                        orientation == BatteryOrientation.TO_RIGHT) {
                    totalHeight = (int) (headerWidth + headerMargin + bodyWidth);
                } else {
                    totalHeight = (int) (headerHeight + headerMargin + bodyHeight);
                }
            }
        }
        setMeasuredDimension(totalWidth, totalHeight);
    }

    /**
     * 重新计算各个宽高值
     */
    private void calcDimension() {
        headerWidth = headerWidth ^ headerHeight;
        headerHeight = headerWidth ^ headerHeight;
        headerWidth = headerWidth ^ headerHeight;
        bodyWidth = bodyWidth ^ bodyHeight;
        bodyHeight = bodyWidth ^ bodyHeight;
        bodyWidth = bodyWidth ^ bodyHeight;
        totalWidth = totalWidth ^ totalHeight;
        totalHeight = totalWidth ^ totalHeight;
        totalWidth = totalWidth ^ totalHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        drawBattery(canvas);
        canvas.restoreToCount(layerId);
        drawPercentText(canvas);
    }

    /**
     * 绘制电池
     */
    private void drawBattery(Canvas canvas) {
        int headerLeft;
        int headerTop;
        int headerBottom;
        int bodyLeft;
        int bodyTop;
        int powerTop;
        int powerLeft;
        int powerBottom;

        if (orientation == BatteryOrientation.TO_UP) {
            if (headerRect == null) {
                headerTop = 0;
                headerBottom = headerHeight;
                headerLeft = (totalWidth - headerWidth) / 2;
                headerRect = new RectF(headerLeft, headerTop, headerLeft + headerWidth, headerBottom);
            }
            if (bodyRect == null) {
                bodyLeft = (totalWidth - bodyWidth) / 2;
                bodyTop = (int) (headerHeight + headerMargin);
                bodyRect = new RectF(bodyLeft, bodyTop, bodyLeft + bodyWidth, bodyHeight + bodyTop);
            }
            int powerH = (bodyHeight * currentPower) / 100;
            powerTop = (int) (headerHeight + headerMargin + (bodyHeight - powerH));
            powerBottom = powerTop + powerH;
            if (powerRect == null) {
                powerLeft = (totalWidth - bodyWidth) / 2;
                powerRect = new RectF(powerLeft, powerTop, powerLeft + bodyWidth, powerBottom);
            } else {
                powerRect.top = powerTop;
                powerRect.bottom = powerTop + powerH;
            }
        } else if (orientation == BatteryOrientation.TO_DOWN) {
            if (headerRect == null) {
                headerTop = (int) (bodyHeight + headerMargin);
                headerBottom = headerTop + headerHeight;
                headerLeft = (totalWidth - headerWidth) / 2;
                headerRect = new RectF(headerLeft, headerTop, headerLeft + headerWidth, headerBottom);
            }
            if (bodyRect == null) {
                bodyLeft = (totalWidth - bodyWidth) / 2;
                bodyTop = 0;
                bodyRect = new RectF(bodyLeft, bodyTop, bodyLeft + bodyWidth, bodyHeight + bodyTop);
            }
            powerTop = 0;
            int powerH = (bodyHeight * currentPower) / 100;
            powerBottom = powerTop + powerH;

            if (powerRect == null) {
                powerLeft = (totalWidth - bodyWidth) / 2;
                powerRect = new RectF(powerLeft, powerTop, powerLeft + bodyWidth, powerBottom);
            } else {
                powerRect.top = powerTop;
                powerRect.bottom = powerBottom;
            }
        } else if (orientation == BatteryOrientation.TO_LEFT) {
            if (headerRect == null) {
                headerLeft = 0;
                headerTop = (totalHeight - headerHeight) / 2;
                headerBottom = headerTop + headerHeight;
                headerRect = new RectF(headerLeft, headerTop, headerLeft + headerWidth, headerBottom);
            }
            if (bodyRect == null) {
                bodyLeft = (int) (headerWidth + headerMargin);
                bodyTop = 0;
                bodyRect = new RectF(bodyLeft, bodyTop, bodyLeft + bodyWidth, bodyHeight + bodyTop);
            }
            int powerW = (bodyWidth * currentPower) / 100;
            powerLeft = (int) (headerWidth + headerMargin + (bodyWidth - powerW));
            if (powerRect == null) {
                powerRect = new RectF(powerLeft, 0, powerLeft + bodyWidth, bodyHeight);
            } else {
                powerRect.left = powerLeft;
                powerRect.right = powerLeft + bodyWidth;
            }
        } else {
            if (headerRect == null) {
                headerLeft = (int) (bodyWidth + headerMargin);
                headerTop = (totalHeight - headerHeight) / 2;
                headerBottom = headerTop + headerHeight;
                headerRect = new RectF(headerLeft, headerTop, headerLeft + headerWidth, headerBottom);
            }
            if (bodyRect == null) {
                bodyLeft = 0;
                bodyTop = 0;
                bodyRect = new RectF(bodyLeft, bodyTop, bodyLeft + bodyWidth, bodyHeight + bodyTop);
            }
            int powerW = (bodyWidth * currentPower) / 100;
            powerLeft = 0;
            if (powerRect == null) {
                powerRect = new RectF(powerLeft, 0, powerW, bodyHeight);
            } else {
                powerRect.left = powerLeft;
                powerRect.right = powerW;
            }
        }

        if (currentPower == 100) {
            headerPaint.setColor(powerColor);
        } else {
            headerPaint.setColor(headerColor);
        }
        //绘制电池头
        canvas.drawRoundRect(headerRect, headerCorner, headerCorner, headerPaint);

        //绘制电池身体
        canvas.drawRoundRect(bodyRect, bodyCorner, bodyCorner, bodyPaint);

        //绘制电量
        powerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRoundRect(powerRect, bodyCorner, bodyCorner, powerPaint);
        powerPaint.setXfermode(null);
    }

    /**
     * 绘制电量百分比
     */
    private void drawPercentText(Canvas canvas) {
        if (!isShowPercentText) return;
        if (currentPower >= 60) {
            percentPaint.setColor(Color.WHITE);
        } else {
            percentPaint.setColor(percentTextColor);
        }
        if (currentPower >= 50) {
            tipPaint.setColor(Color.WHITE);
        } else {
            tipPaint.setColor(tipTextColor);
        }
        if (orientation == BatteryOrientation.TO_UP) {
            float x = totalWidth / 2f;
            Paint.FontMetrics metrics = percentPaint.getFontMetrics();
            Paint.FontMetrics metrics1 = tipPaint.getFontMetrics();
            float y1 = ((bodyHeight / 2f - textMargin / 2f) - (metrics.bottom - metrics.top) / 2) + headerHeight + headerMargin;
            float y2 = bodyHeight / 2f + textMargin / 2f + (metrics1.bottom - metrics1.top) / 2 + headerHeight + headerMargin;
            canvas.drawText(currentPower + "%", x, y1, percentPaint);
            canvas.drawText(tipText, x, y2, tipPaint);
        } else if (orientation == BatteryOrientation.TO_DOWN) {
            float x = totalWidth / 2f;
            Paint.FontMetrics metrics = percentPaint.getFontMetrics();
            Paint.FontMetrics metrics1 = tipPaint.getFontMetrics();
            float y1 = ((bodyHeight / 2f - textMargin / 2f) - (metrics.bottom - metrics.top) / 2);
            float y2 = bodyHeight / 2f + textMargin / 2f + (metrics1.bottom - metrics1.top) / 2 + headerHeight + headerMargin;
            canvas.rotate(-180, x, y2);
            canvas.drawText(currentPower + "%", x, y2, percentPaint);
            canvas.rotate(180, x, y2);
            canvas.rotate(-180, x, y1);
            canvas.drawText(tipText, x, y1, tipPaint);
            canvas.rotate(180, x, y1);
        } else if (orientation == BatteryOrientation.TO_LEFT) {
            float y = totalHeight / 2f;
            float x1 = (bodyWidth / 2f - textMargin / 2f);
            float x2 = bodyWidth / 2f + textMargin / 2f + headerWidth + headerMargin;
            canvas.rotate(-90, x1, y);
            canvas.drawText(currentPower + "%", x1, y, percentPaint);
            canvas.rotate(90, x1, y);
            canvas.rotate(-90, x2, y);
            canvas.drawText(tipText, x2, y, tipPaint);
            canvas.rotate(90, x2, y);
        } else {
            float y = totalHeight / 2f;
            float x1 = (bodyWidth / 2f - textMargin / 2f);
            float x2 = bodyWidth / 2f + textMargin / 2f + headerWidth + headerMargin;
            canvas.rotate(90, x2, y);
            canvas.drawText(currentPower + "%", x2, y, percentPaint);
            canvas.rotate(-90, x2, y);
            canvas.rotate(90, x1, y);
            canvas.drawText(tipText, x1, y, tipPaint);
            canvas.rotate(-90, x1, y);
        }
    }

    /**
     * 设置当前电量
     */
    public void setCurrentPower(float currentPower) {
        this.currentPower = (int) currentPower;
        if (this.currentPower < 0) {
            this.currentPower = 0;
        }
        if (this.currentPower > 100) {
            this.currentPower = 100;
        }
        postInvalidate();
    }

    /**
     * 获取当前电量
     */
    public float getCurrentPower() {
        return currentPower;
    }

    /**
     * 设置方向
     */
    public void setOrientation(BatteryOrientation orientation) {
        if (this.orientation == orientation) return;
        headerRect = null;
        bodyRect = null;
        powerRect = null;
        isChangeOrientation = true;
        if (
                ((orientation == BatteryOrientation.TO_UP ||
                        orientation == BatteryOrientation.TO_DOWN) &&
                        (this.orientation == BatteryOrientation.TO_LEFT ||
                                this.orientation == BatteryOrientation.TO_RIGHT)) ||
                        ((orientation == BatteryOrientation.TO_LEFT ||
                                orientation == BatteryOrientation.TO_RIGHT) &&
                                (this.orientation == BatteryOrientation.TO_UP ||
                                        this.orientation == BatteryOrientation.TO_DOWN))
        ) {
            calcDimension();
            this.orientation = orientation;
            requestLayout();
        } else {
            this.orientation = orientation;
            postInvalidate();
        }
    }
}
