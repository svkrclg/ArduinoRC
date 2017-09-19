package com.svkrdj.arduinobluetooth;

import android.app.ProgressDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nisrulz.sensey.ChopDetector;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.WristTwistDetector;

import java.io.IOException;
import java.util.UUID;


public class ledControl extends AppCompatActivity {

    // Button btnOn, btnOff, btnDis;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    public Vibrator v;
    private Toolbar mToolbar;
    String address = null;
    TextView Discnt;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    Switch s1,s2;
    ChopDetector.ChopListener chopListener = new ChopDetector.ChopListener() {
        @Override
        public void onChop() {
            transmission("4");
            v.vibrate(100);
            Toast.makeText(getApplicationContext(), "U r choping", Toast.LENGTH_SHORT).show();


        }
    };
    WristTwistDetector.WristTwistListener wristTwistListener = new WristTwistDetector.WristTwistListener() {
        @Override
        public void onWristTwist() {
            transmission("5");
            v.vibrate(100);
            Toast.makeText(getApplicationContext(), "u r wristing", Toast.LENGTH_SHORT).show();

        }
    };
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //view of the ledControl
        setContentView(R.layout.activity_main);
        Sensey.getInstance().init(this);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        mToolbar=(Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        mToggle=new ActionBarDrawerToggle(this, mDrawerLayout,R.string.Open,R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Discnt = (TextView) findViewById(R.id.discnt);
        s2=(Switch)findViewById(R.id.switch2);
        s1=(Switch) findViewById(R.id.switch1);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1)
                {

                Sensey.getInstance().startChopDetection(3, 1, chopListener);
                Sensey.getInstance().startWristTwistDetection(2, 1, wristTwistListener);
                }
                else
                {
                    Sensey.getInstance().stopChopDetection(chopListener);
                    Sensey.getInstance().stopWristTwistDetection(wristTwistListener);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        new ConnectBT().execute();
        Discnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (btSocket!=null) //If the btSocket is busy
                {
                    try
                    {
                        btSocket.close(); //close connection
                    }
                    catch (IOException e)
                    { msg("Error");}
                }
                finish(); //return to the first layout
            }
        });



    }
    public void game(View v)
    {
        switch (v.getId())
        {
            case R.id.a:   transmission("a");
                break;
            case R.id.x:   transmission("x");
                break;
            case R.id.y:   transmission("y");
                break;
            case R.id.b:   transmission("b");
                break;
            case R.id.up:   transmission("u");
                break;
            case R.id.down:   transmission("d");
                break;
            case R.id.right:   transmission("r");
                break;
            case R.id.left:   transmission("l");
                break;

        }
    }
    public void s1call(View v){
        //Get reference of TextView from XML layout

        //Is the switch on?
        boolean on = ((Switch) v).isChecked();

        if(on)
        {
            transmission("0");
        }
        else
        {
            transmission("1");
        }
    }
    public void s2call(View v){
        //Get reference of TextView from XML layout

        //Is the switch on?
        boolean on = ((Switch) v).isChecked();

        if(on)
        {
            transmission("2");
        }
        else
        {
            transmission("3");
        }
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void transmission(String value)
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write(value.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Sensey.getInstance().stopChopDetection(chopListener);
        Sensey.getInstance().stopWristTwistDetection(wristTwistListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Sensey.getInstance().stopChopDetection(chopListener);
        Sensey.getInstance().stopWristTwistDetection(wristTwistListener);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        Sensey.getInstance().stopChopDetection(chopListener);
        Sensey.getInstance().stopWristTwistDetection(wristTwistListener);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}