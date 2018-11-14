package com.adrian.security;

import com.adrian.domain.SysUser;
import com.adrian.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wangyunfei
 * @date 2017/6/9
 */
@Transactional(rollbackFor = Exception.class)
@Service("userDetailsService")
@Slf4j
public class DomainUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("当前登录的用户是:" + userName);
        String lowcaseUsername = userName.toLowerCase();
        SysUser sysUser = sysUserRepository.findOneWithRolesByUserName(userName);
        if (sysUser == null) {
            log.info("找不到" + lowcaseUsername + "该账户信息!");
            throw new UsernameNotFoundException("找不到" + lowcaseUsername + "该账户信息!");
        }
        return new UserDetailsImpl(sysUser);
    }
}
