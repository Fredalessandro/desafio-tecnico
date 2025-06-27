import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class LoginComponent {
  loginForm: FormGroup;
  loading = false;
  erro: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      login: ['', [Validators.required, Validators.minLength(3)]],
      senha: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.loading = true;
      this.erro = null;

      this.authService.login(this.loginForm.value).subscribe({
        next: () => {
          this.router.navigate(['/credito']);
        },
        error: (err) => {
          this.erro = err?.error?.message || 'Erro ao fazer login. Verifique suas credenciais.';
          this.loading = false;
        }
      });
    } else {
      this.marcarCamposInvalidos();
    }
  }

  private marcarCamposInvalidos(): void {
    Object.keys(this.loginForm.controls).forEach(key => {
      const control = this.loginForm.get(key);
      if (control?.invalid) {
        control.markAsTouched();
      }
    });
  }

  getErrorMessage(campo: string): string {
    const control = this.loginForm.get(campo);
    if (control?.errors && control.touched) {
      if (control.errors['required']) return `${campo.charAt(0).toUpperCase() + campo.slice(1)} é obrigatório.`;
      if (control.errors['minlength']) return `${campo.charAt(0).toUpperCase() + campo.slice(1)} deve ter pelo menos ${control.errors['minlength'].requiredLength} caracteres.`;
    }
    return '';
  }
}
