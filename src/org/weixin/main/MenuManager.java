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
 * 菜单管理器类
 * 
 * @date 2013-10-17
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	/**
	 * 定义菜单结构
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		

		ViewButton btn21 = new ViewButton();
		btn21.setName("芯片");
		btn21.setType("view");
		btn21
				.setUrl("http://www.rthitech.com.cn/products/Chip/SJK1127_Module.shtml");

		ViewButton btn22 = new ViewButton();
		btn22.setName("智能表(水/电/气/暖)");
		btn22.setType("view");
		btn22
				.setUrl("http://www.rthitech.com.cn/products/SmartMeter/EsamModule.shtml");

		ViewButton btn23 = new ViewButton();
		btn23.setName("RFID & IC卡读写机/标签");
		btn23.setType("view");
		btn23.setUrl("http://www.rthitech.com.cn/products/RFID/SL500.shtml");

		ViewButton btn24 = new ViewButton();
		btn24.setName("数据安全");
		btn24.setType("view");
		btn24
				.setUrl("http://www.rthitech.com.cn/products/DataSecurity/keySystem.shtml");

		ViewButton btn25 = new ViewButton();
		btn25.setName("用户充值(接口调试)");
		btn25.setType("view");
		btn25.setUrl("http://1.weixintest77.applinzi.com/");
		
		ClickButton btn26= new ClickButton();
		btn26.setName("账户查询(接口调试)");
		btn26.setType("click");
		btn26.setKey("userquery");

		ViewButton mainBtn1 = new ViewButton();
		mainBtn1.setName("公司简介");
		mainBtn1.setType("view");
		mainBtn1.setUrl("http://www.rthitech.com.cn/aboutRt/about.shtml");

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("产品介绍");
		mainBtn2
				.setSub_button(new Button[] { btn21, btn22, btn23, btn25 ,btn26});

		ViewButton mainBtn3 = new ViewButton();
		mainBtn3.setName("联系客服");
		mainBtn3.setType("view");
		mainBtn3.setUrl("http://www.rthitech.com.cn/contact/ContentUs.shtml");

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}

	public static void main(String[] args) {
		// 第三方用户唯一凭证  公司账户
		String appId = "wx31ac720aa5838480";
		// 第三方用户唯一凭证密钥
		String appSecret = "6820b4f14d6e3f8afafe094e99533a24";

//		// 第三方用户唯一凭证  个人账户 貌似不支持自定义菜单
//		String appId = "wxc8ee3e935b6bb515";
//		// 第三方用户唯一凭证密钥
//		String appSecret = "beddb90d51a6aa8627834af252322b25";
		
		
		// 调用接口获取凭证
		Token token = CommonUtil.getToken(appId, appSecret);

		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(getMenu(), token
					.getAccessToken());

			// 判断菜单创建结果
			if (result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败！");
		}
	}
}
