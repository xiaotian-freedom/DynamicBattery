# DynamicBattery
一款可自由改动朝向的电池组件

# 使用方法：

## XML中添加如下引用：

```
<com.storn.view.battery.BigBatteryView
        android:id="@+id/bigBatteryView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        app:big_bv_body_bg_color="#F3F3F3"
        app:big_bv_body_corner="23dp"
        app:big_bv_body_height="300dp"
        app:big_bv_body_power_color="@color/purple_500"
        app:big_bv_body_width="140dp"
        app:big_bv_header_color="#F3F3F3"
        app:big_bv_header_corner="5dp"
        app:big_bv_header_height="14dp"
        app:big_bv_header_margin="5dp"
        app:big_bv_header_width="56dp"
        app:big_bv_orientation="to_up"
        app:big_bv_percent_text_color="#333333"
        app:big_bv_percent_text_size="26sp"
        app:big_bv_show_power_text="true"
        app:big_bv_text_margin="10dp"
        app:big_bv_tip_text="剩余电量"
        app:big_bv_tip_text_color="#333333"
        app:big_bv_tip_text_size="14sp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

## 代码中调用代码动态改变电池朝向：
```
//朝上
binding.bigBatteryView.setOrientation(BatteryOrientation.TO_UP)
//朝下
binding.bigBatteryView.setOrientation(BatteryOrientation.TO_DOWN)
//朝左
binding.bigBatteryView.setOrientation(BatteryOrientation.TO_LEFT)
//朝右
binding.bigBatteryView.setOrientation(BatteryOrientation.TO_RIGHT)
```

## 想要把电池动起来可调用demo中的如下方法
```
//开始电量动画
startAnim()

//重置电量动画
resetAnim()
```

## 动态图：
![GIF](https://github.com/xiaotian-freedom/DynamicBattery/blob/main/preview/53744d85-f1ef-4c72-b9f4-f4fe452b3b0e.gif)

## 静态图：
![图1](https://github.com/xiaotian-freedom/DynamicBattery/blob/main/preview/Screenshot_20221126-184823.png)
![图2](https://github.com/xiaotian-freedom/DynamicBattery/blob/main/preview/Screenshot_20221126-184831.png)
![图3](https://github.com/xiaotian-freedom/DynamicBattery/blob/main/preview/Screenshot_20221126-184835.png)
![图4](https://github.com/xiaotian-freedom/DynamicBattery/blob/main/preview/Screenshot_20221126-184840.png)
![图5](https://github.com/xiaotian-freedom/DynamicBattery/blob/main/preview/Screenshot_20221126-184848.png)
![图6](https://github.com/xiaotian-freedom/DynamicBattery/blob/main/preview/Screenshot_20221126-184855.png)
![图7](https://github.com/xiaotian-freedom/DynamicBattery/blob/main/preview/Screenshot_20221126-184901.png)
![图8](https://github.com/xiaotian-freedom/DynamicBattery/blob/main/preview/Screenshot_20221126-184906.png)
![图9](https://github.com/xiaotian-freedom/DynamicBattery/blob/main/preview/Screenshot_20221126-184911.png)
![图10](https://github.com/xiaotian-freedom/DynamicBattery/blob/main/preview/Screenshot_20221126-184914.png)
