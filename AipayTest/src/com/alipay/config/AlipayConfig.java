package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016102300743490";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCND8jw0U4nhx04v23rMTu1DVw9OvRtR8bhKWuzebQlWyuV1jc3rqIWpAgsefRQqYX3vcOXGzSwMe973JMdO8CbPasR9E7lVwMH+bssWWhEfCVTE+5A5URsbWjhlaC461MrR6Fsc3TFm48EBt8oRTahD9zUGHvx1d/LHmUL0vglQx/BpODb5lczgSTZpnsrOXRaHkjei3WWF/RjAuj34a9/7p0ZRY3yvR1McG9E/aXttomKL7addm4Ze+sXI1dyZGFAhij+MLgfjs94tYr7FqWWF+2xEQuI3TaDvtL/msq1MhmouFsRvkHllhf+sGzSHBl+93IpE/tAGjyNcIsK99TPAgMBAAECggEAJ/hTDbmhFZ45b3z+nmgC7RhuDIOKAvUVq3NbKy3PF86vmPNsDgETuKqeDkw4KCsA5OQGaeixk0NzAnvLy7rEprhgouPA/0caesYOfVCQNOegg9vf5M+SBc8yqskFE6DKnjbbzKkia2PasF9qGDUUd6rQ/eA6LppVoW5pX2JWIehpgesBJBl0vcJ+1ur7pKrU88NiTdIZfGvTTQ6xcqsY3Rtpo1atmR8EA5OkD/N6gR0Z7+NfFgMFsC76ZcOcmbYGIE324Yz8sEYt5ZfoV4a40R7QCPQ4BFMcziOiJ1KAfg2pRP2ssFSv/W6C84SIqfQbrUz7YHAsoa72TS7IGOAR4QKBgQDAR7L+zUpoTHtSc5awPJRqbY1wj25/k6uU2C/5AZhVzjHxjnZuSvXOPg3W2pS/ZG8RjrTtCLn7Tmj2xOYaxDveQ/wxRBY2acGpiwIMu61/cipowPf32owYdkOdMA3aNNDh4CXDEJbyjNi3MHLSDcfV6QO66T8Z6oSvbxdX2HaWSQKBgQC7zumm9Nln3mdHQRA54ldgIang+jciwCa7V7v9s45sTViMWUJNyPdLfcc1TWIqtkfzUqt3CnbcydoKAnmkDfzXRH+47RW0ujNobwqCXRclpW+AlbcvaQO1gy1mWcjFl8IV3JqfFxvkF6oZue16zhQqa9drE+OWrE07VwUsJMyyVwKBgEaHRjHOTXe6bhXNTj2wyuPK8XGTOv3SxcIgFmCo5B69NnabQfesyULwYhUoMh2OEmuHQ/gaFlYG6dtyV9EDn84kc9ICXn9xBVdSJNtU0AwKbWHlPk+Mu4yRfLWglcQvUVs448EMScIIRcnUHJnN8joVcYfS3JF+A1NyVUakoML5AoGAPnu8qNtVaYVI/x/acienwkfw6PTWWy1qtXGfDe0rOUUSbL9Vm+N8wxT5rfYs7uDuVznt6hEFMrfFXwcryTUOJeCYTcIzMomwiNJIwM6P4OqFilZFN/TMUxttg1vjvDmdTQC0QLHDicOFyEFfjTf65ZIzfWCEm6B/oXp2MhWcNMkCgYEAoT43ykQYWNsfCTQuH2clGK78cLzdO4ExtE25xmKT34tuJ8QKc5jB5i+8J3NXH0FxAG78I6QGEN6tMLzSfPEhiG+ZKvf2f0yR+vQBkMvBuXV7sQVl8CAIBSYJ/bkXKgYuxR4VY1rSj2o54ZciD+lg7Kw5Aymc7LKmCD9Ts/9GuJw=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiRje5XlwkrA2nFKsAw0rvqwZwSKCms1SaRtwU56PqrqBgUk65QFpBgxxdzeaHi0pGFX0yHNoxk6zw09s+SUkumnfAN7j45CvbDh9A02Q+lxmKTtIcBrgS20HHmMyxnEQXz0+Tk9xoByOvFMdsbD8xP8SMm8A67HDa88MlesUd8oO+bSsjPk2GqoYtM+6ajCUOuIGNoKJJyKVwOWSxkO70JPB8RPHv5azinR+1DRybhc8tENALR/A72JkV4wNs4XDYp9acotZHw2Xcy1M0FmgbnC0Fb2qODn4resWfgCyUvA7AanSTUURMqs1wxGcN1QIGv2nL+XRfhXamz7UMU+WzQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "F:\\大数据视频\\第三方支付\\支付宝支付\\log";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

