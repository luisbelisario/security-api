package com.reservei.securityapi.securityapi.service;

import com.reservei.securityapi.securityapi.domain.dto.MessageDto;
import com.reservei.securityapi.securityapi.domain.dto.UserDto;
import com.reservei.securityapi.securityapi.domain.model.User;
import com.reservei.securityapi.securityapi.domain.record.UserData;
import com.reservei.securityapi.securityapi.exception.GenericException;
import com.reservei.securityapi.securityapi.exception.InactiveAccountException;
import com.reservei.securityapi.securityapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserDto create(UserData data) throws GenericException {
        if (userRepository.findByLogin(data.login()) != null) throw new GenericException("Usuário já cadastrado");
        User user = User.toUser(data);
        return UserDto.toDto(userRepository.save(user));
    }

    public UserDto findById(Long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado para o id informado"));
        if (user.getDeletedAt() != null) {
            throw new InactiveAccountException("Cliente com a conta inativa");
        }
        return UserDto.toDto(user);
    }

    public UserDto updateByPublicId(String publicId, UserData data) {
        User user = userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado para o public id informado"));
        User updatedUser = User.updateUser(user, data);
        userRepository.save(updatedUser);
        return UserDto.toDto(updatedUser);
    }

    public MessageDto reactivateById(String publicId) throws Exception {
        User user = userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado para o public id informado"));
        if(user.getDeletedAt() == null) {
            throw new GenericException("Usuário já está com a conta ativa");
        }
        user.setDeletedAt(null);
        userRepository.save(user);

        return MessageDto.toDto("Usuário reativado com sucesso");
    }

    public MessageDto deleteById(String publicId) throws Exception {
        User user = userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado para o id informado"));
        if(user.getDeletedAt() != null) {
            throw new GenericException("Usuário já está com a conta inativa");
        }
        user.setDeletedAt(LocalDate.now());
        userRepository.save(user);

        return MessageDto.toDto("Usuário excluído com sucesso");
    }

    public UserDetails findByLogin(String login) throws GenericException {
        UserDetails user = userRepository.findByLogin(login);
        if (user == null) {
            throw new GenericException("Usuário não encontrado para os dados informados");

        }
        return user;
    }
}
