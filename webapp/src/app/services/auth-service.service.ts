import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {LoginRequest, LoginResponse} from '../openapi/tomaszewski/openapi';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  private apiUrl = 'http://localhost:8080/loginUser';
  private isLoggedInSubject = new BehaviorSubject<boolean>(this.hasSession());
  isLoggedIn$ = this.isLoggedInSubject.asObservable();

  constructor(private http: HttpClient) {
  }

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.apiUrl, loginRequest).pipe(
      tap(response => {
        sessionStorage.setItem('userSession', JSON.stringify(response));
        this.isLoggedInSubject.next(true);
      })
    );
  }

  private hasSession(): boolean {
    return !!sessionStorage.getItem('userSession');
  }
}
