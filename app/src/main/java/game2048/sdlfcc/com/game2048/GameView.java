package game2048.sdlfcc.com.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 芳芳 on 2016/3/16.
 * 这是一个自定义的游戏布局类，其中包含了游戏的主要逻辑，
 * 其中y表示行，x表示列。
 *
 */
public class GameView extends GridLayout {
    //构造函数
    public GameView(Context context) {
        super(context);
        initGameView();
    }


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }
    //初始化
    public void initGameView(){

        setColumnCount(4);//定义布局列数，行数由两个for循环控制
        setBackgroundColor(0xffbbada0);//定义布局背景颜色
        setOnTouchListener(new View.OnTouchListener(){
            private float startX,startY,offsetX,offsetY;
            public boolean onTouch(View v, MotionEvent event){
               switch (event.getAction()){
                   case MotionEvent.ACTION_DOWN:
                       startX=event.getX();
                       startY=event.getY();
                        break;
                   case MotionEvent.ACTION_UP:
                       offsetX=event.getX()-startX;
                       offsetY=event.getY()-startY;

                       if (Math.abs(offsetX)>Math.abs(offsetY)){
                           if(offsetX<-5)
                               swipeLeft(); //向左划动
                           else if(offsetX>5)
                               swipeRight(); //向右划动
                       }else if(Math.abs(offsetX)<Math.abs(offsetY))
                           if(offsetY<-5)
                               swipeUp(); //向上划动
                           else if(offsetY>5)
                               swipeDown(); //向下划动
                        break;
               }
                return true;
        }
        });

    }

    @Override
    //自定义View，当View大小发生变化时，系统自动调用该方法，即为回调方法。
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth=(Math.min(w,h)-10)/4;

        addCard(cardWidth,cardWidth);
       // MainActivity.getMainActivity().clearScore();//此处引起页面添加12行，只有后四行有效的错误。

        startGame();
    }
    //向4*4的gridView中添加卡片。
    public void addCard(int cardWidth,int cardHeight){
        Card c;
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                c=new Card(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeight);
                cardsMap[x][y]=c;//定义一个集合，用于存储卡片。
            }

        }

    }
    private void startGame(){
        //MainActivity.getMainActivity().clearScore();
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
               cardsMap[x][y].setNum(0);
           }

        }
        addRandomNum();
        addRandomNum();
    }
    //在游戏开始或每次发生变化时，随机改变卡片数字（2或4）
    private void addRandomNum(){
       emptyPoints.clear();
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                if(cardsMap[x][y].getNum()<=0){
                    emptyPoints.add(new Point(x,y));
                }
            }
        }
        Point p=emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);//按照1:9的概率随机插入2或4
    }
    //向左划动
    public void swipeLeft(){
        boolean merge=false;//判断是否发生变化， y表示行，x表示列。
        for(int y=0;y<4;y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            merge=true;
                          x--;//迭代

                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge=true;

                        }
                        break;
                    }

                }
            }
        }

        if(merge){
            addRandomNum();
            checkComplete();
        }

    }
    //向右划动
    public void swipeRight(){
        boolean merge=false;
        for(int y=0;y<4;y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            merge=true;
                            x++;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }
    //向上划动
    public void swipeUp(){
        boolean merge=false;
        for(int x=0;x<4;x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                             merge=true;
                             y--;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }
    //向下划动
    public void swipeDown(){
        boolean merge=false;
        for(int x=0;x<4;x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            merge=true;
                            y++;
                            } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }
    //检查游戏是否结束
    private void checkComplete(){
        boolean complete=true;
        ALL:
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                //判断游戏没有结束的条件
                if(cardsMap[x][y].getNum()==0||(x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||(x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))
                        ||(y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||(y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){
                    complete=false;
                    break ALL;
                }
            }
        }
        //游戏结束弹出如下对话框
        if(complete){
            new AlertDialog.Builder(getContext()).setTitle("Hello").setMessage("Game Over").setPositiveButton("Replay",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.getMainActivity().clearScore();
                    startGame();
                }
            }).show();
        }
    }
    private Card[][] cardsMap=new Card[4][4];//卡片集合。
    private List<Point> emptyPoints=new ArrayList<Point>();//点集
}
