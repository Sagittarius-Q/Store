package com.example.store.service.user;

import com.example.store.domain.entity.User;
import com.example.store.domain.enums.Role;
import com.example.store.domain.model.service.UserServiceModel;
import com.example.store.exceptions.UserDuplicateException;
import com.example.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel,User.class);
        if (this.userRepository.count() == 0) {
            user.setRoles(new HashSet<>());
            user.getRoles().add(Role.ADMIN);
        } else {
            user.setRoles(new HashSet<>());
            user.getRoles().add(Role.MODERATOR);
        }
        user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));
        this.userRepository.save(user);
    }

    @Override
    public void existsByUsername(String username) {
        if(this.userRepository.existsByUsername(username))
            throw  new UserDuplicateException( username + " already exists , try different username" );
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(user == null){
            throw new UsernameNotFoundException("user is not found");
        }
        return user;
    }
}
