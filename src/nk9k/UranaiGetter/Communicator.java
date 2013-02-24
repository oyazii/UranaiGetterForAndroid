package nk9k.UranaiGetter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.*;
import org.w3c.dom.*;

public class Communicator {

	private final static String cURL = "http://www.fujitv.co.jp/meza/uranai/uranai.xml";

	public static String GetUranai(EConstellations TargetType, Date TargetDate)
	{
		try
		{
			String result = "";
			// パラメータの生成
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			String urlParam = df.format(TargetDate);

//			URL url = new URL(cURL + "?" + urlParam);
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.setRequestMethod("GET");
//			con.connect();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document d = db.parse(cURL + "?" + urlParam);

			Element root = d.getDocumentElement();
			NodeList datelist =((Element)root.getElementsByTagName("date").item(0)).getChildNodes();
			// 占い日のチェック
			String dateValue = datelist.item(0).getNodeValue();

			DateFormat dateNodeFormat = new SimpleDateFormat("M月d日");
			String targetdateValue = dateNodeFormat.format(TargetDate);
			if (!dateValue.equals(targetdateValue))
			{
				return "本日の占いがありません。占い日:" + dateValue;
			}

			// 占い取得
			// ここで<Item></Item>Elementのリストが返る
			NodeList datalist = ((Element)root.getElementsByTagName("data").item(0)).getChildNodes();
			for (int idx = 0; idx <= datalist.getLength() - 1; idx++)
			{
				// 改行コードは無視
				Node n = datalist.item(idx);
				if (n.getNodeName().equals("#text")) continue;

				Element itemElement = (Element)n;
				UranaiItem uiObj = new UranaiItem(itemElement);

				int id = Integer.parseInt(uiObj.GetId());
				if (id != TargetType.GetID()) continue;

				// idが一致したら文字生成
				result = uiObj.GetUranaiString();
			}

//			con.disconnect();

			return result;
		}
		catch (Exception e)
		{
			return "読み込みでエラーがありました。" + e.getMessage();
		}
	}

}
