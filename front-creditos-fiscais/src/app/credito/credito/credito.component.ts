import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Usuario } from '../../models/usuario.model';
import { AuthService } from '../../services/auth.service';
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
  valorFiltro = new FormControl('');
  creditos: any[] = [];
  loading = false;
  erro: string | null = null;
  usuario: Usuario | null = null;

  constructor(
    private creditoService: CreditoService,
    private authService: AuthService,
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

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
