import {Component} from '@angular/core';
import {AsyncPipe, NgIf} from '@angular/common';
import {MatButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {AuthServiceService} from '../../services/auth-service.service';
import {Observable} from 'rxjs';
import {UserData} from '../../openapi/tomaszewski/openapi';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';

@Component({
  selector: 'app-header',
  imports: [
    MatButton,
    MatIcon,
    NgIf,
    AsyncPipe,
    MatMenuTrigger,
    MatMenuItem,
    MatMenu
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  user$!: Observable<UserData | null>;

  constructor(private authService: AuthServiceService) {
    this.user$ = this.authService.currentUser$;
  }

  logout() {
    this.authService.logout()
  }
}
