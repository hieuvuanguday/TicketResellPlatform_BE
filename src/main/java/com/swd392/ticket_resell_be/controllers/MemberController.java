package com.swd392.ticket_resell_be.controllers;

import com.swd392.ticket_resell_be.services.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class MemberController {
    MemberService memberService;


}
