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
  loginError: boolean = false;

  toggleForm() {
    this.isLogin = !this.isLogin;
  }

  constructor(private fb: FormBuilder, private authService: AuthServiceService, private router: Router) {
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
          this.router.navigate(['/home']).then(r => {})
        },
        error: (error) => {
          this.loginError = true
        }
      });
    }
  }
}
