package nk9k.UranaiGetter;

import java.util.EnumSet;

import android.content.Context;
import android.widget.ArrayAdapter;

public enum EConstellations {
	Aries("���Ђ���", 1),
	Taurus("��������", 2),
	Gemini("�ӂ�����", 3),
	Cancer("���ɍ�", 4),
	Leo("������", 5),
	Virgo("���Ƃߍ�", 6),
	Libra("�Ă�т��", 7),
	Scorpius("�������", 8),
	Sagittarius("���č�", 9),
	Capriconus("�€��", 10),
	Aquarius("�݂����ߍ�", 11),
	Pisces("������", 12);

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
//		if (myName == "Aries"){value = "���Ђ���";}
//		else if (myName == "Taurus"){value = "��������";}
//		else if (myName == "Gemini"){value = "�ӂ�����";}
//		else if (myName == "Cancer"){value = "���ɍ�";}
//		else if (myName == "Leo"){value = "������";}
//		else if (myName == "Virgo"){value = "���Ƃߍ�";}
//		else if (myName == "Libra"){value = "�Ă�т��";}
//		else if (myName == "Scorpius"){value = "�������";}
//		else if (myName == "Sagittarius"){value = "���č�";}
//		else if (myName == "Capriconus"){value = "�€��";}
//		else if (myName == "Aquarius"){value = "�݂����ߍ�";}
//		else if (myName == "Pisces"){value = "������";}
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
