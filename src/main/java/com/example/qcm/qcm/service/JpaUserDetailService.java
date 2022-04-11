package com.example.qcm.qcm.service;

import com.example.qcm.qcm.entity.Role;
import com.example.qcm.qcm.entity.User;
import com.example.qcm.qcm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class JpaUserDetailService  implements UserDetailsService {
    @Autowired
    private UserRepository userDao;


    @Override
    @Transactional()
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException ("Utilisateur introuvable : |"+username+"|");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
//        for(Role grp: user.getRoles()){
//            authorities.add(new SimpleGrantedAuthority(grp.getUsername().name()));
//        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername (),
                user.getPassword(),
                authorities);
    }
}
