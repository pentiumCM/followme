package com.cl.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;

/**
 * 通过腾讯云 指定模板发送短信
 * 
 * @author: dylan
 * @date: 2019年4月18日 下午11:47:32
 */
@Configuration
public class QcloudSmsUtil {
	/**
	 * 短信应用SDK AppID
	 */
	private int appid;

	/**
	 * 短信应用SDK AppKey
	 */
	private String appkey;

	/**
	 * 签名使用的是`签名内容`，而不是`签名ID`
	 */
//	@Value("${smsSign}")
	private String smsSign;

	/**
	 * 自定义短信验证码
	 */
	private String code = "";
	
	/**
	 * 验证码长度
	 */
	private int codeLength;

	/**
	 * 资源文件路径
	 */
	private String qcloudSmsProperties = "resource/qcloudSms.properties";

	PropertiesUtil pUtil = new PropertiesUtil();

	public QcloudSmsUtil() {
		// 读取腾讯云短信配置文件
		try {
			Properties smsProp = pUtil.readProperties(qcloudSmsProperties);
			this.appid = Integer.parseInt(smsProp.getProperty("appid"));
			this.appkey = smsProp.getProperty("appkey");
			this.smsSign = smsProp.getProperty("smsSign");
			this.codeLength = Integer.parseInt(smsProp.getProperty("codeLength"));
//			System.out.println("短信配置-smsSign：" + smsSign);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自定义验证码
	 * 
	 * @return
	 */
	public String smsCode() {
		for (int i = 0; i < codeLength; i++) {
			code += (int) (Math.random() * 9 + 1);
		}
		return code;
	}

	/**
	 * 指定模板ID单发登录，注册，忘记密码短信
	 * 
	 * @param phoneNumbers 不带国家码的手机号列表
	 * @param phoneType    短信发送类型:registerPhone forgetPwdPhone loginPhone
	 * @return 返回错误码 返回0代表成功
	 * @throws Exception 读取读取腾讯云短信配置文件异常
	 */
	public int sendQcloudSms(String[] phoneNumbers, String phoneType) throws Exception {
		System.out.println("短信模板：" + phoneType);
		Properties p = pUtil.readProperties(qcloudSmsProperties);
		// 短信模板ID，需要在短信应用中申请
		// 真实的模板ID需要在短信控制台中申请
		int phoneTypeId = 0;
		// 自定义验证码
		String code = smsCode();
		// 自定义短信失效时间
		String qMsgFailureTime = "";
		if (phoneType.equals("registerPhone")) {
			phoneTypeId = Integer.parseInt(p.getProperty("registerTemplateId"));
			qMsgFailureTime = p.getProperty("registerFailureTime");
		} else if (phoneType.equals("forgetPwdPhone")) {
			phoneTypeId = Integer.parseInt(p.getProperty("forgetPwdTemplateId"));
			qMsgFailureTime = p.getProperty("forgetPwdFailureTime");
		} else if (phoneType.equals("loginPhone")) {
			phoneTypeId = Integer.parseInt(p.getProperty("loginTemplateId"));
			qMsgFailureTime = p.getProperty("loginFailureTime");
		}
		SmsSingleSenderResult result = null; // 短信发送返回信息
		try {
			// params 为短信模板中{1}{2}的具体内容
			String[] params = { code, qMsgFailureTime };
			SmsSingleSender msender = new SmsSingleSender(appid, appkey);
			/**
			 * @param nationCode  国家码，如 86 为中国
			 * @param phoneNumber 不带国家码的手机号d
			 * @param phoneTypeId 短信正文id
			 * @param params      信息内容
			 * @param params      模板参数列表，如模板 {1}...{2}...{3}，那么需要带三个参数
			 * @param smsSign     签名，如果填空，系统会使用默认签名
			 */
			result = msender.sendWithParam("86", phoneNumbers[0], phoneTypeId, params, smsSign, "", "");
			System.out.println(result);
			System.out.println("***********发送结束***********");
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
			return -1;
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
			return -2;
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
			return -3;
		}
		// 获取返回值
		int a = result.result;

		if (a == 0) {
			// 发送成功时，将验证码和有效时间放入session中
			if (phoneType.equals("registerPhone")) {
				setSession("registerSmsCode", code, "registerFailureTime", qMsgFailureTime);
			} else if (phoneType.equals("forgetPwdPhone")) {
				setSession("forgetPwdSmsCode", code, "forgetPwdFailureTime", qMsgFailureTime);
			} else if (phoneType.equals("loginPhone")) {
				setSession("loginSmsCode", code, "loginFailureTime", qMsgFailureTime);
			} else {
				System.out.println("phoneType不存在");
			}
		}
		return a;
	}

	/**
	 * 短信发送成功时
	 * 
	 * @param codeStr 短信验证码 key
	 * @param code    短信验证码 value
	 * @param timeStr 短信有效时间 key
	 * @param time    短信有效时间 value
	 */
	private void setSession(String codeStr, String code, String timeStr, String time) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		request.getSession().setAttribute(codeStr, code);
		request.getSession().setAttribute(timeStr, time);
		System.out.println(request.getSession().getAttribute(codeStr));
	}
}