import {Component} from '@angular/core';
import {DefaultService} from '../../openapi/tomaszewski/openapi';
import {AuthServiceService} from '../../services/auth-service.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SharedModule} from '../../shared.module';


@Component({
  selector: 'app-login-panel',
  imports: [
    SharedModule
  ],
  templateUrl: './login-panel.component.html',
  styleUrl: './login-panel.component.scss',
  providers: [DefaultService]
})
export class LoginPanelComponent {
  isLogin = true;
  loginForm: FormGroup;

  toggleForm() {
    this.isLogin = !this.isLogin;
  }

  constructor(private fb: FormBuilder, private authService: AuthServiceService) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      rememberMe: [false]
    });
  }

  username: string = '';
  password: string = '';
  errorMessage: string = '';

  login() {
    if (this.loginForm.valid) {
      const {username, password} = this.loginForm.value;
      this.authService.login({username, password}).subscribe({
        next: (response) => {
          console.log('logged', response);
        },
        error: (error) => {
          console.error('error', error);
        }
      });
    }
  }
}
