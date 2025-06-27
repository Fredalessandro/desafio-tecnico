package com.desafio.credito.repository;


import com.desafio.credito.entity.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {

    /**
     * Busca todos os créditos por número da NFS-e
     */
    List<Credito> findByNumeroNfse(String numeroNfse);

    /**
     * Busca um crédito específico por número do crédito
     */
    Optional<Credito> findByNumeroCredito(String numeroCredito);

    /**
     * Verifica se existe um crédito com o número informado
     */
    boolean existsByNumeroCredito(String numeroCredito);

    /**
     * Query customizada para buscar créditos por NFS-e (exemplo adicional)
     */
    @Query("SELECT c FROM Credito c WHERE c.numeroNfse = :numeroNfse ORDER BY c.dataConstituicao DESC")
    List<Credito> buscarCreditosPorNfseOrdenados(@Param("numeroNfse") String numeroNfse);
}