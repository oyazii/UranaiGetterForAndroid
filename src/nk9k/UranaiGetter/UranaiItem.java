package nk9k.UranaiGetter;

import org.w3c.dom.Element;

public class UranaiItem {

	final String TAG_Rank = "rank";
	final String TAG_Id = "id";
	final String TAG_Text = "text";
	final String TAG_Advice = "advice";
	final String TAG_Point = "point";

	public UranaiItem(Element ItemElement)
	{
		_Rank = ItemElement.getElementsByTagName(TAG_Rank).item(0).getChildNodes().item(0).getNodeValue();
		_Id = ItemElement.getElementsByTagName(TAG_Id).item(0).getChildNodes().item(0).getNodeValue();
		_Text = ItemElement.getElementsByTagName(TAG_Text).item(0).getChildNodes().item(0).getNodeValue();
		if (_Rank.equals("1��") || _Rank.equals("12��"))
		{
			_Advice = ItemElement.getElementsByTagName(TAG_Advice).item(0).getChildNodes().item(0).getNodeValue();
		}
		_Point = ItemElement.getElementsByTagName(TAG_Point).item(0).getChildNodes().item(0).getNodeValue();
	}

	private String _Rank;
	public String GetRank()
	{
		return _Rank;
	}

	private String _Id;
	public String GetId()
	{
		return _Id;
	}

	private String _Text;
	public String GetText()
	{
		return _Text.replace(",", "");
	}

	private String _Advice;
	public String GetAdvice()
	{
		return _Advice;
	}

	private String _Point;
	public String GetPoint()
	{
		return _Point;
	}

	public String GetUranaiString()
	{
		String result;

		EConstellations Seiza = EConstellations.GetFromID(
				Integer.parseInt(this.GetId()));

		result = Seiza.GetJPName() + this.GetRank();
		if (GetRank().equals("12��"))
		{
			result += "�c";
		}
		else
		{
			result += "�I";
		}

		result += this.GetText();
		if (GetRank().equals("1��"))
		{
			result += "�A�h�o�C�X��" + this.GetAdvice() + "�B";
		}
		else if (GetRank().equals("12��"))
		{
			result += "���܂��Ȃ���" + this.GetAdvice() + "�B";
		}

		result += "���b�L�[�|�C���g��" + this.GetPoint() + "�B";
		result += "�����Ă�����Ⴂ�I";

		return result;
	}
}
