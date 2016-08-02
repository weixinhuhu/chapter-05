package org.course.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.course.message.resp.Article;
import org.course.message.resp.NewsMessage;
import org.course.message.resp.TextMessage;
import org.course.util.MessageUtil;

import org.sqlserver.util.SqlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 核心服务类
 * 
 * 
 * @date 2013-10-17
 */
public class CoreService {

	private static Logger log = (Logger) LoggerFactory
			.getLogger(CoreService.class);
	public static String fromUserName;
	public static String toUserName;
	public static String respXml;
	public static String usernum = "";

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */
	public static String processRequest(HttpServletRequest request) {

		// xml格式的消息数据
		respXml = null;
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号
			fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			// 事件推送
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					textMessage.setContent("您好，欢迎关注融通高科公众号！我们会竭诚为您服务！");
					// 将消息对象转换成xml
					respXml = MessageUtil.messageToXml(textMessage);
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 暂不做处理
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建菜单时的key值对应
					String eventKey = requestMap.get("EventKey");
					// 根据key值判断用户点击的按钮
					if (eventKey.equals("userquery")) {
						String SendMsg = getResult("11");
						if (SendMsg == "" || SendMsg == null) {
							textMessage.setContent("对不起，没用查到此用户信息");
							respXml = MessageUtil.messageToXml(textMessage);
						} else {
							newsMessage(SendMsg);
						}
					}
				}
			} else {
				// 获取接收到的消息内容
				String resmessage = requestMap.get("Content").toUpperCase()
						.trim();
				if (resmessage.contains("CX")) {
					if (resmessage.length() <= 2) {
						textMessage
								.setContent("请在CX后添加需要查询的用水户号！如编辑CX+11，即可查询用水户号为11的用水信息");
						respXml = MessageUtil.messageToXml(textMessage);
					} else {
						usernum = resmessage.replaceAll("[^0-9]", "");
						if (usernum.length() == 0) {
							textMessage.setContent("请输入合法的用水户号！");
							respXml = MessageUtil.messageToXml(textMessage);
						} else {
							String SendMsg = "";
							log.info("查询用水户号 " + usernum + " 请求的请求");
							SendMsg = getResult(usernum);
							if (SendMsg.length() == 0 || SendMsg.equals(null)
									|| SendMsg.equals("")) {
								textMessage.setContent("对不起，没用查到此用户信息");
								respXml = MessageUtil.messageToXml(textMessage);
							} else {
								newsMessage(SendMsg);
							}
						}
					}
				} else {
					textMessage.setContent("欢迎关注融通高科公众号！如需帮助可致电 010-62961992");
					respXml = MessageUtil.messageToXml(textMessage);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}

	/**
	 * 通过用水户号获取结果集
	 * 
	 * @param usernum
	 *            用水户号
	 * 
	 * @return
	 * @throws SQLException
	 */
	private static String getResult(String usernum) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs = null;
		// 调用存储过程
		rs = sqlutil.getResult_Pro(Integer.parseInt(usernum));
		StringBuffer msg = new StringBuffer();
		try {
			while (rs.next()) {
				msg.append("\n" + "账户余额：" + rs.getString("Income") + "元\n用水户号："
						+ rs.getString("CustomerId") + "\n用水户名："
						+ rs.getString("User_Name") + " \n用户地址："
						+ rs.getString("Address") + "\n更新日期："
						+ rs.getString("Fdate")
						+ "\n账户余额=充值金额-表端已用量*用水单价(用水量以每月账单为准)" + "\n\n\n立即缴费");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg.toString();
	}

	/**
	 * newsMessage封装
	 * 
	 * @param sendMsg
	 */
	private static void newsMessage(String sendMsg) {
		Article article = new Article();
		article.setTitle("查询结果详情");
		article.setDescription(sendMsg);
		article.setPicUrl("");
		article.setUrl("");
		List<Article> articleList = new ArrayList<Article>();
		articleList.add(article);
		// 创建图文消息
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		respXml = MessageUtil.messageToXml(newsMessage);
	}
}