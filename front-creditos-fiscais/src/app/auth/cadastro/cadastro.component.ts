import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class CadastroComponent {
  cadastroForm: FormGroup;
  loading = false;
  erro: string | null = null;
  sucesso: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.cadastroForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(2)]],
      login: ['', [Validators.required, Validators.minLength(3)]],
      senha: ['', [Validators.required, Validators.minLength(6)]],
      confirmarSenha: ['', [Validators.required]]
    }, { validators: this.senhasIguais });

    // Validação reativa para exibir erro de senha imediatamente
    this.cadastroForm.get('confirmarSenha')?.valueChanges.subscribe(() => {
      this.cadastroForm.get('confirmarSenha')?.markAsTouched();
      this.cadastroForm.updateValueAndValidity();
    });
    this.cadastroForm.get('senha')?.valueChanges.subscribe(() => {
      this.cadastroForm.get('confirmarSenha')?.updateValueAndValidity();
    });
  }

  onSubmit(): void {
    if (this.cadastroForm.valid) {
      this.loading = true;
      this.erro = null;
      this.sucesso = null;

      const { confirmarSenha, ...dadosCadastro } = this.cadastroForm.value;

      this.authService.cadastrar(dadosCadastro).subscribe({
        next: () => {
          this.sucesso = 'Cadastro realizado com sucesso! Redirecionando para login...';
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000);
        },
        error: (err) => {
          this.erro = err?.error?.message || 'Erro ao realizar cadastro. Tente novamente.';
          this.loading = false;
        }
      });
    } else {
      this.marcarCamposInvalidos();
    }
  }

  private senhasIguais(form: FormGroup): { [key: string]: any } | null {
    const senha = form.get('senha');
    const confirmarSenha = form.get('confirmarSenha');

    if (senha && confirmarSenha && senha.value !== confirmarSenha.value) {
      return { senhasDiferentes: true };
    }

    return null;
  }

  private marcarCamposInvalidos(): void {
    Object.keys(this.cadastroForm.controls).forEach(key => {
      const control = this.cadastroForm.get(key);
      if (control?.invalid) {
        control.markAsTouched();
      }
    });
  }

  getErrorMessage(campo: string): string {
    const control = this.cadastroForm.get(campo);
    if (control?.errors && control.touched) {
      if (control.errors['required']) return `${campo.charAt(0).toUpperCase() + campo.slice(1)} é obrigatório.`;
      if (control.errors['minlength']) return `${campo.charAt(0).toUpperCase() + campo.slice(1)} deve ter pelo menos ${control.errors['minlength'].requiredLength} caracteres.`;
    }
    return '';
  }

  getSenhaErrorMessage(): string {
    const control = this.cadastroForm.get('confirmarSenha');
    if (control?.errors && control.touched) {
      if (control.errors['required']) return 'Confirmação de senha é obrigatória.';
      if (this.cadastroForm.errors?.['senhasDiferentes']) return 'As senhas não coincidem.';
    }
    // Exibir erro de senhas diferentes mesmo se o campo não estiver tocado, mas o formulário foi tocado
    if (this.cadastroForm.errors?.['senhasDiferentes'] && this.cadastroForm.get('senha')?.touched && control?.value) {
      return 'As senhas não coincidem.';
    }
    return '';
  }

  isCadastroInvalido(): boolean {
    return this.cadastroForm.invalid || this.cadastroForm.errors?.['senhasDiferentes'];
  }
}
