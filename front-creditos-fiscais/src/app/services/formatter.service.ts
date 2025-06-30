import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FormatterService {

  constructor() { }

  /**
   * Formata data para dd/mm/yyyy
   */
  formatarData(data: string): string {
    if (!data) return '';
    const date = new Date(data);
    if (isNaN(date.getTime())) return data;

    return date.toLocaleDateString('pt-BR');
  }

  /**
   * Formata valor monetário
   */
  formatarMoeda(valor: number): string {
    if (valor === null || valor === undefined) return '';
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(valor);
  }

  /**
   * Formata alíquota com símbolo de %
   */
  formatarAliquota(aliquota: number): string {
    if (aliquota === null || aliquota === undefined) return '';
    return `${aliquota.toFixed(2)}%`;
  }

  /**
   * Converte boolean para Sim/Não
   */
  formatarSimplesNacional(simplesNacional: boolean): string {
    return simplesNacional ? 'Sim' : 'Não';
  }

  /**
   * Formata tipo de crédito
   */
  formatarTipoCredito(tipoCredito: string): string {
    if (!tipoCredito) return '';
    return tipoCredito.toUpperCase();
  }

  /**
   * Valida se o valor contém apenas números
   */
  validarApenasNumeros(valor: string): boolean {
    return /^\d+$/.test(valor);
  }
}
