import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class CreditoService {
  private api = `${environment.apiUrl}/creditos`;

  constructor(private http: HttpClient) {}

  buscarPorNfe(numeroNfe: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.api}/${numeroNfe}`).pipe(
      catchError(err => throwError(() => err))
    );
  }

  buscarPorCredito(numeroCredito: string): Observable<any> {
    return this.http.get<any>(`${this.api}/credito/${numeroCredito}`).pipe(
      catchError(err => throwError(() => err))
    );
  }
}
