package fyordo.lifeagragator.food.base.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WorkspaceUtils {
    public static Long getUserId(){
        RequestAttributes requestAttributes = RequestContextHolder
                .currentRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        HttpSession httpSession = request.getSession(true);

        return Long.valueOf((String)httpSession.getAttribute("userId"));
    }
}
