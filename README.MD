### axios 사용하기
- 다운로드 주소 https://axios-http.com/kr/docs/intro
#### CDN
```javascript
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
```

#### 로그인 패싱을 배움
```java
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 사용자가 요청한 주소 확인
        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);
        // 세션객체 생성
        HttpSession session = request.getSession();
        // 세션에 저장된 로그인 정보 확인
        if (session.getAttribute("memberEmail") == null) {
            // 로그인하지 않았다면 로그인페이지로 보내면서
            // 요청한 주소값도 같이 보냄
            response.sendRedirect("/member/login?requestURI="+requestURI);
            return false;
        }else {
            //로그인 상태라면 요청한 페이지로 보냄
            return true;
        }
    }
}
```