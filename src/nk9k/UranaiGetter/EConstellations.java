package nk9k.UranaiGetter;

import java.util.EnumSet;

import android.content.Context;
import android.widget.ArrayAdapter;

public enum EConstellations {
	Aries("Ç®Ç–Ç¬Ç∂ç¿", 1),
	Taurus("Ç®Ç§Çµç¿", 2),
	Gemini("Ç”ÇΩÇ≤ç¿", 3),
	Cancer("Ç©Ç…ç¿", 4),
	Leo("ÇµÇµç¿", 5),
	Virgo("Ç®Ç∆Çﬂç¿", 6),
	Libra("ÇƒÇÒÇ—ÇÒç¿", 7),
	Scorpius("Ç≥ÇªÇËç¿", 8),
	Sagittarius("Ç¢Çƒç¿", 9),
	Capriconus("Ç‚Ç¨ç¿", 10),
	Aquarius("Ç›Ç∏Ç™Çﬂç¿", 11),
	Pisces("Ç§Ç®ç¿", 12);

	private String _JPName;
	public String GetJPName()
	{
		return _JPName;
	}

	private int _ID;
	public int GetID()
	{
		return _ID;
	}

	EConstellations(String JPName, int ID)
	{
		_JPName = JPName;
		_ID = ID;
	}

	@Override
	public String toString() {
		return GetJPName();
//		String value;
//		String myName = this.name();
//		if (myName == "Aries"){value = "Ç®Ç–Ç¬Ç∂ç¿";}
//		else if (myName == "Taurus"){value = "Ç®Ç§Çµç¿";}
//		else if (myName == "Gemini"){value = "Ç”ÇΩÇ≤ç¿";}
//		else if (myName == "Cancer"){value = "Ç©Ç…ç¿";}
//		else if (myName == "Leo"){value = "ÇµÇµç¿";}
//		else if (myName == "Virgo"){value = "Ç®Ç∆Çﬂç¿";}
//		else if (myName == "Libra"){value = "ÇƒÇÒÇ—ÇÒç¿";}
//		else if (myName == "Scorpius"){value = "Ç≥ÇªÇËç¿";}
//		else if (myName == "Sagittarius"){value = "Ç¢Çƒç¿";}
//		else if (myName == "Capriconus"){value = "Ç‚Ç¨ç¿";}
//		else if (myName == "Aquarius"){value = "Ç›Ç∏Ç™Çﬂç¿";}
//		else if (myName == "Pisces"){value = "Ç§Ç®ç¿";}
//		else {value = "";}
//
//		return value;
	}

	public static ArrayAdapter<EConstellations> GetAdapter(Context context)
	{
		ArrayAdapter<EConstellations> wAdapter = new ArrayAdapter<EConstellations>(
				context,
				android.R.layout.simple_spinner_item);
		wAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		wAdapter.add(Aries);
		wAdapter.add(Taurus);
		wAdapter.add(Gemini);
		wAdapter.add(Cancer);
		wAdapter.add(Leo);
		wAdapter.add(Virgo);
		wAdapter.add(Libra);
		wAdapter.add(Scorpius);
		wAdapter.add(Sagittarius);
		wAdapter.add(Capriconus);
		wAdapter.add(Aquarius);
		wAdapter.add(Pisces);

		return wAdapter;
	}

	public static EConstellations GetFromID(int ID)
	{
		for(EConstellations obj : EnumSet.allOf(EConstellations.class))
		{
			if (obj.GetID() == ID) return obj;
		}
		return null;
	}

}
