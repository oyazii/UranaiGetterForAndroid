package nk9k.UranaiGetter;

import java.util.EnumSet;

import android.content.Context;
import android.widget.ArrayAdapter;

public enum EConstellations {
	Aries("おひつじ座", 1),
	Taurus("おうし座", 2),
	Gemini("ふたご座", 3),
	Cancer("かに座", 4),
	Leo("しし座", 5),
	Virgo("おとめ座", 6),
	Libra("てんびん座", 7),
	Scorpius("さそり座", 8),
	Sagittarius("いて座", 9),
	Capriconus("やぎ座", 10),
	Aquarius("みずがめ座", 11),
	Pisces("うお座", 12);

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
//		if (myName == "Aries"){value = "おひつじ座";}
//		else if (myName == "Taurus"){value = "おうし座";}
//		else if (myName == "Gemini"){value = "ふたご座";}
//		else if (myName == "Cancer"){value = "かに座";}
//		else if (myName == "Leo"){value = "しし座";}
//		else if (myName == "Virgo"){value = "おとめ座";}
//		else if (myName == "Libra"){value = "てんびん座";}
//		else if (myName == "Scorpius"){value = "さそり座";}
//		else if (myName == "Sagittarius"){value = "いて座";}
//		else if (myName == "Capriconus"){value = "やぎ座";}
//		else if (myName == "Aquarius"){value = "みずがめ座";}
//		else if (myName == "Pisces"){value = "うお座";}
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
