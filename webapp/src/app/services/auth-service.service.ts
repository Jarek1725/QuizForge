import {BehaviorSubject, Observable, tap} from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';

export interface UserData {
  id: number;
  email: string;
  roles: string[];
}

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  private currentUserSubject: BehaviorSubject<UserData | null>;
  public currentUser$: Observable<UserData | null>;

  constructor(private http: HttpClient, private httpClient: HttpClient,
  ) {
    const storedUser = localStorage.getItem('user');
    this.currentUserSubject = new BehaviorSubject<UserData | null>(
      storedUser ? JSON.parse(storedUser) : null
    );
    this.currentUser$ = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): UserData | null {
    return this.currentUserSubject.value;
  }

  login(email: string, password: string): Observable<any> {
    const body: HttpParams = new HttpParams().set('email', email).set('password', password);
    const headers: HttpHeaders = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');

    return this.httpClient
      .post('/api/auth/login', body.toString(), {
        headers: headers,
        observe: 'response',
        withCredentials: true,
      })
      .pipe(
        tap((response: any) => {
          if (response.status === 200) {
            console.log('Zalogowano pomyślnie');
            const userData: UserData = {
              id: response.headers.get('user-id'),
              email: email,
              roles: ['user'],
            };
            console.log(userData)
            localStorage.setItem('user', JSON.stringify(userData));
            this.currentUserSubject.next(userData);
          } else {
            console.log('Błąd logowania');
            this.currentUserSubject.next(null);
          }
        })
      );
  }

  logout(): void {
    localStorage.removeItem('user');
    this.currentUserSubject.next(null);
  }

  isLoggedIn(): boolean {
    return this.currentUserSubject.value !== null;
  }

}
