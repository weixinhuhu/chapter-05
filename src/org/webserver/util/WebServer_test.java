package org.webserver.util;

import java.rmi.RemoteException;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

public class WebServer_test {
    
    public String invokeRemoteFuc() {
        // Զ�̵���·��
        String endpoint = "http://localhost:8080/WebServiceTest/services/HelloWorld";
        String result = "call failed!";
        Service service = new Service();
        Call call;
        
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint); 
            // ���õķ�����
            call.setOperationName("printStr"); 
            
            // ���ò�����
            call.addParameter("name",   // ������
                    XMLType.XSD_STRING, // ��������:String
                    ParameterMode.IN);  // ����ģʽ��'IN' or 'OUT'

            // ���÷���ֵ����
            call.setReturnType(XMLType.XSD_STRING); // ����ֵ���ͣ�String
            String name = "Alexia";
            result = (String) call.invoke(new Object[] { name });// Զ�̵���
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }       
        return result;
    }

    
    // ����
    public static void main(String[] args) {
        WebServer_test test = new WebServer_test();
        String result = test.invokeRemoteFuc();
        System.out.println(result);
    }

}