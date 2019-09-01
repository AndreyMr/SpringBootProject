package com.service;

import com.entity.Role;
import com.entity.User;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }
    public boolean addUser(User user){
        User userDB = userRepository.findByUsername(user.getUsername());
        if(userDB != null) return false;
        // при добавлении нового пользователя до активации по email активацию ставим false
        user.setActive(false);
        // устанавливаем права USER по умолчанию
        user.setRoles(Collections.singleton(Role.USER));
        // генерируем и устанавливаем код активации
        user.setActivationCode(UUID.randomUUID().toString());
        //шифруем пароль перед добавлением пользователя в базу
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //сохраняем пользователя
        userRepository.save(user);
        sendActivationCodeEmail(user);


        return true;
    }

    private void sendActivationCodeEmail(User user) {
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
    }

    public boolean activateUser(String code) {
        User user =  userRepository.findByActivationCode(code);
        // если пользователь по коду не найден, то возвращаем false
        if(user == null) return false;
        //если найден, то устанавливаем в поле activationCode значение null, т.е. пользователь подтвердил email
        user.setActivationCode(null);
        //активируем пользователя
        user.setActive(true);
        userRepository.save(user);
        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(String username, User user, Map<String,String> form) {
        user.setUsername(username);
        //получаем список ролей переведенных из enum в строковый вид
        Set<String> collect = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        // очищаем пользователя от всех текущих ролей
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if(collect.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public void updateUser(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChange = (email != null && !email.equals(userEmail))
                || (userEmail != null && !userEmail.equals(email));
        if (isEmailChange){
            user.setEmail(email);

            if(!StringUtils.isEmpty(email)){
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if(!StringUtils.isEmpty(password)) user.setPassword(password);

        userRepository.save(user);
        if(isEmailChange) sendActivationCodeEmail(user);
    }
}
