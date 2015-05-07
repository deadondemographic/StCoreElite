package impl;

import android.content.Context;
import android.os.Vibrator;

import framework.Vibration;

public class AndroidVibration implements Vibration
{
	Vibrator vibrator;

	public AndroidVibration(Context context)
	{
		vibrator = 
			(Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	public void vibrate(int time)
	{
			vibrator.vibrate((long)(time));
	}
}
