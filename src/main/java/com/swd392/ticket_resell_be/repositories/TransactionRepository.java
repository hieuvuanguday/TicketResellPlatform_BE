package com.swd392.ticket_resell_be.repositories;

import com.swd392.ticket_resell_be.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
//    Optional<Transaction> findTransactionByOrderId(String orderCode);
}
