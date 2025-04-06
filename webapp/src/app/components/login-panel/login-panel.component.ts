import {Component} from '@angular/core';
import {AuthServiceService} from '../../services/auth-service.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SharedModule} from '../../shared.module';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';


@Component({
  selector: 'app-login-panel',
  imports: [
    SharedModule
  ],
  templateUrl: './login-panel.component.html',
  styleUrl: './login-panel.component.scss'
})
export class LoginPanelComponent {
  isLogin = true;
  loginForm: FormGroup;

  toggleForm() {
    this.isLogin = !this.isLogin;
  }

  constructor(private fb: FormBuilder, private authService: AuthServiceService, private httpClient: HttpClient) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      rememberMe: [false]
    });
  }

  login() {
    if (this.loginForm.valid) {
      const {username, password} = this.loginForm.value;
      this.authService.login(username, password).subscribe({
        next: (response) => {
          console.log('logged', response);
        },
        error: (error) => {
          console.error('error', error);
        }
      });
    }
  }

  test(): void {
    this.httpClient.get('/api/user', {withCredentials: true}).subscribe(
      (response) => {
        console.log('Response:', response);
      },
      (error) => {
        console.error('Error:', error);
      }
    )
  }
}
