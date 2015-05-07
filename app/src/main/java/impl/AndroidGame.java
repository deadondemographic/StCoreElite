package impl;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import framework.Audio;
import framework.FileIO;
import framework.Game;
import framework.Graphics;
import framework.Input;
import framework.Screen;
import framework.Vibration;
import impl.AndroidVibration;
import jason.cruz.coreelite.R;


public abstract class AndroidGame extends Activity implements Game
{
	AndroidFastRenderView renderView;
	Graphics graphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Vibration vibration;
	Screen screen;
    private AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Bitmap frameBuffer = 
			Bitmap.createBitmap(
					getWindowManager().getDefaultDisplay().getWidth(),
					getWindowManager().getDefaultDisplay().getHeight(),
					Config.RGB_565);
		renderView = new AndroidFastRenderView(this, frameBuffer);
		graphics = new AndroidGraphics(frameBuffer);
		fileIO = new AndroidFileIO(getAssets());
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, renderView);
		vibration = new AndroidVibration(this);
		screen = getStartScreen();
		//setContentView(renderView);

//        RelativeLayout layout = new RelativeLayout(this);
//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
//        layout.addView(renderView);
//        layout.addView(adView);
//        adView.loadAd(new AdRequest.Builder().build());
//        setContentView(layout);
// jason
        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-5735264719731428/1962012345");

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.adView.setLayoutParams(lp);

        RelativeLayout layout = new RelativeLayout(this);
        layout.addView(renderView);
        layout.addView(this.adView);
        adView.loadAd(new AdRequest.Builder().build());

        setContentView(layout);

		PowerManager powerManager = 
			(PowerManager) getSystemService(Context.POWER_SERVICE);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		screen.resume();
		renderView.resume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		renderView.pause();
		screen.pause();

		if(isFinishing())
			screen.dispose();
	}

	@Override
	public Input getInput()
	{
		return input;
	}

	@Override
	public FileIO getFileIO()
	{
		return fileIO;
	}

	@Override
	public Graphics getGraphics()
	{
		return graphics;
	}

	@Override
	public Audio getAudio()
	{
		return audio;
	}

	@Override
	public void setScreen(Screen screen)
	{
		if(screen == null)
			throw new IllegalArgumentException("Screen must not be null");

		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}

	public Screen getCurrentScreen()
	{
		return screen;
	}

	public Vibration getVibration()
	{
		return vibration;
	}
}
