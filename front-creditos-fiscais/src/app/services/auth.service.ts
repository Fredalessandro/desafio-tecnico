import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { CadastroRequest, CadastroResponse, LoginRequest, LoginResponse, Usuario } from '../models/usuario.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly API_URL = `${environment.apiUrl}/usuarios`;
  private readonly TOKEN_KEY = 'auth_token';
  private readonly USER_KEY = 'auth_user';

  private usuarioAtual = new BehaviorSubject<Usuario | null>(this.getUsuarioFromStorage());
  public usuarioAtual$ = this.usuarioAtual.asObservable();

  constructor(private http: HttpClient) {}

  login(credenciais: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.API_URL}/login`, credenciais).pipe(
      tap(response => this.setarSessao(response))
    );
  }

  cadastrar(dados: CadastroRequest): Observable<CadastroResponse> {
    return this.http.post<CadastroResponse>(this.API_URL, dados);
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    this.usuarioAtual.next(null);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isAutenticado(): boolean {
    return !!this.getToken();
  }

  getUsuario(): Usuario | null {
    return this.usuarioAtual.value;
  }

  private setarSessao(response: LoginResponse): void {
    const usuario: Usuario = {
      nome: response.nome,
      login: response.login,
      token: response.token
    };

    localStorage.setItem(this.TOKEN_KEY, response.token);
    localStorage.setItem(this.USER_KEY, JSON.stringify(usuario));
    this.usuarioAtual.next(usuario);
  }

  private getUsuarioFromStorage(): Usuario | null {
    const userStr = localStorage.getItem(this.USER_KEY);
    return userStr ? JSON.parse(userStr) : null;
  }
}
