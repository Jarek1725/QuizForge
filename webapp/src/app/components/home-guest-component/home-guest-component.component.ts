import { Component } from '@angular/core';
import {MatButton} from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import {LoginPanelComponent} from '../login-panel/login-panel.component';
import {HeaderComponent} from '../header/header.component';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-home-guest-component',
  imports: [
    MatButton,
    MatIconModule,
    LoginPanelComponent,
    HeaderComponent,
    RouterOutlet
  ],
  templateUrl: './home-guest-component.component.html',
  styleUrl: './home-guest-component.component.scss'
})
export class HomeGuestComponentComponent {

}
