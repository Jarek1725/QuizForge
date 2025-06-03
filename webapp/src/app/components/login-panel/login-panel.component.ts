import {Component} from '@angular/core';
import {AuthServiceService} from '../../services/auth-service.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SharedModule} from '../../shared.module';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Router} from '@angular/router';


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
  registerForm: FormGroup;
  loginError: boolean = false;
  registerError: boolean = false;

  toggleForm() {
    this.isLogin = !this.isLogin;
  }

  constructor(
    private fb: FormBuilder,
    private authService: AuthServiceService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      rememberMe: [false]
    });

    this.registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
  }

  login() {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      this.authService.login(username, password).subscribe({
        next: () => {
          this.loginError = false;
          this.router.navigate(['/home']);
        },
        error: () => {
          this.loginError = true;
        }
      });
    }
  }

  register() {
    if (this.registerForm.valid) {
      const { email, password, confirmPassword } = this.registerForm.value;

      if (password !== confirmPassword) {
        this.registerError = true;
        return;
      }

      this.authService.register(email, password).subscribe({
        next: () => {
          this.registerError = false;
          this.toggleForm();
        },
        error: () => {
          this.registerError = false;
          this.toggleForm();
        }
      });
    }
  }
}
