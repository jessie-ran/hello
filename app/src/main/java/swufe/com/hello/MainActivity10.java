package swufe.com.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class MainActivity10 extends AppCompatActivity {
    String TAG="rateCalc";
    float rate=0f;
    TextView inp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);


        String title =getIntent().getStringExtra("title");
        rate=getIntent().getFloatExtra("rate",0f);
        ((TextView)findViewById(R.id.title)).setText(title); //转成TextView对象，匿名对象
        inp=findViewById(R.id.inp);
        inp.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
//
            @Override
            public void afterTextChanged(Editable s) {
                TextView show=(TextView)MainActivity10.this.findViewById(R.id.show);
                if(s.length()>0){
                    float val=Float.parseFloat(s.toString());
                    show.setText(val+"RMB==>"+(rate*val));

                }
                else {
                    show.setText("");
                }
            }
        });
    }
}