package game2048.sdlfcc.com.game2048;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public  class MainActivity extends ActionBarActivity {
    private int score=0; //用于记录分数。
    private TextView tvScore;
    private static MainActivity mainActivity=null;
    public MainActivity() {
        mainActivity=this;
    }
    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public void clearScore(){    //清除计分
        score=0;
        showScore();
    }
    private void showScore(){  //显示计分
        tvScore.setText(score+"");
    }//显示得分
    public void addScore(int s){  //累加计分
        score+=s;
        showScore();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore=(TextView)findViewById(R.id.tvScore);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}