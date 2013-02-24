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
			// �p�����[�^�̐���
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
			// �肢���̃`�F�b�N
			String dateValue = datelist.item(0).getNodeValue();

			DateFormat dateNodeFormat = new SimpleDateFormat("M��d��");
			String targetdateValue = dateNodeFormat.format(TargetDate);
			if (!dateValue.equals(targetdateValue))
			{
				return "�{���̐肢������܂���B�肢��:" + dateValue;
			}

			// �肢�擾
			// ������<Item></Item>Element�̃��X�g���Ԃ�
			NodeList datalist = ((Element)root.getElementsByTagName("data").item(0)).getChildNodes();
			for (int idx = 0; idx <= datalist.getLength() - 1; idx++)
			{
				// ���s�R�[�h�͖���
				Node n = datalist.item(idx);
				if (n.getNodeName().equals("#text")) continue;

				Element itemElement = (Element)n;
				UranaiItem uiObj = new UranaiItem(itemElement);

				int id = Integer.parseInt(uiObj.GetId());
				if (id != TargetType.GetID()) continue;

				// id����v�����當������
				result = uiObj.GetUranaiString();
			}

//			con.disconnect();

			return result;
		}
		catch (Exception e)
		{
			return "�ǂݍ��݂ŃG���[������܂����B" + e.getMessage();
		}
	}

}
