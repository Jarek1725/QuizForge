import {Component} from '@angular/core';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {CommonModule} from '@angular/common';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {Router} from '@angular/router';
import {DefaultService} from '../../openapi/tomaszewski/openapi';
import {MatCheckbox} from '@angular/material/checkbox';


@Component({
  selector: 'app-login-panel',
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    MatButton,
    CommonModule,
    HttpClientModule,
    MatCheckbox
  ],
  templateUrl: './login-panel.component.html',
  styleUrl: './login-panel.component.scss',
  providers:[DefaultService]
})
export class LoginPanelComponent {
  isLogin = true;

  toggleForm() {
    this.isLogin = !this.isLogin;
  }

  constructor(private http: HttpClient,
              private defaultService: DefaultService,
              private router: Router
  ) {
  }

    username: string = '';
  password: string = '';
  errorMessage: string = '';

  // login() {
  //   const loginRequest: LoginRequest = {
  //     username: this.username,
  //     password: this.password
  //   }
  //
  //   this.defaultService.loginUser(loginRequest).subscribe({
  //     next: (response: LoginResponse) => {
  //       console.log('Zalogowano pomyślnie', response);
  //       this.router.navigate(['/home']);
  //     },
  //     error: (error) => {
  //       console.error('Błąd logowania', error);
  //       this.errorMessage = 'Nieprawidłowy login lub hasło';
  //     }
  //   });
  // }
}
