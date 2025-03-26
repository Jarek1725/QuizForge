import {Component} from '@angular/core';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';


@Component({
  selector: 'app-login-panel',
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    MatButton
  ],
  templateUrl: './login-panel.component.html',
  styleUrl: './login-panel.component.scss'
})
export class LoginPanelComponent {

}
