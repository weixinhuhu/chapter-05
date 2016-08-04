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
 * ���ķ�����
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
	 * ����΢�ŷ���������
	 * 
	 * @param request
	 * @return xml
	 */
	public static String processRequest(HttpServletRequest request) {

		// xml��ʽ����Ϣ����
		respXml = null;
		try {
			// ����parseXml��������������Ϣ
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// ���ͷ��ʺ�
			fromUserName = requestMap.get("FromUserName");
			// ������΢�ź�
			toUserName = requestMap.get("ToUserName");
			// ��Ϣ����
			String msgType = requestMap.get("MsgType");

			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			// �¼�����
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// �¼�����
				String eventType = requestMap.get("Event");
				// ����
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					textMessage.setContent("���ã���ӭ��ע��ͨ�߿ƹ��ںţ����ǻ�߳�Ϊ������");
					// ����Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(textMessage);
				}
				// ȡ������
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO �ݲ�������
				}
				// �Զ���˵�����¼�
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// �¼�KEYֵ���봴���˵�ʱ��keyֵ��Ӧ
					String eventKey = requestMap.get("EventKey");
					// ����keyֵ�ж��û�����İ�ť
					if (eventKey.equals("userquery")) {
						String SendMsg = getResult("11");
						if (SendMsg == "" || SendMsg == null) {
							textMessage.setContent("�Բ���û�ò鵽���û���Ϣ");
							respXml = MessageUtil.messageToXml(textMessage);
						} else {
							newsMessage(SendMsg);
						}
					}
				}
			} else {
				// ��ȡ���յ�����Ϣ����
				String resmessage = requestMap.get("Content").toUpperCase()
						.trim();
				if (resmessage.contains("CX")) {
					if (resmessage.length() <= 2) {
						textMessage
								.setContent("����CX�������Ҫ��ѯ����ˮ���ţ���༭CX+11�����ɲ�ѯ��ˮ����Ϊ11����ˮ��Ϣ");
						respXml = MessageUtil.messageToXml(textMessage);
					} else {
						usernum = resmessage.replaceAll("[^0-9]", "");
						if (usernum.length() == 0) {
							textMessage.setContent("������Ϸ�����ˮ���ţ�");
							respXml = MessageUtil.messageToXml(textMessage);
						} else {
							String SendMsg = "";
							log.info("��ѯ��ˮ���� " + usernum + " ���������");
							SendMsg = getResult(usernum);
							if (SendMsg.length() == 0 || SendMsg.equals(null)
									|| SendMsg.equals("")) {
								textMessage.setContent("�Բ���û�ò鵽���û���Ϣ");
								respXml = MessageUtil.messageToXml(textMessage);
							} else {
								newsMessage(SendMsg);
							}
						}
					}
				} else {
					textMessage.setContent("��ӭ��ע��ͨ�߿ƹ��ںţ�����������µ� 010-62961992");
					respXml = MessageUtil.messageToXml(textMessage);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}

	/**
	 * ͨ����ˮ���Ż�ȡ�����
	 * 
	 * @param usernum
	 *            ��ˮ����
	 * 
	 * @return
	 * @throws SQLException
	 */
	private static String getResult(String usernum) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs = null;
		// ���ô洢����
		rs = sqlutil.getResult_Pro(Integer.parseInt(usernum));
		StringBuffer msg = new StringBuffer();
		try {
			while (rs.next()) {
				msg.append("\n" + "�˻���" + rs.getString("Income") + "Ԫ\n��ˮ���ţ�"
						+ rs.getString("CustomerId") + "\n��ˮ������"
						+ rs.getString("User_Name") + " \n�û���ַ��"
						+ rs.getString("Address") + "\n�������ڣ�"
						+ rs.getString("Fdate")
						+ "\n�˻����=��ֵ���-���������*��ˮ����(��ˮ����ÿ���˵�Ϊ׼)" + "\n\n\n�����ɷ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg.toString();
	}

	/**
	 * newsMessage��װ
	 * 
	 * @param sendMsg
	 */
	private static void newsMessage(String sendMsg) {
		Article article = new Article();
		article.setTitle("��ѯ�������");
		article.setDescription(sendMsg);
		article.setPicUrl("");
		article.setUrl("");
		List<Article> articleList = new ArrayList<Article>();
		articleList.add(article);
		// ����ͼ����Ϣ
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