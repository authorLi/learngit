package com.mycclee.blog.realm;

import com.mycclee.blog.beans.User;
import com.mycclee.blog.beans.UserExample;
import com.mycclee.blog.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm {

    //设置加密算法
    private static final String Algorithm = "MD5";

    @Autowired
    private UserMapper userMapper;

//    //将加密算法加入到shiro中
//    @PostConstruct//此注解只在服务器加载servlet时运行，他会在servlet的init()方法之前执行
//    public void initCredentialsMatcher(){
//        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Algorithm);
//        setCredentialsMatcher(matcher);
//    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String) super.getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = getOnlyOneUserByUsername(username);
        Set<String> roles = new HashSet<>(Arrays.asList(user.getRoles().split("/")));
        info.setRoles(roles);
        return info;
//        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String) token.getPrincipal();
        User user = getOnlyOneUserByUsername(username);
        if(user == null){
            //没有此账号
//            throw new UnknownAccountException();
            return null;
        }
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.getSession().setAttribute("username",username);
        currentUser.getSession().setAttribute("isAdmin",user.getRoles().contains("admin"));
        String password = new String((char[]) token.getCredentials());
        if (password != null && password.equals(user.getPassword())){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password, ByteSource.Util.bytes(username),getName());
            return info;
        }

        return null;
    }

    private User getOnlyOneUserByUsername(String username){
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        return users.get(0);
    }
}
