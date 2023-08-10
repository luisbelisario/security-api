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
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserDto create(UserData data) throws Exception {
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

    public UserDto updateById(Long id, UserData data) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado para o id informado"));
        User updatedUser = User.updateUser(user, data);
        userRepository.save(updatedUser);
        return UserDto.toDto(updatedUser);
    }

    public MessageDto reactivateById(Long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado para o id informado"));
        if(user.getDeletedAt() == null) {
            throw new GenericException("Cliente já está com a conta ativa");
        }
        user.setDeletedAt(null);
        userRepository.save(user);

        return MessageDto.toDto("Cliente reativado com sucesso");
    }

    public MessageDto deleteById(Long id) throws Exception {
        User client = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado para o id informado"));
        if(client.getDeletedAt() != null) {
            throw new GenericException("Cliente já está com a conta inativa");
        }
        client.setDeletedAt(LocalDate.now());
        userRepository.save(client);

        return MessageDto.toDto("Cliente excluído com sucesso");
    }
}
