import { Component } from '@angular/core';
import {MatButton} from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import {LoginPanelComponent} from '../login-panel/login-panel.component';

@Component({
  selector: 'app-home-guest-component',
  imports: [
    MatButton,
    MatIconModule,
    LoginPanelComponent
  ],
  templateUrl: './home-guest-component.component.html',
  styleUrl: './home-guest-component.component.scss'
})
export class HomeGuestComponentComponent {

}
