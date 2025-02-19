package com.swd392.ticket_resell_be.services.impls;

import com.swd392.ticket_resell_be.dtos.responses.ApiItemResponse;
import com.swd392.ticket_resell_be.entities.Transaction;
import com.swd392.ticket_resell_be.enums.Categorize;
import com.swd392.ticket_resell_be.services.MembershipService;
import com.swd392.ticket_resell_be.services.PaymentService;
import com.swd392.ticket_resell_be.services.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PaymentServiceImplement implements PaymentService {

    TransactionService transactionService;
    MembershipService membershipService;

    @Value("${SUCCESS_REDIRECT_URL}")
    @NonFinal
    private String successRedirectUrl;

    @Value("${FAILURE_REDIRECT_URL}")
    @NonFinal
    private String failureRedirectUrl;

    @Override
    public void handlePaymentCallback(
            String vnpAmount, String vnpBankCode, String vnpBankTranNo, String vnpCardType,
            String orderInfo, String vnpPayDate, String responseCode, String vnpTmnCode,
            String vnpTransactionNo, String transactionStatus, String vnpTxnRef, String vnpSecureHash,
            HttpServletResponse response
    ) {
        ApiItemResponse<Transaction> transactionResponse = transactionService.findTransactionByOrderId(vnpTxnRef);
        Transaction transaction = transactionResponse.data();

        try {
            if ("00".equalsIgnoreCase(responseCode) && "00".equalsIgnoreCase(transactionStatus)) {
                if (transaction == null) {
                    response.sendRedirect(failureRedirectUrl);
                    return;
                }
                transactionService.updateTransactionStatus(transaction.getId(), Categorize.COMPLETED);
                membershipService.updateMembership(transaction.getSeller(), transaction.getSubscription());
                response.sendRedirect(successRedirectUrl);
            } else {
                transactionService.updateTransactionStatus(transaction.getId(), Categorize.FAILED);
                response.sendRedirect(failureRedirectUrl);
            }
        } catch (Exception e) {
            try {
                response.sendRedirect(failureRedirectUrl);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

}
