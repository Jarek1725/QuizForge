import {Component} from '@angular/core';
import {MatButton} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {LoginPanelComponent} from '../login-panel/login-panel.component';
import {HeaderComponent} from '../header/header.component';
import {SideNavComponent} from '../logged-user/side-nav/side-nav.component';
import {AsyncPipe, NgIf} from '@angular/common';
import {Observable} from 'rxjs';
import {UserData} from '../../openapi/tomaszewski/openapi';
import {AuthServiceService} from '../../services/auth-service.service';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-home-guest-component',
  imports: [
    MatButton,
    MatIconModule,
    LoginPanelComponent,
    HeaderComponent,
    SideNavComponent,
    NgIf,
    AsyncPipe,
    RouterOutlet
  ],
  templateUrl: './home-guest-component.component.html',
  styleUrl: './home-guest-component.component.scss'
})
export class HomeGuestComponentComponent {
  user$!: Observable<UserData | null>;

  constructor(private authService: AuthServiceService) {
    this.user$ = this.authService.currentUser$;
  }

}
