package com.swd392.ticket_resell_be.services.impls;

import com.nimbusds.jose.JOSEException;
import com.swd392.ticket_resell_be.dtos.requests.LoginDtoRequest;
import com.swd392.ticket_resell_be.dtos.responses.ApiItemResponse;
import com.swd392.ticket_resell_be.dtos.responses.ApiListResponse;
import com.swd392.ticket_resell_be.entities.User;
import com.swd392.ticket_resell_be.enums.ErrorCode;
import com.swd392.ticket_resell_be.exceptions.AppException;
import com.swd392.ticket_resell_be.repositories.UserRepository;
import com.swd392.ticket_resell_be.services.UserService;
import com.swd392.ticket_resell_be.utils.ApiResponseBuilder;
import com.swd392.ticket_resell_be.utils.TokenUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImplement implements UserService {

    UserRepository userRepository;
    TokenUtil tokenUtil;
    ApiResponseBuilder apiResponseBuilder;

    @Override
    public ApiItemResponse<String> login(LoginDtoRequest loginDtoRequest) throws JOSEException {
        User user = userRepository.findByUsername(loginDtoRequest.username())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (!user.getPassword().equals(loginDtoRequest.password()))
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        return apiResponseBuilder.buildResponse(tokenUtil.generateToken(user), HttpStatus.OK);
    }

    @Override
    public Optional<User> getUserByName(String username) throws AppException {
        try {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                return user;
            } else {
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }


}
