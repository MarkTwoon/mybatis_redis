package com.chinasoft.mybatis_redis.util;

import com.chinasoft.mybatis_redis.exception.MyException;
import com.chinasoft.mybatis_redis.exception.StatusCode;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestUtil {
	public static List<Map<String,Object>> checkNullData(List<Map<String,Object>> list){
		if(list.size()<=0){
			throw new MyException(StatusCode.DATA_NULL,"查询数据为空！！");
		}
		return  list;
	}


	public static Map<String, Object> getQueryMap(HttpServletRequest request) {
		Map<String, Object> bm = new HashMap<String, Object>();
		Map<String, String[]> tmp = request.getParameterMap();
		
		if (tmp != null) {
			for (String key : tmp.keySet()) {
				Object[] values = tmp.get(key);
				Object o = values.length == 1 ? values[0].toString().trim()
						: values;
				bm.put(key, isType(o));
			}
		}
		return bm;
	}

	//将字符串转化成数值类型
	public static Object isType(Object o) {
		Object object = o;
		try {
			object = Integer.parseInt(o.toString());
		} catch (Exception e) {
			try {
				object = Double.parseDouble(o.toString());
			} catch (Exception e1) {
				object = o.toString();
			}

		}
		return object;
	}
/*验证码的请求方法  打开页面 刷新加载出验证码内容*/
public static void GeetestOnLoad(HttpServletRequest request, HttpServletResponse response){
	try {
     /*极验验证码的 使用请求id 与 秘钥 实例化过程*/
  GeetestLib gtsdk=new GeetestLib(GeetestConfig.getGeetest_id(),GeetestConfig.getGeetest_key(),GeetestConfig.isNewfailback());
  String resStr="{}";
  String userid="test";
  HashMap<String,String> param=new HashMap<String,String>();
		/*请求传递参数*/
		param.put("user_id", "99909090");
		param.put("client_type", "web");
		param.put("ip_address","127.0.0.1");
		int gtServerStatus=gtsdk.preProcess(param);
		request.getSession().setAttribute(gtsdk.gtServerStatusSessionKey , gtServerStatus);
		request.getSession().setAttribute("userid", userid);
		resStr=gtsdk.getResponseStr();
		/*返回并页面输出 验证码*/
		PrintWriter out;
		out = response.getWriter();
		out.println(resStr);
	}catch (Exception e){
		e.printStackTrace();
	}
}

/*使用验证码 进行登录的极验验证 滑动结果判断*/
 public static JSONObject GeetestBy
 (HttpServletRequest request,HttpServletResponse response){
	 JSONObject data=new JSONObject();
   try {
   	GeetestLib gtSdk=new
			GeetestLib(GeetestConfig.getGeetest_id(),GeetestConfig.getGeetest_key(),GeetestConfig.isNewfailback());
	   String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
	   String validate=request.getParameter(GeetestLib.fn_geetest_validate);
	   String seccode=request.getParameter(GeetestLib.fn_geetest_seccode);
	   //从session中获取gt-server状态
	   int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
	   //从session中获取userid
	   String userid = (String)request.getSession().getAttribute("userid");

	   //自定义参数,可选择添加
	   HashMap<String, String> param = new HashMap<String, String>();
	   param.put("user_id", userid); //网站用户id
	   param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
	   param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

	   int gtResult = 0;
	   if (gt_server_status_code == 1) {
		   //gt-server正常，向gt-server进行二次验证
		   gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
	   } else {
		   // gt-server非正常情况下，进行failback模式验证
		   gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
	   }
	   System.out.println(gtResult);
	   if (gtResult == 1) {
		   // 验证成功
		   //	PrintWriter out = response.getWriter();

		   try {
			   data.put("status", "success");
			   data.put("version", gtSdk.getVersionInfo());
		   } catch (JSONException e) {
			   e.printStackTrace();
		   }
		   //	out.println(data.toString());
	   }else {
		   // 验证失败
		   //JSONObject data = new JSONObject();
		   try {
			   data.put("status", "fail");
			   data.put("version", gtSdk.getVersionInfo());
		   } catch (JSONException e) {
			   e.printStackTrace();
		   }
		/*PrintWriter out = response.getWriter();
		out.println(data.toString());*/
	   }

   }catch (Exception e){
	   System.out.println("IO流异常");
   	e.printStackTrace();
   }

   return  data;
 }


}


