import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  private apiUrl = 'http://localhost:8080/loginUser';
  private isLoggedInSubject = new BehaviorSubject<boolean>(this.hasSession());
  isLoggedIn$ = this.isLoggedInSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.apiUrl, loginRequest).pipe(
      tap(response => {
        sessionStorage.setItem('userSession', JSON.stringify(response));
        this.isLoggedInSubject.next(true);  // Zmieniamy stan logowania
      })
    );
  }

  private hasSession(): boolean {
    return !!sessionStorage.getItem('userSession');
  }

  logout(): void {
    sessionStorage.removeItem('userSession');
    this.isLoggedInSubject.next(false);
  }
}
