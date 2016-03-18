package game2048.sdlfcc.com.game2048;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by 芳芳 on 2016/3/16.
 * 这是一个卡片类（其实是一个自定义布局），定义了卡片的基本属性信息及卡片上数字的setter，getter方法，
 * equals方法。
 */

public class Card extends FrameLayout {
    public TextView label;
    public Card(Context context) {
        super(context);
        label=new TextView(getContext());
        label.setTextSize(32);//设置TextView上数字大小
        label.setGravity(Gravity.CENTER);//设置TextView数字显示位置
        label.setBackgroundColor(0x33ffffff);//设置背景颜色
        LayoutParams lp=new LayoutParams(-1,-1);//设置布局参数
        lp.setMargins(10,10,0,0);//设置TextView之间的间隔
        addView(label,lp);//将TextView添加到卡片布局上
        //setNum(0);
    }
    public int num=0;
    //setter()方法
    public void setNum(int num) {
        this.num=num;
        if(num<=0){
            label.setText("");//当设置的Num为0的时候，设置卡片显示空。
        }else{
            label.setText(num+"");
        }

    }
    //getter()方法
    public int getNum() {

        return num;
    }
    //判断卡片上的数字是否相等
    public boolean equals(Card o){
        return getNum()==o.getNum();
    }


}
