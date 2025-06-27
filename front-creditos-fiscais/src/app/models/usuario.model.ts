export interface LoginRequest {
  login: string;
  senha: string;
}

export interface LoginResponse {
  nome: string;
  login: string;
  token: string;
}

export interface CadastroRequest {
  nome: string;
  login: string;
  senha: string;
}

export interface CadastroResponse {
  id: number;
  nome: string;
  login: string;
}

export interface Usuario {
  nome: string;
  login: string;
  token: string;
}