package com.swd392.ticket_resell_be.services;

import com.swd392.ticket_resell_be.dtos.responses.ApiItemResponse;
import com.swd392.ticket_resell_be.dtos.responses.ApiListResponse;
import com.swd392.ticket_resell_be.dtos.responses.TransactionDtoResponse;
import com.swd392.ticket_resell_be.entities.Subscription;
import com.swd392.ticket_resell_be.entities.Transaction;
import com.swd392.ticket_resell_be.entities.User;
import com.swd392.ticket_resell_be.enums.Categorize;
import com.swd392.ticket_resell_be.exceptions.AppException;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface TransactionService {

    ApiItemResponse<Transaction> savePendingTransaction(Subscription subscription, User user, String orderId);

    ApiItemResponse<Transaction> findTransactionByOrderId(String orderCode) throws AppException;

    ApiListResponse<TransactionDtoResponse> getAllTransactions(String description, Categorize status, int page, int size, Sort.Direction direction, String... properties);

    ApiItemResponse<Transaction> updateTransactionStatus(UUID transactionId, Categorize status) throws AppException;

    ApiListResponse<TransactionDtoResponse> getAllTransactionsByUser(int page, int size, Sort.Direction direction, String... properties);

    void updatePendingToFailed();
}
