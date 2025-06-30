import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Usuario } from '../../models/usuario.model';
import { Credito } from '../../models/credito.model';
import { AuthService } from '../../services/auth.service';
import { FormatterService } from '../../services/formatter.service';
import { CreditoService } from '../credito.service';

@Component({
  selector: 'app-credito',
  templateUrl: './credito.component.html',
  styleUrls: ['./credito.component.scss'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule]
})
export class CreditoComponent implements OnInit {
  filtroTipo = new FormControl<'nfe' | 'credito'>('nfe');
  valorFiltro = new FormControl('', [Validators.required, Validators.pattern(/^\d+$/)]);
  creditos: Credito[] = [];
  loading = false;
  erro: string | null = null;
  usuario: Usuario | null = null;

  constructor(
    private creditoService: CreditoService,
    private authService: AuthService,
    private formatterService: FormatterService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.usuario = this.authService.getUsuario();
  }

  buscar() {
    this.erro = null;
    this.creditos = [];
    const valor = this.valorFiltro.value;

    if (!valor) {
      this.erro = 'Informe o número da NFe ou do Crédito.';
      return;
    }

    if (!this.formatterService.validarApenasNumeros(valor)) {
      this.erro = 'Digite apenas números no campo de pesquisa.';
      return;
    }

    this.loading = true;
    if (this.filtroTipo.value === 'nfe') {
      this.creditoService.buscarPorNfe(valor).subscribe({
        next: (res) => {
          this.creditos = res || [];
          this.loading = false;
          if (!this.creditos.length) this.erro = 'Nenhum crédito encontrado.';
        },
        error: (err) => {
          this.erro = err?.error?.message || 'Erro ao buscar créditos.';
          this.loading = false;
        }
      });
    } else {
      this.creditoService.buscarPorCredito(valor).subscribe({
        next: (res) => {
          this.creditos = res ? [res] : [];
          this.loading = false;
          if (!this.creditos.length) this.erro = 'Nenhum crédito encontrado.';
        },
        error: (err) => {
          this.erro = err?.error?.message || 'Erro ao buscar crédito.';
          this.loading = false;
        }
      });
    }
  }

  // Métodos de formatação
  formatarData(data: string): string {
    return this.formatterService.formatarData(data);
  }

  formatarMoeda(valor: number): string {
    return this.formatterService.formatarMoeda(valor);
  }

  formatarAliquota(aliquota: number): string {
    return this.formatterService.formatarAliquota(aliquota);
  }

  formatarSimplesNacional(simplesNacional: boolean): string {
    return this.formatterService.formatarSimplesNacional(simplesNacional);
  }

  formatarTipoCredito(tipoCredito: string): string {
    return this.formatterService.formatarTipoCredito(tipoCredito);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
