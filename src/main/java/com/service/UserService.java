package com.service;

import com.entity.Role;
import com.entity.User;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailSender mailSender;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
    public boolean addUser(User user){
        User userDB = userRepository.findByUsername(user.getUsername());
        if(userDB != null) return false;

        user.setActive(true);
        // устанавливаем права USER по умолчанию
        user.setRoles(Collections.singleton(Role.USER));
        // генерируем и устанавливаем код активации
        user.setActivationCode(UUID.randomUUID().toString());
        //сохраняем пользователя
        userRepository.save(user);
        //проверка что пользователь ввел почтовый адрес
        if(!StringUtils.isEmpty(user.getEmail())){
            //формируем строку приветствия
            String message = String.format(
                    "Hello, %s! \n" +
                            "Plese visit to http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode());
            //производим отправку письма
            mailSender.send(user.getEmail(),"Activation code",message);
        }

        return true;
    }

    public boolean activateUser(String code) {
        User user =  userRepository.findByActivationCode(code);
        // если пользователь по коду не найден, то возвращаем false
        if(user == null) return false;
        //если найден, то устанавливаем в поле activationCode значение null, т.е. пользователь подтвердил email
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }
}
