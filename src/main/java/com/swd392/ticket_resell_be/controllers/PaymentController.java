package com.swd392.ticket_resell_be.controllers;

import com.swd392.ticket_resell_be.services.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/payment")
@Tag(name = "Payment APIs")
public class PaymentController {
    PaymentService paymentService;

    @GetMapping("/callback")
    public void paymentCallback(
            @RequestParam("vnp_Amount") String vnpAmount,
            @RequestParam("vnp_BankCode") String vnpBankCode,
            @RequestParam(value = "vnp_BankTranNo", required = false) String vnpBankTranNo,
            @RequestParam("vnp_CardType") String vnpCardType,
            @RequestParam("vnp_OrderInfo") String orderInfo,
            @RequestParam("vnp_PayDate") String vnpPayDate,
            @RequestParam("vnp_ResponseCode") String responseCode,
            @RequestParam("vnp_TmnCode") String vnpTmnCode,
            @RequestParam("vnp_TransactionNo") String vnpTransactionNo,
            @RequestParam("vnp_TransactionStatus") String transactionStatus,
            @RequestParam("vnp_TxnRef") String vnpTxnRef,
            @RequestParam("vnp_SecureHash") String vnpSecureHash,
            HttpServletResponse response
    ) {
        paymentService.handlePaymentCallback(
                vnpAmount, vnpBankCode, vnpBankTranNo, vnpCardType,
                orderInfo, vnpPayDate, responseCode, vnpTmnCode,
                vnpTransactionNo, transactionStatus, vnpTxnRef, vnpSecureHash, response
        );
    }
}
