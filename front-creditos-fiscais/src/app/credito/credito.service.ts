import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { Credito } from '../models/credito.model';

@Injectable({ providedIn: 'root' })
export class CreditoService {
  private api = `${environment.apiUrl}/creditos`;

  constructor(private http: HttpClient) {}

  buscarPorNfe(numeroNfe: string): Observable<Credito[]> {
    return this.http.get<Credito[]>(`${this.api}/${numeroNfe}`).pipe(
      catchError(err => throwError(() => err))
    );
  }

  buscarPorCredito(numeroCredito: string): Observable<Credito> {
    return this.http.get<Credito>(`${this.api}/credito/${numeroCredito}`).pipe(
      catchError(err => throwError(() => err))
    );
  }
}
