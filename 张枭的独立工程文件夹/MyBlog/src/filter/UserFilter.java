package filter;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 检查session，确定用户是否登录
 */
//筛选规则为全筛选
@WebFilter( dispatcherTypes = {DispatcherType.REQUEST }
					, urlPatterns = { "/*" })
public class UserFilter implements Filter {
	//不过滤的URL
	private final String noLoginPaths="error.jsp;Login_Test;index.html";
	
    public UserFilter() {

    }


	public void destroy() {

	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//类型强转，检查session中的“username”属性是否为空，为空则跳转到error.jsp页面
	HttpServletRequest request2=(HttpServletRequest) request;
	HttpServletResponse response2=(HttpServletResponse) response;
	HttpSession session=request2.getSession();
	
		if(noLoginPaths!=null){
			//分割数组
			String[] strArray=noLoginPaths.split(";");
			for(int i=0;i<strArray.length;i++){
				//验证字符数组的安全性
				if(strArray[i]==null||"".equals(strArray[i])) continue;
				
				if(request2.getRequestURI().indexOf(strArray[i])!=-1 	){
					chain.doFilter(request, response);
					return;
				}
			}
		}
	
		if(session.getAttribute("username")!=null){
			chain.doFilter(request, response);
		}else{
			response2.sendRedirect("error.jsp");//请求重定向（前后不使用同一个request）
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
