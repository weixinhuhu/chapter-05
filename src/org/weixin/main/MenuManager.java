package org.weixin.main;

import org.course.menu.Button;
import org.course.menu.ClickButton;
import org.course.menu.ComplexButton;
import org.course.menu.Menu;
import org.course.menu.ViewButton;
import org.course.pojo.Token;
import org.course.util.CommonUtil;
import org.course.util.MenuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * �˵���������
 * 
 * @date 2013-10-17
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	/**
	 * ����˵��ṹ
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		

		ViewButton btn21 = new ViewButton();
		btn21.setName("оƬ");
		btn21.setType("view");
		btn21
				.setUrl("http://www.rthitech.com.cn/products/Chip/SJK1127_Module.shtml");

		ViewButton btn22 = new ViewButton();
		btn22.setName("���ܱ�(ˮ/��/��/ů)");
		btn22.setType("view");
		btn22
				.setUrl("http://www.rthitech.com.cn/products/SmartMeter/EsamModule.shtml");

		ViewButton btn23 = new ViewButton();
		btn23.setName("RFID & IC����д��/��ǩ");
		btn23.setType("view");
		btn23.setUrl("http://www.rthitech.com.cn/products/RFID/SL500.shtml");

		ViewButton btn24 = new ViewButton();
		btn24.setName("���ݰ�ȫ");
		btn24.setType("view");
		btn24
				.setUrl("http://www.rthitech.com.cn/products/DataSecurity/keySystem.shtml");

		ViewButton btn25 = new ViewButton();
		btn25.setName("�û���ֵ(�ӿڵ���)");
		btn25.setType("view");
		btn25.setUrl("http://1.weixintest77.applinzi.com/");
		
		ClickButton btn26= new ClickButton();
		btn26.setName("�˻���ѯ(�ӿڵ���)");
		btn26.setType("click");
		btn26.setKey("userquery");

		ViewButton mainBtn1 = new ViewButton();
		mainBtn1.setName("��˾���");
		mainBtn1.setType("view");
		mainBtn1.setUrl("http://www.rthitech.com.cn/aboutRt/about.shtml");

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("��Ʒ����");
		mainBtn2
				.setSub_button(new Button[] { btn21, btn22, btn23, btn25 ,btn26});

		ViewButton mainBtn3 = new ViewButton();
		mainBtn3.setName("��ϵ�ͷ�");
		mainBtn3.setType("view");
		mainBtn3.setUrl("http://www.rthitech.com.cn/contact/ContentUs.shtml");

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}

	public static void main(String[] args) {
		// �������û�Ψһƾ֤  ��˾�˻�
		String appId = "wx31ac720aa5838480";
		// �������û�Ψһƾ֤��Կ
		String appSecret = "6820b4f14d6e3f8afafe094e99533a24";

//		// �������û�Ψһƾ֤  �����˻� ò�Ʋ�֧���Զ���˵�
//		String appId = "wxc8ee3e935b6bb515";
//		// �������û�Ψһƾ֤��Կ
//		String appSecret = "beddb90d51a6aa8627834af252322b25";
		
		
		// ���ýӿڻ�ȡƾ֤
		Token token = CommonUtil.getToken(appId, appSecret);

		if (null != token) {
			// �����˵�
			boolean result = MenuUtil.createMenu(getMenu(), token
					.getAccessToken());

			// �жϲ˵��������
			if (result)
				log.info("�˵������ɹ���");
			else
				log.info("�˵�����ʧ�ܣ�");
		}
	}
}
