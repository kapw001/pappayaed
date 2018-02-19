package com.pappayaed.gener;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pappayaed.R;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        T t = new T();
        Tv tv = new Tv();

        Test<T, Tv> test = new Test<T, Tv>(t);
        test.show(tv);
    }

    public void onCL(View view) {
    }
}


class Test<P, V> {

    private T p;

    public Test(P p) {
        this.p = (T) p;
    }

    public void show(V v) {

        String s = this.p.get();
        ((Tv) v).show(s);
    }

}


class T {
    public String get() {
        return "s";
    }
}

class Tv {
    private static final String TAG = "Tv";

    public void show(String s) {
        Log.e(TAG, "show: hello world " + s);
    }
}
