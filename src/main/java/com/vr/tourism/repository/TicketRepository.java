package com.vr.tourism.repository;

import com.vr.tourism.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TicketRepository extends JpaRepository<Ticket, String> {}
