package org.webserver.util;

import java.rmi.RemoteException;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

public class WebServer_test {
    
    public String invokeRemoteFuc() {
        // 远程调用路径
        String endpoint = "http://localhost:8080/WebServiceTest/services/HelloWorld";
        String result = "call failed!";
        Service service = new Service();
        Call call;
        
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint); 
            // 调用的方法名
            call.setOperationName("printStr"); 
            
            // 设置参数名
            call.addParameter("name",   // 参数名
                    XMLType.XSD_STRING, // 参数类型:String
                    ParameterMode.IN);  // 参数模式：'IN' or 'OUT'

            // 设置返回值类型
            call.setReturnType(XMLType.XSD_STRING); // 返回值类型：String
            String name = "Alexia";
            result = (String) call.invoke(new Object[] { name });// 远程调用
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }       
        return result;
    }

    
    // 测试
    public static void main(String[] args) {
        WebServer_test test = new WebServer_test();
        String result = test.invokeRemoteFuc();
        System.out.println(result);
    }

}